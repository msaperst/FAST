package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.testpros.fast.utilities.Constants.WAITING_FOR_ELEMENT;

public class RemoteWebDriver implements WebDriver, JavascriptExecutor {

    org.openqa.selenium.remote.RemoteWebDriver seleniumRemoteWebDriver;
    Capabilities capabilities;
    DriverService service;
    MutableCapabilities options;
    CommandExecutor executor;
    URL remoteAddress;
    HttpClient.Factory httpClientFactory;
    AppiumServiceBuilder builder;
    int port;

    Reporter reporter = new Reporter(null);

    //wait times
    long waitTime = 5;  //TODO - need better ways to set/change these
    long pollTime = 50;  //TODO - need better ways to set/change these

    public org.openqa.selenium.remote.RemoteWebDriver getDriver() {
        return seleniumRemoteWebDriver;
    }

    public Capabilities getCapabilities() {
        return capabilities;
    }

    public DriverService getService() {
        return service;
    }

    public MutableCapabilities getOptions() {
        return options;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public URL getRemoteAddress() {
        return remoteAddress;
    }

    public HttpClient.Factory getHttpClientFactory() {
        return httpClientFactory;
    }

    public AppiumServiceBuilder getBuilder() {
        return builder;
    }

    public int getPort() {
        return port;
    }

    public Reporter getReporter() {
        return reporter;
    }

    String getDeviceName() {
        return "Remote " + Reporter.capitalizeFirstLetter(capabilities.getBrowserName());
    }

    String getDriverName() {
        return getDeviceName().replaceAll(" ", "") + "Driver";
    }

    Step setupStep() {
        return new Step("Launching new " + getDeviceName() + " instance",
                "New " + getDriverName() + " starts");
    }

    void passStep(Step step) {
        step.setPassed(getDriverName() + " started");
        if (reporter.getDriver() == null) {
            reporter = new Reporter(seleniumRemoteWebDriver);
        }
    }

    void failStep(Step step, Exception e) {
        step.setFailed("Unable to launch new " + getDeviceName() + " instance: " + e);
        if (reporter.getDriver() == null) {
            reporter = new Reporter(seleniumRemoteWebDriver);
        }
    }

    protected RemoteWebDriver() {
        //Do nothing, just for child implementations
    }

    public RemoteWebDriver(Capabilities capabilities) {
        this.capabilities = capabilities;
        Step step = setupStep();
        try {
            seleniumRemoteWebDriver = new org.openqa.selenium.remote.RemoteWebDriver(capabilities);
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
            seleniumRemoteWebDriver = new org.openqa.selenium.remote.RemoteWebDriver(executor, capabilities);
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
            seleniumRemoteWebDriver = new org.openqa.selenium.remote.RemoteWebDriver(remoteAddress, capabilities);
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
                "Expected URL to load");
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
            getDriver().findElement(by.getBy());
            return true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            // not doing any logging, as this is just a check, nothing to log
        }
        return false;
    }

    public void waitForElementPresent(By by) {
        if (!isElementPresent(by)) {
            // if it's not present, wait, and log that wait
            Step step = new Step(WAITING_FOR_ELEMENT + by.getBy() + "' to be present",
                    "Element is present");
            try {
                WebDriverWait wait = new WebDriverWait(getDriver(), waitTime, pollTime);
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by.getBy()));
                step.setPassed("Waited '" + step.getTime() + "' milliseconds for element to be present");
            } catch (TimeoutException e) {
                step.setFailed("After waiting '" + waitTime + "' seconds, element is not present");
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
        List<org.openqa.selenium.WebElement> elements = getDriver().findElements(by.getBy());
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
                "Window is no longer open");
        try {
            getDriver().close();
            step.setTime();
            if (getWindowHandles().contains(windowHandle)) {
                step.setFailed("Window is still open");
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
     * activity into the FAST reporter. Additionally, calling this method will set the end
     * time in the reporter. If you want the test to end later (or sooner), a separate call
     * to {@link Reporter#setEndTime()} must be made. If all windows are closed, and the
     * driver has exited, it will be considered a pass, otherwise a failure will be recorded,
     * with the reason for the failure.
     */
    @Override
    public void quit() {
        Step step = new Step("Destroying " + getDeviceName() + " instance",
                getDriverName() + " stops");
        try {
            getDriver().quit();
            step.setPassed(getDriverName() + " stopped");
        } catch (Exception e) {
            step.setFailed("Unable to destroy " + getDeviceName() + " instance: " + e);
        } finally {
            getReporter().addStep(step, false);
            getReporter().setEndTime();
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

    @Override
    public TargetLocator switchTo() {
        // not doing any logging, as this is just a check, nothing to log
        return new com.testpros.fast.RemoteTargetLocator(this, getDriver().switchTo(), getReporter());
    }

    @Override
    public Navigation navigate() {
        // not doing any logging, as this is just a check, nothing to log
        return new com.testpros.fast.RemoteNavigation(this, getDriver().navigate(), getReporter());
    }

    @Override
    public Options manage() {
        // not doing any logging, as this is just a check, nothing to log
        return new com.testpros.fast.RemoteWebDriverOptions(getDriver().manage(), getReporter());
    }

    /**
     * Executes JavaScript in the context of the currently selected frame or window. The script
     * fragment provided will be executed as the body of an anonymous function.
     *
     * <p>
     * Within the script, use <code>document</code> to refer to the current document. Note that local
     * variables will not be available once the script has finished executing, though global variables
     * will persist.
     *
     * <p>
     * If the script has a return value (i.e. if the script contains a <code>return</code> statement),
     * then the following steps will be taken:
     *
     * <ul>
     * <li>For an HTML element, this method returns a WebElement</li>
     * <li>For a decimal, a Double is returned</li>
     * <li>For a non-decimal number, a Long is returned</li>
     * <li>For a boolean, a Boolean is returned</li>
     * <li>For all other cases, a String is returned.</li>
     * <li>For an array, return a List&lt;Object&gt; with each object following the rules above. We
     * support nested lists.</li>
     * <li>For a map, return a Map&lt;String, Object&gt; with values following the rules above.</li>
     * <li>Unless the value is null or there is no return value, in which null is returned</li>
     * </ul>
     *
     * <p>
     * Arguments must be a number, a boolean, a String, WebElement, or a List of any combination of
     * the above. An exception will be thrown if the arguments do not meet these criteria. The
     * arguments will be made available to the JavaScript via the "arguments" magic variable, as if
     * the function were called via "Function.apply"
     * <p>
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     *
     * @param script The JavaScript to execute
     * @param args   The arguments to the script. May be empty
     * @return One of Boolean, Long, Double, String, List, Map or WebElement. Or null.
     */
    @Override
    public Object executeScript(String script, Object... args) {
        Step step = new Step("Executing script '" + script + "' with arguments: '" + args,
                "Script completes");
        try {
            Object object = getDriver().executeScript(script, args);
            step.setPassed("Script executed");
            return object;
        } catch (Exception e) {
            step.setFailed("Unable to execute the script: " + e);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }

    /**
     * Execute an asynchronous piece of JavaScript in the context of the currently selected frame or
     * window. Unlike executing {@link #executeScript(String, Object...) synchronous JavaScript},
     * scripts executed with this method must explicitly signal they are finished by invoking the
     * provided callback. This callback is always injected into the executed function as the last
     * argument.
     *
     * <p>
     * The first argument passed to the callback function will be used as the script's result. This
     * value will be handled as follows:
     *
     * <ul>
     * <li>For an HTML element, this method returns a WebElement</li>
     * <li>For a number, a Long is returned</li>
     * <li>For a boolean, a Boolean is returned</li>
     * <li>For all other cases, a String is returned.</li>
     * <li>For an array, return a List&lt;Object&gt; with each object following the rules above. We
     * support nested lists.</li>
     * <li>For a map, return a Map&lt;String, Object&gt; with values following the rules above.</li>
     * <li>Unless the value is null or there is no return value, in which null is returned</li>
     * </ul>
     *
     * <p>
     * The default timeout for a script to be executed is 0ms. In most cases, including the examples
     * below, one must set the script timeout
     * {@link Timeouts#setScriptTimeout(long, TimeUnit)}  beforehand
     * to a value sufficiently large enough.
     *
     *
     * <p>
     * Example #1: Performing a sleep in the browser under test. <pre>{@code
     *   long start = System.currentTimeMillis();
     *   ((JavascriptExecutor) driver).executeAsyncScript(
     *       "window.setTimeout(arguments[arguments.length - 1], 500);");
     *   System.out.println(
     *       "Elapsed time: " + System.currentTimeMillis() - start);
     * }</pre>
     *
     * <p>
     * Example #2: Synchronizing a test with an AJAX application: <pre>{@code
     *   WebElement composeButton = driver.findElement(By.id("compose-button"));
     *   composeButton.click();
     *   ((JavascriptExecutor) driver).executeAsyncScript(
     *       "var callback = arguments[arguments.length - 1];" +
     *       "mailClient.getComposeWindowWidget().onload(callback);");
     *   driver.switchTo().frame("composeWidget");
     *   driver.findElement(By.id("to")).sendKeys("bog@example.com");
     * }</pre>
     *
     * <p>
     * Example #3: Injecting a XMLHttpRequest and waiting for the result: <pre>{@code
     *   Object response = ((JavascriptExecutor) driver).executeAsyncScript(
     *       "var callback = arguments[arguments.length - 1];" +
     *       "var xhr = new XMLHttpRequest();" +
     *       "xhr.open('GET', '/resource/data.json', true);" +
     *       "xhr.onreadystatechange = function() {" +
     *       "  if (xhr.readyState == 4) {" +
     *       "    callback(xhr.responseText);" +
     *       "  }" +
     *       "};" +
     *       "xhr.send();");
     *   JsonObject json = new JsonParser().parse((String) response);
     *   assertEquals("cheese", json.get("food").getAsString());
     * }</pre>
     *
     * <p>
     * Script arguments must be a number, a boolean, a String, WebElement, or a List of any
     * combination of the above. An exception will be thrown if the arguments do not meet these
     * criteria. The arguments will be made available to the JavaScript via the "arguments"
     * variable.
     * <p>
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     *
     * @param script The JavaScript to execute.
     * @param args   The arguments to the script. May be empty.
     * @return One of Boolean, Long, String, List, Map, WebElement, or null.
     * @see Timeouts#setScriptTimeout(long, TimeUnit)
     */
    @Override
    public Object executeAsyncScript(String script, Object... args) {
        Step step = new Step("Executing asynchronous script '" + script + "' with arguments: '" + args,
                "Asynchronous script completes");
        try {
            Object object = getDriver().executeAsyncScript(script, args);
            step.setPassed("Asynchronous script executed");
            return object;
        } catch (Exception e) {
            step.setFailed("Unable to execute the asynchronous script: " + e);
        } finally {
            reporter.addStep(step);
        }
        return null;
    }
}
