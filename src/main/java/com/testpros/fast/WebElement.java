package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import com.testpros.fast.reporter.Step.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class WebElement implements org.openqa.selenium.WebElement {

    org.openqa.selenium.WebDriver driver;
    org.openqa.selenium.WebElement element;
    String elementName;
    Reporter reporter;
    String screenshot;

    //wait times
    long waitTime = 30;
    long pollTime = 50;

    protected WebElement(org.openqa.selenium.WebDriver driver, org.openqa.selenium.WebElement element, int match, Reporter reporter) {
        this.driver = driver;
        this.elementName = element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "") + " [" + match + "]";
        this.reporter = reporter;
    }

    protected WebElement(org.openqa.selenium.WebDriver driver, By by, Reporter reporter) {
        this.driver = driver;
        this.elementName = by.toString();   //TODO - clean this up some
        this.reporter = reporter;
        // before we do anything, ensure the element present, and wait for it if needed
        if (!isPresent(by)) {
            // if it's not present, wait, and log that wait
            Step step = new Step("Waiting for element '" + by + "' to be present",
                    "Element '" + by + "' is present");
            try {
                WebDriverWait wait = new WebDriverWait(driver, waitTime, pollTime);
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
                step.setStatus(Status.PASS);
                step.setActual("Waited '" + step.getTime() + "' milliseconds for element '" + by + "' to be present");
            } catch (TimeoutException e) {
                step.setStatus(Status.FAIL);
                step.setActual("After waiting '" + waitTime + "' seconds, element '" + by + "' is not present");
            } finally {
                reporter.addStep(step);
            }
        }
        this.element = driver.findElement(by);
        // scroll to the element
        // TODO
        // capture the element
        try {
            File fullPageScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
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

    private boolean isPresent(org.openqa.selenium.By by) {
        boolean isPresent = false;
        try {
            driver.findElement(by);
            isPresent = true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            // do nothing
        }
        return isPresent;
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

    public boolean isEnabled() {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.isEnabled();
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
        new WebElement(driver, by, reporter);
        // not doing any logging, as this is just a check, nothing to log
        List<WebElement> webElements = new ArrayList<>();
        List<org.openqa.selenium.WebElement> elements = this.element.findElements(by);
        int counter = 1;
        for (org.openqa.selenium.WebElement element : elements) {
            webElements.add(new WebElement(driver, element, counter, reporter));
            counter++;
        }
        return webElements;
    }

    public WebElement findElement(By by) {
        // not doing any logging, as this is just a check, nothing to log
        return new WebElement(driver, by, reporter);
    }

    public boolean isDisplayed() {
        // not doing any logging, as this is just a check, nothing to log
        return this.element.isDisplayed();
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
