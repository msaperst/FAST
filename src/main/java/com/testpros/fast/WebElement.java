package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import com.testpros.fast.reporter.Step.Status;
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

public class WebElement implements org.openqa.selenium.WebElement {

    WebDriver driver;
    org.openqa.selenium.WebElement element;
    String elementName;
    Reporter reporter;
    String screenshot;

    protected WebElement(WebDriver driver, org.openqa.selenium.WebElement element, int match) {
        this.driver = driver;
        this.elementName = element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "") + " [" + match + "]";
        this.reporter = driver.getReporter();
    }

    protected WebElement(WebDriver driver, By by) {
        this.driver = driver;
        this.elementName = by.toString();   //TODO - clean this up some
        this.reporter = driver.getReporter();
        // before we do anything, ensure the element present, and wait for it if needed
        driver.waitForElementPresent(by);
        this.element = driver.driver.findElement(by);
        // scroll to the element
        // TODO
        // capture the element
        try {
            File fullPageScreenshot = ((TakesScreenshot) driver.driver).getScreenshotAs(OutputType.FILE);
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
            e.printStackTrace();
        }
    }

    public void click() {
        Step step = new Step("Clicking on element '" + this.elementName + "'",
                "Element '" + this.elementName + "' is present and visible and clicked");
        try {
            this.element.click();
            step.setActual("Successfully clicked on element '" + this.elementName + "'");
            step.setStatus(Status.PASS);
        } catch (Exception e) {
            step.setActual("Unable to click on element: " + e);
            step.setStatus(Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    public void submit() {
        Step step = new Step("Submitting element '" + this.elementName + "'",
                "Element '" + this.elementName + "' is present and visible and submitted");
        try {
            this.element.submit();
            step.setActual("Successfully submitted element '" + this.elementName + "'");
            step.setStatus(Status.PASS);
        } catch (Exception e) {
            step.setActual("Unable to submit element: " + e);
            step.setStatus(Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    public void sendKeys(CharSequence... charSequences) {
        String charsSent = String.join("', '", charSequences);
        Step step = new Step("Sending keys '" + charsSent + "' to element '" + this.elementName + "'",
                "Element '" + this.elementName + "' is present, visible, an input, and enabled and had keys '" +
                        charsSent + "' sent to it");
        try {
            this.element.sendKeys(charSequences);
            step.setActual("Successfully sent keys '" + charsSent +
                    "' to element '" + this.elementName + "'");
            step.setStatus(Status.PASS);
        } catch (Exception e) {
            step.setActual("Unable to send keys: " + e);
            step.setStatus(Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    public void clear() {
        Step step = new Step("Clearing all input from element '" + this.elementName + "'",
                "Element '" + this.elementName + "' doesn't contain any input");
        try {
            this.element.clear();
            step.setTime();
            if (!"".equals(getAttribute("value"))) {
                step.setActual("Did not clear content from element '" + this.elementName + "'");
                step.setStatus(Status.FAIL);
            } else {
                step.setActual("Successfully cleared content from element '" + this.elementName + "'");
                step.setStatus(Status.PASS);
            }
        } catch (Exception e) {
            step.setActual("Unable to submit element: " + e);
            step.setStatus(Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    public String getTagName() {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.getTagName();
    }

    public String getAttribute(String s) {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.getAttribute(s);
    }

    public boolean isSelected() {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.isSelected();
    }

    public void waitForSelected() {
        if (!isSelected()) {
            // if it's not displayed, wait, and log that wait
            Step step = new Step("Waiting for element '" + elementName + "' to be selected",
                    "Element '" + elementName + "' is selected");
            try {
                WebDriverWait wait = new WebDriverWait(driver, driver.waitTime, driver.pollTime);
                wait.until((ExpectedCondition<Boolean>) d -> isSelected());
                step.setStatus(Status.PASS);
                step.setActual("Waited '" + step.getTime() + "' milliseconds for element '" + elementName + "' to be selected");
            } catch (TimeoutException e) {
                step.setStatus(Status.FAIL);
                step.setActual("After waiting '" + driver.waitTime + "' seconds, element '" + elementName + "' is not selected");
            } finally {
                reporter.addStep(step);
            }
        }
    }

    public boolean isEnabled() {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.isEnabled();
    }

    public void waitForEnabled() {
        if (!isEnabled()) {
            // if it's not displayed, wait, and log that wait
            Step step = new Step("Waiting for element '" + elementName + "' to be enabled",
                    "Element '" + elementName + "' is enabled");
            try {
                WebDriverWait wait = new WebDriverWait(driver, driver.waitTime, driver.pollTime);
                wait.until((ExpectedCondition<Boolean>) d -> isEnabled());
                step.setStatus(Status.PASS);
                step.setActual("Waited '" + step.getTime() + "' milliseconds for element '" + elementName + "' to be enabled");
            } catch (TimeoutException e) {
                step.setStatus(Status.FAIL);
                step.setActual("After waiting '" + driver.waitTime + "' seconds, element '" + elementName + "' is not enabled");
            } finally {
                reporter.addStep(step);
            }
        }
    }

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

    public List<WebElement> findElements(By by) {
        // first wait, and ensure at least one match is available, but we'll throw it away
        this.driver.waitForElementPresent(by);
        // not doing any logging, as this is just a check, nothing to log
        List<WebElement> webElements = new ArrayList<>();
        List<org.openqa.selenium.WebElement> elements = this.element.findElements(by);
        int counter = 1;
        for (org.openqa.selenium.WebElement element : elements) {
            webElements.add(new WebElement(driver, element, counter));
            counter++;
        }
        return webElements;
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
            Step step = new Step("Waiting for element '" + elementName + "' to be displayed",
                    "Element '" + elementName + "' is displayed");
            try {
                WebDriverWait wait = new WebDriverWait(driver, driver.waitTime, driver.pollTime);
                wait.until((ExpectedCondition<Boolean>) d -> isDisplayed());
                step.setStatus(Status.PASS);
                step.setActual("Waited '" + step.getTime() + "' milliseconds for element '" + elementName + "' to be displayed");
            } catch (TimeoutException e) {
                step.setStatus(Status.FAIL);
                step.setActual("After waiting '" + driver.waitTime + "' seconds, element '" + elementName + "' is not displayed");
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
}
