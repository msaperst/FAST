package integration;

import com.testpros.fast.WebDriver;
import com.testpros.fast.reporter.FailedStepException;
import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import com.testpros.fast.reporter.Step.Status;
import org.openqa.selenium.Cookie;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.*;

public class RemoteWebDriverOptionsIT extends FastTestBase {

    private final Cookie cookie = new Cookie("foo", "bar");
    private final String cookieString = "foo=bar; path=/";

    @Test
    public void addCookieTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get("https://www.google.com/");
        driver.manage().addCookie(cookie);
        //assert cookie exists
        assertEquals(driver.manage().getCookieNamed("foo").getValue(), "bar");
        //assert reporter has proper information on cookie
        Step cookieStep = reporter.getSteps().get(2);
        assertEquals(cookieStep.getNumber(), 3);
        assertNotEquals(cookieStep.getTime(), 0.0);
        assertEquals(cookieStep.getAction(), "Adding cookie '" + cookieString + "' to browser session");
        assertEquals(cookieStep.getExpected(), "Cookie '" + cookieString + "' successfully added");
        assertEquals(cookieStep.getActual(), "Successfully added cookie to browser session");
        assertEquals(cookieStep.getStatus(), Status.PASS);
        assertNull(cookieStep.getRequest());
        assertNull(cookieStep.getResponse());
        assertNotNull(cookieStep.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void addCookieFailedTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        try {
            driver.manage().addCookie(cookie);
        } finally {
            Step cookieStep = reporter.getSteps().get(1);
            assertEquals(cookieStep.getNumber(), 2);
            assertNotEquals(cookieStep.getTime(), 0.0);
            assertEquals(cookieStep.getAction(), "Adding cookie '" + cookieString + "' to browser session");
            assertEquals(cookieStep.getExpected(), "Cookie '" + cookieString + "' successfully added");
            assertTrue(cookieStep.getActual().startsWith("Unable to add cookie to browser session: org.openqa.selenium." +
                    "InvalidCookieDomainException: invalid cookie domain\n  (Session info: "));
            assertEquals(cookieStep.getStatus(), Status.FAIL);
            assertNull(cookieStep.getRequest());
            assertNull(cookieStep.getResponse());
            assertNotNull(cookieStep.getScreenshot());
        }
    }

    @Test
    public void deleteCookieNamedTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get("https://www.google.com/");
        driver.manage().addCookie(new Cookie("foo", "bar"));
        driver.manage().deleteCookieNamed("foo");
        //assert cookie exists
        assertNull(driver.manage().getCookieNamed("foo"));
        //assert reporter has proper information on cookie
        Step cookieStep = reporter.getSteps().get(3);
        assertEquals(cookieStep.getNumber(), 4);
        assertNotEquals(cookieStep.getTime(), 0.0);
        assertEquals(cookieStep.getAction(), "Removing cookie named 'foo' from browser session");
        assertEquals(cookieStep.getExpected(), "Cookie named 'foo' successfully deleted");
        assertEquals(cookieStep.getActual(), "Successfully removed cookie from browser session");
        assertEquals(cookieStep.getStatus(), Status.PASS);
        assertNull(cookieStep.getRequest());
        assertNull(cookieStep.getResponse());
        assertNotNull(cookieStep.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void deleteCookieNamedFailedTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.manage().deleteCookieNamed("foo");
        } finally {
            Step cookieStep = reporter.getSteps().get(2);
            assertEquals(cookieStep.getNumber(), 3);
            assertNotEquals(cookieStep.getTime(), 0.0);
            assertEquals(cookieStep.getAction(), "Removing cookie named 'foo' from browser session");
            assertEquals(cookieStep.getExpected(), "Cookie named 'foo' successfully deleted");
            assertTrue(cookieStep.getActual().startsWith("Unable to delete cookie from browser session: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(cookieStep.getStatus(), Status.FAIL);
            assertNull(cookieStep.getRequest());
            assertNull(cookieStep.getResponse());
            assertNull(cookieStep.getScreenshot());
        }
    }

    @Test
    public void deleteCookieTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get("https://www.google.com/");
        driver.manage().addCookie(cookie);
        driver.manage().deleteCookie(cookie);
        //assert cookie exists
        assertNull(driver.manage().getCookieNamed("foo"));
        //assert reporter has proper information on cookie
        Step cookieStep = reporter.getSteps().get(3);
        assertEquals(cookieStep.getNumber(), 4);
        assertNotEquals(cookieStep.getTime(), 0.0);
        assertEquals(cookieStep.getAction(), "Removing cookie '" + cookieString + "' from browser session");
        assertEquals(cookieStep.getExpected(), "Cookie '" + cookieString + "' successfully deleted");
        assertEquals(cookieStep.getActual(), "Successfully removed cookie from browser session");
        assertEquals(cookieStep.getStatus(), Status.PASS);
        assertNull(cookieStep.getRequest());
        assertNull(cookieStep.getResponse());
        assertNotNull(cookieStep.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void deleteCookieFailedTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.manage().deleteCookie(cookie);
        } finally {
            Step cookieStep = reporter.getSteps().get(2);
            assertEquals(cookieStep.getNumber(), 3);
            assertNotEquals(cookieStep.getTime(), 0.0);
            assertEquals(cookieStep.getAction(), "Removing cookie '" + cookieString + "' from browser session");
            assertEquals(cookieStep.getExpected(), "Cookie '" + cookieString + "' successfully deleted");
            assertTrue(cookieStep.getActual().startsWith("Unable to delete cookie from browser session: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(cookieStep.getStatus(), Status.FAIL);
            assertNull(cookieStep.getRequest());
            assertNull(cookieStep.getResponse());
            assertNull(cookieStep.getScreenshot());
        }
    }

    @Test
    public void deleteAllCookiesTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get("https://www.google.com/");
        driver.manage().addCookie(cookie);
        driver.manage().deleteAllCookies();
        //assert cookie exists
        assertEquals(driver.manage().getCookies().size(), 0);
        //assert reporter has proper information on cookie
        Step cookieStep = reporter.getSteps().get(3);
        assertEquals(cookieStep.getNumber(), 4);
        assertNotEquals(cookieStep.getTime(), 0.0);
        assertEquals(cookieStep.getAction(), "Removing all cookies from browser session");
        assertEquals(cookieStep.getExpected(), "All cookies successfully deleted");
        assertEquals(cookieStep.getActual(), "Successfully removed all cookies from browser session");
        assertEquals(cookieStep.getStatus(), Status.PASS);
        assertNull(cookieStep.getRequest());
        assertNull(cookieStep.getResponse());
        assertNotNull(cookieStep.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void deleteAllCookiesFailedTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.manage().deleteAllCookies();
        } finally {
            Step cookieStep = reporter.getSteps().get(2);
            assertEquals(cookieStep.getNumber(), 3);
            assertNotEquals(cookieStep.getTime(), 0.0);
            assertEquals(cookieStep.getAction(), "Removing all cookies from browser session");
            assertEquals(cookieStep.getExpected(), "All cookies successfully deleted");
            assertTrue(cookieStep.getActual().startsWith("Unable to delete all cookies from browser session: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(cookieStep.getStatus(), Status.FAIL);
            assertNull(cookieStep.getRequest());
            assertNull(cookieStep.getResponse());
            assertNull(cookieStep.getScreenshot());
        }
    }

    @Test
    public void getCookiesTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get("https://www.google.com/");
        driver.manage().deleteAllCookies();
        assertEquals(driver.manage().getCookies().size(), 0);
        driver.manage().addCookie(cookie);
        Set<Cookie> cookies = driver.manage().getCookies();
        assertEquals(cookies.size(), 1);
        assertEquals(cookies.iterator().next(), cookie);
        //assert reporter doesn't show getter information
        assertEquals(reporter.getSteps().size(), 4);
    }

    @Test
    public void getCookieNamedTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get("https://www.google.com/");
        driver.manage().addCookie(cookie);
        assertEquals(driver.manage().getCookieNamed("foo"), cookie);
        //assert reporter doesn't show getter information
        assertEquals(reporter.getSteps().size(), 3);
    }

    @Test
    public void timeoutsTest() {
        //TODO
    }

    @Test
    public void imeTest() {
        //TODO
    }

    @Test
    public void logsTest() {
        //TODO
    }
}
