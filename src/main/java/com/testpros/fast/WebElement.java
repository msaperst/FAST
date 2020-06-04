package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.testpros.fast.utilities.Constants.*;

public class WebElement implements org.openqa.selenium.WebElement {

    public static final String WAITED = "Waited '";
    public static final String AFTER_WAITING = "After waiting '";
    RemoteWebDriver driver;
    org.openqa.selenium.WebElement element;
    String elementName;
    Reporter reporter;
    String screenshot;

    // TODO - JavaDoc
    protected WebElement(RemoteWebDriver driver, org.openqa.selenium.WebElement element, int match) {
        this.driver = driver;
        this.elementName = (element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "") + " [" + match + "]").trim();
        this.reporter = driver.getReporter();
    }

    // TODO - JavaDoc
    protected WebElement(RemoteWebDriver driver, By by) {
        this.driver = driver;
        this.elementName = by.toString().trim();   //TODO - clean this up some
        this.reporter = driver.getReporter();
        // before we do anything, ensure the element present, and wait for it if needed
        driver.waitForElementPresent(by);
        this.element = driver.getDriver().findElement(by);
        // scroll to the element
        // TODO
        // capture the element
        try {
            File fullPageScreenshot = ((TakesScreenshot) driver.getDriver()).getScreenshotAs(OutputType.FILE);
            BufferedImage fullImg = ImageIO.read(fullPageScreenshot);
            Rectangle rectangle = this.element.getRect();
            if (new Rectangle(0, 0, 0, 0).equals(rectangle)) {
                return;
            }
            BufferedImage eleScreenshot = fullImg.getSubimage(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
            ImageIO.write(eleScreenshot, "png", fullPageScreenshot);
            byte[] fileContent = FileUtils.readFileToByteArray(fullPageScreenshot);
            screenshot = Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            //TODO - properly handle this error
            e.printStackTrace();
        }
    }

    /**
     * Extends the basic Selenium click functionality to
     * click this element. If this causes a new page to load, you
     * should discard all references to this element and any further
     * operations performed on this element will throw a
     * StaleElementReferenceException.
     * This action is logged to the FAST reporter, including a screenshot
     * after the action.
     * Note that if click() is done by sending a native event (which is
     * the default on most browsers/platforms) then the method will
     * _not_ wait for the next page to load and the caller should verify
     * that themselves.
     * There are some preconditions for an element to be clicked. The
     * element must be visible and it must have a height and width
     * greater then 0.
     *
     * @throws StaleElementReferenceException If the element no
     *                                        longer exists as initially defined
     */
    public void click() {
        Step step = new Step("Clicking on element '" + this.elementName + "'",
                "Element is present and visible and clicked");
        try {
            this.element.click();
            step.setPassed("Clicked on element");
        } catch (Exception e) {
            step.setFailed("Unable to click on element: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Extends the basic Selenium submit functionality to submit this element.
     * If this current element is a form, or an element within a form, then this will be submitted to
     * the remote server. If this causes the current page to change, then this method will block until
     * the new page is loaded.
     * This action is logged to the FAST reporter, including a screenshot
     * after the action.
     *
     * @throws NoSuchElementException If the given element is not within a form
     */
    public void submit() {
        Step step = new Step("Submitting element '" + this.elementName + "'",
                "Element is present and visible and submitted");
        try {
            this.element.submit();
            step.setPassed("Submitted element");
        } catch (Exception e) {
            step.setFailed("Unable to submit element: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Extends the basic Selenium sendKeys functionality to sendKeys to this element.
     * Use this method to simulate typing into an element, which may set its value.
     * This action is logged to the FAST reporter, including a screenshot
     * after the action.
     *
     * @param keysToSend character sequence to send to the element
     * @throws IllegalArgumentException if keysToSend is null
     */
    public void sendKeys(CharSequence... keysToSend) {
        String charsSent = String.join("', '", keysToSend);
        Step step = new Step("Sending keys '" + charsSent + "' to element '" + this.elementName + "'",
                "Element is present, visible, an input, and enabled and had keys sent to it");
        try {
            this.element.sendKeys(keysToSend);
            step.setPassed("Sent keys to element");
        } catch (Exception e) {
            step.setFailed("Unable to send keys: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Extends the basic Selenium clear functionality to remove text from this element.
     * If this element is a text entry element, this will clear the value. Has no effect on other
     * elements. Text entry elements are INPUT and TEXTAREA elements.
     * Note that the events fired by this event may not be as you'd expect.  In particular, we don't
     * fire any keyboard or mouse events.  If you want to ensure keyboard events are fired, consider
     * using something like {@link #sendKeys(CharSequence...)} with the backspace key.  To ensure
     * you get a change event, consider following with a call to {@link #sendKeys(CharSequence...)}
     * with the tab key.
     * This action is logged to the FAST reporter, including a screenshot
     * after the action.
     */
    public void clear() {
        Step step = new Step("Clearing all input from element '" + this.elementName + "'",
                "Element doesn't contain any input");
        try {
            this.element.clear();
            step.setTime();
            if (!"".equals(getAttribute("value"))) {
                step.setFailed("Failed to clear content from element");
            } else {
                step.setPassed("Successfully cleared content from element");
            }
        } catch (Exception e) {
            step.setFailed("Unable to clear element content: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Get the tag name of this element. <b>Not</b> the value of the name attribute: will return
     * <code>"input"</code> for the element <code>&lt;input name="foo" /&gt;</code>.
     *
     * @return The tag name of this element.
     */
    public String getTagName() {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.getTagName();
    }

    /**
     * Get the value of the given attribute of the element. Will return the current value, even if
     * this has been modified after the page has been loaded.
     *
     * <p>More exactly, this method will return the value of the property with the given name, if it
     * exists. If it does not, then the value of the attribute with the given name is returned. If
     * neither exists, null is returned.
     *
     * <p>The "style" attribute is converted as best can be to a text representation with a trailing
     * semi-colon.
     *
     * <p>The following are deemed to be "boolean" attributes, and will return either "true" or null:
     *
     * <p>async, autofocus, autoplay, checked, compact, complete, controls, declare, defaultchecked,
     * defaultselected, defer, disabled, draggable, ended, formnovalidate, hidden, indeterminate,
     * iscontenteditable, ismap, itemscope, loop, multiple, muted, nohref, noresize, noshade,
     * novalidate, nowrap, open, paused, pubdate, readonly, required, reversed, scoped, seamless,
     * seeking, selected, truespeed, willvalidate
     *
     * <p>Finally, the following commonly mis-capitalized attribute/property names are evaluated as
     * expected:
     *
     * <ul>
     * <li>If the given name is "class", the "className" property is returned.
     * <li>If the given name is "readonly", the "readOnly" property is returned.
     * </ul>
     *
     * <i>Note:</i> The reason for this behavior is that users frequently confuse attributes and
     * properties. If you need to do something more precise, e.g., refer to an attribute even when a
     * property of the same name exists, then you should evaluate Javascript to obtain the result
     * you desire.
     *
     * @param name The name of the attribute.
     * @return The attribute/property's current value or null if the value is not set.
     */
    public String getAttribute(String name) {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.getAttribute(name);
    }

    /**
     * Determine whether or not this element is selected or not. This operation only applies to input
     * elements such as checkboxes, options in a select and radio buttons.
     * For more information on which elements this method supports,
     * refer to the <a href="https://w3c.github.io/webdriver/webdriver-spec.html#is-element-selected">specification</a>.
     *
     * @return True if the element is currently selected or checked, false otherwise.
     */
    public boolean isSelected() {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.isSelected();
    }

    // TODO - JavaDoc
    public void waitForSelected() {
        if (!isSelected()) {
            // if it's not displayed, wait, and log that wait
            Step step = new Step(WAITING_FOR_ELEMENT + elementName + "' to be selected",
                    "Element is selected");
            try {
                WebDriverWait wait = new WebDriverWait(driver, driver.waitTime, driver.pollTime);
                wait.until((ExpectedCondition<Boolean>) d -> isSelected());
                step.setPassed(WAITED + step.getTime() + "' milliseconds for element to be selected");
            } catch (TimeoutException e) {
                step.setFailed(AFTER_WAITING + driver.waitTime + "' seconds, element is not selected");
            } finally {
                reporter.addStep(step);
            }
        }
    }

    /**
     * Is the element currently enabled or not? This will generally return true for everything but
     * disabled input elements.
     *
     * @return True if the element is enabled, false otherwise.
     */
    public boolean isEnabled() {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.isEnabled();
    }

    // TODO - JavaDoc
    public void waitForEnabled() {
        if (!isEnabled()) {
            // if it's not displayed, wait, and log that wait
            Step step = new Step(WAITING_FOR_ELEMENT + elementName + "' to be enabled",
                    "Element is enabled");
            try {
                WebDriverWait wait = new WebDriverWait(driver, driver.waitTime, driver.pollTime);
                wait.until((ExpectedCondition<Boolean>) d -> isEnabled());
                step.setPassed(WAITED + step.getTime() + "' milliseconds for element to be enabled");
            } catch (TimeoutException e) {
                step.setFailed(AFTER_WAITING + driver.waitTime + "' seconds, element is not enabled");
            } finally {
                reporter.addStep(step);
            }
        }
    }

    /**
     * Get the visible (i.e. not hidden by CSS) text of this element, including sub-elements.
     *
     * @return The visible text of this element.
     * @see <a href="https://w3c.github.io/webdriver/#get-element-text">"Get Element Text" section
     * in W3C WebDriver Specification</a>
     */
    public String getText() {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.getText();
    }

    /**
     * @param by The Selenium locating mechanism
     * @return A list of all {@link org.openqa.selenium.WebElement}s, or an empty list if nothing matches
     * @deprecated do not use this with FAST, instead, use the one with
     * the FAST By class. Only use this if you want Selenium WebElements
     * returned, which will not provide FAST functionality
     */
    @Deprecated
    public List<org.openqa.selenium.WebElement> findElements(org.openqa.selenium.By by) {
        return this.element.findElements(by);
    }

    /**
     * Extends the basic Selenium findElements functionality to
     * find all elements within the current context using the given mechanism. When using xpath be
     * aware that webdriver follows standard conventions: a search prefixed with "//" will search the
     * entire document, not just the children of this current node. Use ".//" to limit your search to
     * the children of this WebElement.
     * Unlike the findElement, there is no fluent wait built in, and it also doesn't use the
     * 'implicit wait' times in force at the time of execution. If you want a wait before
     * returning the list (ensuring at least one element is returned, run a
     * {@link RemoteWebDriver#waitForElementPresent(By)} before calling this method
     *
     * @param by The locating mechanism to use
     * @return A list of all {@link WebElement}s, or an empty list if nothing matches.
     * @see By
     */
    public List<WebElement> findElements(By by) {
        // not doing any logging, as this is just a check, nothing to log
        List<WebElement> webElements = new ArrayList<>();
        List<org.openqa.selenium.WebElement> seleniumWebElements = this.element.findElements(by);
        int counter = 1;
        for (org.openqa.selenium.WebElement seleniumWebElement : seleniumWebElements) {
            webElements.add(new WebElement(driver, seleniumWebElement, counter));
            counter++;
        }
        return webElements;
    }

    /**
     * @param by The Selenium locating mechanism
     * @return The first matching element on the current context
     * @deprecated do not use this with FAST, instead, use the one with
     * the FAST By class. Only use this if you want Selenium WebElements
     * returned, which will not provide FAST functionality
     */
    @Deprecated
    public org.openqa.selenium.WebElement findElement(org.openqa.selenium.By by) {
        return this.element.findElement(by);
    }

    public WebElement findElement(By by) {
        // not doing any logging, as this is just a check, nothing to log
        return new WebElement(driver, by);
    }

    public boolean isDisplayed() {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.isDisplayed();
    }

    public void waitForDisplayed() {
        if (!isDisplayed()) {
            // if it's not displayed, wait, and log that wait
            Step step = new Step(WAITING_FOR_ELEMENT + elementName + "' to be displayed",
                    "Element is displayed");
            try {
                WebDriverWait wait = new WebDriverWait(driver, driver.waitTime, driver.pollTime);
                wait.until((ExpectedCondition<Boolean>) d -> isDisplayed());
                step.setPassed(WAITED + step.getTime() + "' milliseconds for element to be displayed");
            } catch (TimeoutException e) {
                step.setFailed(AFTER_WAITING + driver.waitTime + "' seconds, element is not displayed");
            } finally {
                reporter.addStep(step);
            }
        }
    }

    public Point getLocation() {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.getLocation();
    }

    public Dimension getSize() {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.getSize();
    }

    public Rectangle getRect() {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.getRect();
    }

    public String getCssValue(String s) {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.getCssValue(s);
    }

    public <X> X getScreenshotAs(OutputType<X> outputType) {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.getScreenshotAs(outputType);
    }

    public org.openqa.selenium.WebElement getWebElement() {
        return this.element;
    }

    String getElementName() {
        return this.elementName;
    }
}
