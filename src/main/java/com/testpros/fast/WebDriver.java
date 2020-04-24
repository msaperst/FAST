package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import com.testpros.fast.reporter.Step.Status;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WebDriver implements org.openqa.selenium.WebDriver {

    org.openqa.selenium.WebDriver driver;
    Reporter reporter;

    public WebDriver(org.openqa.selenium.WebDriver driver) {
        this.driver = driver;
        reporter = new Reporter(driver);
    }

    public Reporter getReporter() {
        return reporter;
    }

    /**
     * Load a new web page in the current browser window. This is done using an HTTP GET operation,
     * and the method will block until the load is complete. This will follow redirects issued either
     * by the server or as a meta-redirect from within the returned HTML. Should a meta-redirect
     * "rest" for any duration of time, it is best to wait until this timeout is over, since should
     * the underlying page change whilst your test is executing the results of future calls against
     * this interface will be against the freshly loaded page. Synonym for
     * {@link org.openqa.selenium.WebDriver.Navigation#to(String)}. Additionally, this will log the
     * activity into the FAST reporter. If exact the specified URL loads successfully, it will be
     * considered a pass, otherwise a failure will be recorded, with the reason for the failure.
     *
     * @param url The URL to load. It is best to use a fully qualified URL
     */
    public void get(String url) {
        Step step = new Step("Loading URL of '" + url + "'",
                "Expected URL of '" + url + "' to load");
        try {
            this.driver.get(url);
            step.setActual("Loaded URL of '" + getCurrentUrl() + "'");
            if (!url.equals(getCurrentUrl())) {
                step.setStatus(Status.FAIL);
            } else {
                step.setStatus(Status.PASS);
            }
        } catch (Exception e) {
            step.setActual("Unable to load URL: " + e);
            step.setStatus(Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    public String getCurrentUrl() {
        // not doing any logging, as this is just a check, nothing to log
        return this.driver.getCurrentUrl();
    }

    public String getTitle() {
        // not doing any logging, as this is just a check, nothing to log
        return this.driver.getTitle();
    }

    /**
     * @param by The Selenium locating mechanism
     * @return A list of all {@link org.openqa.selenium.WebElement}url,
     * or an empty list if nothing matches
     * @deprecated do not use this with FAST, instead, use the one with
     * the FAST By class. Only use this if you want Selenium WebElements
     * returned, which will not provide FAST functionality
     */
    @Deprecated
    public List<org.openqa.selenium.WebElement> findElements(org.openqa.selenium.By by) {
        return this.driver.findElements(by);
    }

    /**
     * @param by The Selenium locating mechanism
     * @return The first matching element on the current page
     * @deprecated do not use this with FAST, instead, use the one with
     * the FAST By class. Only use this if you want Selenium WebElements
     * returned, which will not provide FAST functionality
     */
    @Deprecated
    public org.openqa.selenium.WebElement findElement(org.openqa.selenium.By by) {
        return this.driver.findElement(by);
    }

    public List<WebElement> findElements(By by) {
        // first wait, and ensure at least one match is available, but we'll throw it away
        new WebElement(driver, by, reporter);
        // not doing any logging, as this is just a check, nothing to log
        List<WebElement> webElements = new ArrayList<>();
        List<org.openqa.selenium.WebElement> elements = driver.findElements(by);
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

    public String getPageSource() {
        // not doing any logging, as this is just a check, nothing to log
        return this.driver.getPageSource();
    }

    /**
     * Close the current window, quitting the browser if it's the last window currently open.
     * Additionally, this will log the activity into the FAST reporter. If current window is
     * closed, it will be considered a pass, otherwise a failure will be recorded,
     * with the reason for the failure.
     */
    public void close() {
        String windowHandle = getWindowHandle();
        Step step = new Step("Closing current window with handle '" + windowHandle + "'",
                "Window with handle '" + windowHandle + "' is no longer open");
        try {
            this.driver.close();
            step.setTime();
            if (getWindowHandles().contains(windowHandle)) {
                step.setActual("Window with handle '" + windowHandle + "' is still open");
                step.setStatus(Status.FAIL);
            } else {
                step.setActual("Window successfully closed");
                step.setStatus(Status.PASS);
            }
        } catch (Exception e) {
            step.setActual("Unable to close window: " + e);
            step.setStatus(Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Quits this driver, closing every associated window. Additionally, this will log the
     * activity into the FAST reporter. If all windows are closed, and the driver has exited,
     * it will be considered a pass, otherwise a failure will be recorded, with the
     * reason for the failure.
     */
    public void quit() {
        Step step = new Step("Exiting WebDriver session",
                "Driver successfully quits");
        try {
            this.driver.quit();
            step.setActual("Application successfully closed");
            step.setStatus(Status.PASS);
        } catch (Exception e) {
            step.setActual("Unable to quit application: " + e);
            step.setStatus(Status.FAIL);
        } finally {
            reporter.addStep(step, false);
        }
    }

    public Set<String> getWindowHandles() {
        // not doing any logging, as this is just a check, nothing to log
        return this.driver.getWindowHandles();
    }

    public String getWindowHandle() {
        // not doing any logging, as this is just a check, nothing to log
        return this.driver.getWindowHandle();
    }

    //TODO - need to dive into this
    public TargetLocator switchTo() {
        // not doing any logging, as this is just a check, nothing to log
        return this.driver.switchTo();
    }

    //TODO - need to dive into this
    public Navigation navigate() {
        // not doing any logging, as this is just a check, nothing to log
        return this.driver.navigate();
    }

    //TODO - need to dive into this
    public Options manage() {
        // not doing any logging, as this is just a check, nothing to log
        return this.driver.manage();
    }
}
