package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver.ImeHandler;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.logging.Logs;

import java.util.Set;

public class RemoteWebDriverOptions implements Options {

    private final Reporter reporter;
    private final Options options;

    protected RemoteWebDriverOptions(Options options, Reporter reporter) {
        this.options = options;
        this.reporter = reporter;
    }

    /**
     * Add a specific cookie. If the cookie's domain name is left blank, it is assumed that the
     * cookie is meant for the domain of the current document.
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     *
     * @param cookie The cookie to add.
     */
    @Override
    public void addCookie(Cookie cookie) {
        Step step = new Step("Adding cookie '" + cookie + "' to browser session",
                "Cookie added");
        try {
            options.addCookie(cookie);
            step.setTime();
            if (cookie.equals(options.getCookieNamed(cookie.getName()))) {
                step.setPassed("Successfully added cookie to browser session");
            } else {
                step.setFailed("Failed to add cookie to browser session");
            }
        } catch (Exception e) {
            step.setFailed("Unable to add cookie to browser session: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Delete the named cookie from the current domain. This is equivalent to setting the named
     * cookie's expiry date to some time in the past.
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     *
     * @param name The name of the cookie to delete
     */
    @Override
    public void deleteCookieNamed(String name) {
        Step step = new Step("Removing cookie named '" + name + "' from browser session",
                "Cookie deleted");
        try {
            options.deleteCookieNamed(name);
            step.setTime();
            if (options.getCookieNamed(name) != null) {
                step.setFailed("Failed to remove cookie from browser session");
            } else {
                step.setPassed("Successfully removed cookie from browser session");
            }
        } catch (Exception e) {
            step.setFailed("Unable to delete cookie from browser session: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Delete a cookie from the browser's "cookie jar". The domain of the cookie will be ignored.
     * Additionally, this will log the activity into the FAST reporter. If any errors
     * are encountered it is considered a failure, and the error will be recorded, otherwise it
     * will be considered a pass.
     *
     * @param cookie nom nom nom
     */
    @Override
    public void deleteCookie(Cookie cookie) {
        Step step = new Step("Removing cookie '" + cookie + "' from browser session",
                "Cookie deleted");
        try {
            options.deleteCookie(cookie);
            step.setTime();
            if (options.getCookieNamed(cookie.getName()) != null) {
                step.setFailed("Failed to remove cookie from browser session");
            } else {
                step.setPassed("Successfully removed cookie from browser session");
            }
        } catch (Exception e) {
            step.setFailed("Unable to delete cookie from browser session: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Delete all the cookies for the current domain. Additionally, this will log the activity into
     * the FAST reporter. If any errors are encountered it is considered a failure, and the error
     * will be recorded, otherwise it will be considered a pass.
     */
    @Override
    public void deleteAllCookies() {
        Step step = new Step("Removing all cookies from browser session",
                "All cookies deleted");
        try {
            options.deleteAllCookies();
            step.setTime();
            if (!options.getCookies().isEmpty()) {
                step.setFailed("Failed to remove all cookies from browser session");
            } else {
                step.setPassed("Successfully removed all cookies from browser session");
            }
        } catch (Exception e) {
            step.setFailed("Unable to delete all cookies from browser session: " + e);
        } finally {
            reporter.addStep(step);
        }
    }

    /**
     * Get all the cookies for the current domain. This is the equivalent of calling
     * "document.cookie" and parsing the result
     *
     * @return A Set of cookies for the current domain.
     */
    @Override
    public Set<Cookie> getCookies() {
        // not doing any logging, as this is just a check, nothing to log
        return options.getCookies();
    }

    /**
     * Get a cookie with a given name.
     *
     * @param name the name of the cookie
     * @return the cookie, or null if no cookie with the given name is present
     */
    @Override
    public Cookie getCookieNamed(String name) {
        // not doing any logging, as this is just a check, nothing to log
        return options.getCookieNamed(name);
    }

    /**
     * @return the interface for managing driver timeouts.
     */
    @Override
    public Timeouts timeouts() {
        // not doing any logging, as this is just a check, nothing to log
        return new RemoteTimeouts(options.timeouts(), reporter);
    }

    /**
     * @return the interface for controlling IME engines to generate complex-script input.
     */
    @Override
    public ImeHandler ime() {
        // not doing any logging, as this is just a check, nothing to log
        return new RemoteInputMethodManager(options.ime(), reporter);
    }

    /**
     * @return the interface for managing the current window.
     */
    @Override
    public Window window() {
        // not doing any logging, as this is just a check, nothing to log
        return new RemoteWindow(options.window(), reporter);
    }

    /**
     * Gets the {@link Logs} interface used to fetch different types of logs.
     *
     * <p>To set the logging preferences {@link LoggingPreferences}.
     *
     * @return A Logs interface.
     */
    @Override
    public Logs logs() {
        // not doing any logging, as this is just a check, nothing to log
        return options.logs();
    }
}
