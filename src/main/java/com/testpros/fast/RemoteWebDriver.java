package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import com.testpros.fast.reporter.Step.Status;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RemoteWebDriver extends org.openqa.selenium.remote.RemoteWebDriver implements WebDriver {

    org.openqa.selenium.remote.RemoteWebDriver remoteWebDriver;
    Capabilities capabilities;
    Reporter reporter = new Reporter(null);

    //wait times
    long waitTime = 5;
    long pollTime = 50;

    public Reporter getReporter() {
        return reporter;
    }

    public org.openqa.selenium.remote.RemoteWebDriver getDriver() {
        return remoteWebDriver;
    }

    String getDeviceName() {
        return "Remote " + Reporter.capitalizeFirstLetter(capabilities.getBrowserName());
    }

    String getDriverName() {
        return getDeviceName().replaceAll(" ", "") + "Driver";
    }

    Step setupStep() {
        return new Step("Launching new " + getDeviceName() + " instance",
                "New " + getDriverName() + " successfully starts");
    }

    void passStep(Step step) {
        step.setPassed(getDriverName() + " successfully started");
        if (reporter.getDriver() == null) {
            reporter = new Reporter(remoteWebDriver);
        }
    }

    void failStep(Step step, Exception e) {
        step.setFailed("Unable to launch new " + getDeviceName() + " instance: " + e);
        if (reporter.getDriver() == null) {
            reporter = new Reporter(remoteWebDriver);
        }
    }

    protected RemoteWebDriver() {
        //Do nothing, just for child implementations
    }

    public RemoteWebDriver(Capabilities capabilities) {
        this.capabilities = capabilities;
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.remote.RemoteWebDriver(capabilities);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public RemoteWebDriver(CommandExecutor executor, Capabilities capabilities) {
        this.capabilities = capabilities;
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.remote.RemoteWebDriver(executor, capabilities);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public RemoteWebDriver(URL remoteAddress, Capabilities capabilities) {
        this.capabilities = capabilities;
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.remote.RemoteWebDriver(remoteAddress, capabilities);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
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
    @Override
    public void get(String url) {
        Step step = new Step("Loading URL of '" + url + "'",
                "Expected URL of '" + url + "' to load");
        try {
            getDriver().get(url);
            step.setActual("Loaded URL of '" + getCurrentUrl() + "'");
            if (!url.equals(getCurrentUrl())) {
                step.setFailed();
            } else {
                step.setPassed();
            }
        } catch (Exception e) {
            step.setFailed("Unable to load URL: " + e);
        } finally {
            getReporter().addStep(step);
        }
    }

    @Override
    public String getCurrentUrl() {
        // not doing any logging, as this is just a check, nothing to log
        return getDriver().getCurrentUrl();
    }

    @Override
    public String getTitle() {
        // not doing any logging, as this is just a check, nothing to log
        return getDriver().getTitle();
    }

    /**
     * Checks to see if the desired element is present or not
     *
     * @param by
     * @return
     */
    public boolean isElementPresent(By by) {
        try {
            getDriver().findElement(by);
            return true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            // not doing any logging, as this is just a check, nothing to log
        }
        return false;
    }

    public void waitForElementPresent(By by) {
        if (!isElementPresent(by)) {
            // if it's not present, wait, and log that wait
            Step step = new Step("Waiting for element '" + by + "' to be present",
                    "Element '" + by + "' is present");
            try {
                WebDriverWait wait = new WebDriverWait(getDriver(), waitTime, pollTime);
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
                step.setPassed("Waited '" + step.getTime() + "' milliseconds for element '" + by + "' to be present");
            } catch (TimeoutException e) {
                step.setFailed("After waiting '" + waitTime + "' seconds, element '" + by + "' is not present");
            } finally {
                getReporter().addStep(step);
            }
        }
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
    @Override
    public List<org.openqa.selenium.WebElement> findElements(org.openqa.selenium.By by) {
        return getDriver().findElements(by);
    }

    /**
     * @param by The Selenium locating mechanism
     * @return The first matching element on the current page
     * @deprecated do not use this with FAST, instead, use the one with
     * the FAST By class. Only use this if you want Selenium WebElements
     * returned, which will not provide FAST functionality
     */
    @Deprecated
    @Override
    public org.openqa.selenium.WebElement findElement(org.openqa.selenium.By by) {
        return getDriver().findElement(by);
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
        List<org.openqa.selenium.WebElement> elements = getDriver().findElements(by);
        int counter = 1;
        for (org.openqa.selenium.WebElement element : elements) {
            webElements.add(new WebElement(this, element, counter));
            counter++;
        }
        return webElements;
    }

    public WebElement findElement(By by) {
        // not doing any logging, as this is just a check, nothing to log
        return new WebElement(this, by);
    }

    @Override
    public String getPageSource() {
        // not doing any logging, as this is just a check, nothing to log
        return getDriver().getPageSource();
    }

    /**
     * Close the current window, quitting the browser if it's the last window currently open.
     * Additionally, this will log the activity into the FAST reporter. If current window is
     * closed, it will be considered a pass, otherwise a failure will be recorded,
     * with the reason for the failure.
     */
    @Override
    public void close() {
        String windowHandle = getWindowHandle();
        Step step = new Step("Closing current window with handle '" + windowHandle + "'",
                "Window with handle '" + windowHandle + "' is no longer open");
        try {
            getDriver().close();
            step.setTime();
            if (getWindowHandles().contains(windowHandle)) {
                step.setFailed("Window with handle '" + windowHandle + "' is still open");
            } else {
                step.setPassed("Window successfully closed");
            }
        } catch (Exception e) {
            step.setFailed("Unable to close window: " + e);
        } finally {
            getReporter().addStep(step);
        }
    }

    /**
     * Quits this driver, closing every associated window. Additionally, this will log the
     * activity into the FAST reporter. If all windows are closed, and the driver has exited,
     * it will be considered a pass, otherwise a failure will be recorded, with the
     * reason for the failure.
     */
    @Override
    public void quit() {
        Step step = new Step("Destroying " + getDeviceName() + " instance",
                getDriverName() + " successfully stops");
        try {
            getDriver().quit();
            step.setPassed(getDriverName() + " successfully stopped");
        } catch (Exception e) {
            step.setFailed("Unable to destroy " + getDeviceName() + " instance: " + e);
        } finally {
            getReporter().addStep(step, false);
        }
    }

    @Override
    public Set<String> getWindowHandles() {
        // not doing any logging, as this is just a check, nothing to log
        return getDriver().getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        // not doing any logging, as this is just a check, nothing to log
        return getDriver().getWindowHandle();
    }

    //TODO - need to dive into this
    @Override
    public TargetLocator switchTo() {
        // not doing any logging, as this is just a check, nothing to log
        return new com.testpros.fast.RemoteTargetLocator(this, getDriver().switchTo(), getReporter());
    }

    //TODO - need to dive into this
    @Override
    public Navigation navigate() {
        // not doing any logging, as this is just a check, nothing to log
        return getDriver().navigate();
    }

    //TODO - need to dive into this
    @Override
    public Options manage() {
        // not doing any logging, as this is just a check, nothing to log
        return getDriver().manage();
    }
}
