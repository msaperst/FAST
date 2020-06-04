package integration;

import com.testpros.fast.WebDriver;
import com.testpros.fast.reporter.FailedStepException;
import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import com.testpros.fast.reporter.Step.Status;
import org.openqa.selenium.Cookie;
import org.testng.annotations.BeforeMethod;
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
        Step step = reporter.getSteps().get(2);
        assertEquals(step.getNumber(), 3);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Adding cookie '" + cookieString + "' to browser session");
        assertEquals(step.getExpected(), "Cookie added");
        assertEquals(step.getActual(), "Successfully added cookie to browser session");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void addCookieNoDriverTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        try {
            driver.manage().addCookie(cookie);
        } finally {
            Step step = reporter.getSteps().get(1);
            assertEquals(step.getNumber(), 2);
            assertNotEquals(step.getTime(), 0.0);
            assertEquals(step.getAction(), "Adding cookie '" + cookieString + "' to browser session");
            assertEquals(step.getExpected(), "Cookie added");
            assertTrue(step.getActual().startsWith("Unable to add cookie to browser session: org.openqa.selenium." +
                    "InvalidCookieDomainException: invalid cookie domain\n  (Session info: "));
            assertEquals(step.getStatus(), Status.FAIL);
            assertNull(step.getRequest());
            assertNull(step.getResponse());
            assertNotNull(step.getScreenshot());
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
        Step step = reporter.getSteps().get(3);
        assertEquals(step.getNumber(), 4);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Removing cookie named 'foo' from browser session");
        assertEquals(step.getExpected(), "Cookie deleted");
        assertEquals(step.getActual(), "Successfully removed cookie from browser session");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void deleteCookieNamedNoDriverTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.manage().deleteCookieNamed("foo");
        } finally {
            Step step = reporter.getSteps().get(2);
            assertEquals(step.getNumber(), 3);
            assertNotEquals(step.getTime(), 0.0);
            assertEquals(step.getAction(), "Removing cookie named 'foo' from browser session");
            assertEquals(step.getExpected(), "Cookie deleted");
            assertTrue(step.getActual().startsWith("Unable to delete cookie from browser session: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(step.getStatus(), Status.FAIL);
            assertNull(step.getRequest());
            assertNull(step.getResponse());
            assertNull(step.getScreenshot());
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
        Step step = reporter.getSteps().get(3);
        assertEquals(step.getNumber(), 4);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Removing cookie '" + cookieString + "' from browser session");
        assertEquals(step.getExpected(), "Cookie deleted");
        assertEquals(step.getActual(), "Successfully removed cookie from browser session");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void deleteCookieNoDriverTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.manage().deleteCookie(cookie);
        } finally {
            Step step = reporter.getSteps().get(2);
            assertEquals(step.getNumber(), 3);
            assertNotEquals(step.getTime(), 0.0);
            assertEquals(step.getAction(), "Removing cookie '" + cookieString + "' from browser session");
            assertEquals(step.getExpected(), "Cookie deleted");
            assertTrue(step.getActual().startsWith("Unable to delete cookie from browser session: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(step.getStatus(), Status.FAIL);
            assertNull(step.getRequest());
            assertNull(step.getResponse());
            assertNull(step.getScreenshot());
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
        Step step = reporter.getSteps().get(3);
        assertEquals(step.getNumber(), 4);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Removing all cookies from browser session");
        assertEquals(step.getExpected(), "All cookies deleted");
        assertEquals(step.getActual(), "Successfully removed all cookies from browser session");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void deleteAllCookiesNoDriverTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.manage().deleteAllCookies();
        } finally {
            Step step = reporter.getSteps().get(2);
            assertEquals(step.getNumber(), 3);
            assertNotEquals(step.getTime(), 0.0);
            assertEquals(step.getAction(), "Removing all cookies from browser session");
            assertEquals(step.getExpected(), "All cookies deleted");
            assertTrue(step.getActual().startsWith("Unable to delete all cookies from browser session: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(step.getStatus(), Status.FAIL);
            assertNull(step.getRequest());
            assertNull(step.getResponse());
            assertNull(step.getScreenshot());
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
    public void imeTest() {
        //TODO
    }

    @Test
    public void logsTest() {
        //TODO
    }
}
