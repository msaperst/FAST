package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import org.openqa.selenium.WebDriver.Navigation;

import java.net.URL;

public class RemoteNavigation implements Navigation {

    private final Reporter reporter;
    private final com.testpros.fast.RemoteWebDriver driver;
    private final Navigation navigation;

    protected RemoteNavigation(com.testpros.fast.RemoteWebDriver driver, Navigation navigation, Reporter reporter) {
        this.driver = driver;
        this.navigation = navigation;
        this.reporter = reporter;
    }

    /**
     * Move back a single "item" in the browser's history. Additionally, this will log the activity
     * into the FAST reporter. If unable to navigate backwards, it is considered a
     * pass, otherwise it will cause a failure and the reason for the failure will be recorded.
     */
    @Override
    public void back() {
        Step step = new Step("Navigating to the previous item in the browser's history",
                "Navigated to the previous browser page");
        try {
            navigation.back();
            step.setPassed("Navigated to page '" + driver.getCurrentUrl() + "'");
        } catch (Exception e) {
            step.setFailed("Unable to navigate back one page: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Move a single "item" forward in the browser's history. Does nothing if we are on the latest
     * page viewed. Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     */
    @Override
    public void forward() {
        Step step = new Step("Navigating to the next item in the browser's history",
                "Navigated to the next browser page");
        try {
            navigation.forward();
            step.setPassed("Navigated to page '" + driver.getCurrentUrl() + "'");
        } catch (Exception e) {
            step.setFailed("Unable to navigate forward one page: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Load a new web page in the current browser window. This is done using an HTTP GET operation,
     * and the method will block until the load is complete. This will follow redirects issued
     * either by the server or as a meta-redirect from within the returned HTML. Should a
     * meta-redirect "rest" for any duration of time, it is best to wait until this timeout is over,
     * since should the underlying page change whilst your test is executing the results of future
     * calls against this interface will be against the freshly loaded page.
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     *
     * @param url The URL to load. It is best to use a fully qualified URL
     */
    @Override
    public void to(String url) {
        Step step = new Step("Navigating to URL '" + url + "'",
                "Browser loaded URL");
        try {
            navigation.to(url);
            step.setActual("Navigated to URL '" + driver.getCurrentUrl() + "'");
            if (!url.equals(driver.getCurrentUrl())) {
                step.setFailed();
            } else {
                step.setPassed();
            }
        } catch (Exception e) {
            step.setFailed("Unable to navigate to URL: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Overloaded version of {@link #to(String)} that makes it easy to pass in a URL.
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     *
     * @param url URL
     */
    @Override
    public void to(URL url) {
        Step step = new Step("Navigating to URL '" + url + "'",
                "Browser loaded URL");
        try {
            navigation.to(url);
            step.setActual("Navigated to URL '" + driver.getCurrentUrl() + "'");
            if (!url.toString().equals(driver.getCurrentUrl())) {
                step.setFailed();
            } else {
                step.setPassed();
            }
        } catch (Exception e) {
            step.setFailed("Unable to navigate to URL: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Refresh the current page. Additionally, this will log the activity into the FAST reporter.
     * If any errors are encountered it is considered a failure, and the error will be recorded,
     * otherwise it will be considered a pass.
     */
    @Override
    public void refresh() {
        Step step = new Step("Refreshing the current browser page", "Browser page displayed");
        try {
            navigation.refresh();
            step.setPassed("Refreshed the page");
        } catch (Exception e) {
            step.setFailed("Unable to refresh the page: " + e);
        } finally {
            reporter.addStep(step);
        }
    }
}
