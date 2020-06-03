package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver.*;

import java.util.concurrent.TimeUnit;

public class RemoteTimeouts implements Timeouts {

    private final Reporter reporter;
    private final Timeouts timeouts;

    protected RemoteTimeouts(Timeouts timeouts, Reporter reporter) {
        this.timeouts = timeouts;
        this.reporter = reporter;
    }

    /**
     * Specifies the amount of time the driver should wait when searching for an element if it is
     * not immediately present.
     * When searching for a single element, the driver should poll the page until the element has
     * been found, or this timeout expires before throwing a {@link NoSuchElementException}. When
     * searching for multiple elements, the driver should poll the page until at least one element
     * has been found or this timeout has expired.
     * Increasing the implicit wait timeout should be used judiciously as it will have an adverse
     * effect on test run time, especially when used with slower location strategies like XPath.
     * Note, that when using the FAST framework, instead of using this implicit wait, use the
     * {@link RemoteWebDriver#waitForElementPresent(By)} which adds logging. Note, that the specific
     * FAST wait is automatically used in {@link RemoteWebDriver#findElement(By)}, but not in
     * {@link RemoteWebDriver#findElements(By)}
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     * @param time The amount of time to wait.
     * @param unit The unit of measure for {@code time}.
     * @return A self reference.
     */
    @Deprecated
    @Override
    public Timeouts implicitlyWait(long time, TimeUnit unit) {
        Step step = new Step("Setting implicit wait to '" + time + " " + unit.toString().toLowerCase() + "'",
                "Implicit wait changed");
        try {
            timeouts.implicitlyWait(time, unit);
            step.setPassed("Updated implicit wait");
        } catch (Exception e) {
            step.setFailed("Unable to update implicit wait: " + e);
        } finally {
            reporter.addStep(step, false);
        }
        return this;
    }

    /**
     * Sets the amount of time to wait for an asynchronous script to finish execution before
     * throwing an error. If the timeout is negative, then the script will be allowed to run
     * indefinitely.
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     * @param time The timeout value.
     * @param unit The unit of time.
     * @return A self reference.
     * @see JavascriptExecutor#executeAsyncScript(String, Object...)
     */
    @Override
    public Timeouts setScriptTimeout(long time, TimeUnit unit) {
        Step step = new Step("Setting script timeout to '" + time + " " + unit.toString().toLowerCase() + "'",
                "Script timeout changed");
        try {
            timeouts.setScriptTimeout(time, unit);
            step.setPassed("Updated script timeout");
        } catch (Exception e) {
            step.setFailed("Unable to update script timeout: " + e);
        } finally {
            reporter.addStep(step, false);
        }
        return this;
    }

    /**
     * Sets the amount of time to wait for a page load to complete before throwing an error.
     * If the timeout is negative, page loads can be indefinite.
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     * @param time The timeout value.
     * @param unit The unit of time.
     * @return A Timeouts interface.
     */
    @Override
    public Timeouts pageLoadTimeout(long time, TimeUnit unit) {
        Step step = new Step("Setting page load timeout to '" + time + " " + unit.toString().toLowerCase() + "'",
                "Page load timeout changed");
        try {
            timeouts.pageLoadTimeout(time, unit);
            step.setPassed("Updated page load timeout");
        } catch (Exception e) {
            step.setFailed("Unable to update page load timeout: " + e);
        } finally {
            reporter.addStep(step, false);
        }
        return this;
    }
}
