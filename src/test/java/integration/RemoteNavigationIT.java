package integration;

import com.testpros.fast.WebDriver;
import com.testpros.fast.reporter.FailedStepException;
import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import com.testpros.fast.reporter.Step.Status;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.*;

public class RemoteNavigationIT extends FastTestBase {

    @Test
    public void backTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get("https://www.google.com/");
        driver.navigate().back();
        //assert at proper url
        assertEquals(driver.getCurrentUrl(), "data:,");
        //assert reporter has proper information on cookie
        Step cookieStep = reporter.getSteps().get(2);
        assertEquals(cookieStep.getNumber(), 3);
        assertNotEquals(cookieStep.getTime(), 0.0);
        assertEquals(cookieStep.getAction(), "Navigating to the previous item in the browser's history");
        assertEquals(cookieStep.getExpected(), "Navigated to the previous browser page");
        assertEquals(cookieStep.getActual(), "Navigated to page 'data:,'");
        assertEquals(cookieStep.getStatus(), Status.PASS);
        assertNull(cookieStep.getRequest());
        assertNull(cookieStep.getResponse());
        assertNotNull(cookieStep.getScreenshot());
    }

    @Test
    public void backNoBackTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.navigate().back();
        //assert at proper url
        assertEquals(driver.getCurrentUrl(), "data:,");
        //assert reporter has proper information on cookie
        Step cookieStep = reporter.getSteps().get(1);
        assertEquals(cookieStep.getNumber(), 2);
        assertNotEquals(cookieStep.getTime(), 0.0);
        assertEquals(cookieStep.getAction(), "Navigating to the previous item in the browser's history");
        assertEquals(cookieStep.getExpected(), "Navigated to the previous browser page");
        assertEquals(cookieStep.getActual(), "Navigated to page 'data:,'");
        assertEquals(cookieStep.getStatus(), Status.PASS);
        assertNull(cookieStep.getRequest());
        assertNull(cookieStep.getResponse());
        assertNotNull(cookieStep.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void backNoDriverTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.navigate().back();
        } finally {
            //assert reporter has proper information on cookie
            Step cookieStep = reporter.getSteps().get(2);
            assertEquals(cookieStep.getNumber(), 3);
            assertNotEquals(cookieStep.getTime(), 0.0);
            assertEquals(cookieStep.getAction(), "Navigating to the previous item in the browser's history");
            assertEquals(cookieStep.getExpected(), "Navigated to the previous browser page");
            assertTrue(cookieStep.getActual().startsWith("Unable to navigate back one page: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(cookieStep.getStatus(), Status.FAIL);
            assertNull(cookieStep.getRequest());
            assertNull(cookieStep.getResponse());
            assertNull(cookieStep.getScreenshot());
        }
    }

    @Test
    public void forwardTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get("https://www.google.com/");
        driver.navigate().back();
        driver.navigate().forward();
        //assert at proper url
        assertEquals(driver.getCurrentUrl(), "https://www.google.com/");
        //assert reporter has proper information on cookie
        Step cookieStep = reporter.getSteps().get(3);
        assertEquals(cookieStep.getNumber(), 4);
        assertNotEquals(cookieStep.getTime(), 0.0);
        assertEquals(cookieStep.getAction(), "Navigating to the next item in the browser's history");
        assertEquals(cookieStep.getExpected(), "Navigated to the next browser page");
        assertEquals(cookieStep.getActual(), "Navigated to page 'https://www.google.com/'");
        assertEquals(cookieStep.getStatus(), Status.PASS);
        assertNull(cookieStep.getRequest());
        assertNull(cookieStep.getResponse());
        assertNotNull(cookieStep.getScreenshot());
    }

    @Test
    public void forwardNoForwardTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.navigate().forward();
        //assert at proper url
        assertEquals(driver.getCurrentUrl(), "data:,");
        //assert reporter has proper information on cookie
        Step cookieStep = reporter.getSteps().get(1);
        assertEquals(cookieStep.getNumber(), 2);
        assertNotEquals(cookieStep.getTime(), 0.0);
        assertEquals(cookieStep.getAction(), "Navigating to the next item in the browser's history");
        assertEquals(cookieStep.getExpected(), "Navigated to the next browser page");
        assertEquals(cookieStep.getActual(), "Navigated to page 'data:,'");
        assertEquals(cookieStep.getStatus(), Status.PASS);
        assertNull(cookieStep.getRequest());
        assertNull(cookieStep.getResponse());
        assertNotNull(cookieStep.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void forwardNoDriverTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.navigate().forward();
        } finally {
            Step cookieStep = reporter.getSteps().get(2);
            assertEquals(cookieStep.getNumber(), 3);
            assertNotEquals(cookieStep.getTime(), 0.0);
            assertEquals(cookieStep.getAction(), "Navigating to the next item in the browser's history");
            assertEquals(cookieStep.getExpected(), "Navigated to the next browser page");
            assertTrue(cookieStep.getActual().startsWith("Unable to navigate forward one page: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(cookieStep.getStatus(), Status.FAIL);
            assertNull(cookieStep.getRequest());
            assertNull(cookieStep.getResponse());
            assertNull(cookieStep.getScreenshot());
        }
    }

    @Test
    public void toStringTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.navigate().to("https://www.google.com/");
        //assert at proper url
        assertEquals(driver.getCurrentUrl(), "https://www.google.com/");
        //assert reporter has proper information on cookie
        Step cookieStep = reporter.getSteps().get(1);
        assertEquals(cookieStep.getNumber(), 2);
        assertNotEquals(cookieStep.getTime(), 0.0);
        assertEquals(cookieStep.getAction(), "Navigating to URL 'https://www.google.com/'");
        assertEquals(cookieStep.getExpected(), "Browser loaded URL");
        assertEquals(cookieStep.getActual(), "Navigated to URL 'https://www.google.com/'");
        assertEquals(cookieStep.getStatus(), Status.PASS);
        assertNull(cookieStep.getRequest());
        assertNull(cookieStep.getResponse());
        assertNotNull(cookieStep.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void toStringBadUrlTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        try {
            driver.navigate().to("https://www.google.com");
        } finally {
            Step cookieStep = reporter.getSteps().get(1);
            assertEquals(cookieStep.getNumber(), 2);
            assertNotEquals(cookieStep.getTime(), 0.0);
            assertEquals(cookieStep.getAction(), "Navigating to URL 'https://www.google.com'");
            assertEquals(cookieStep.getExpected(), "Browser loaded URL");
            assertEquals(cookieStep.getActual(), "Navigated to URL 'https://www.google.com/'");
            assertEquals(cookieStep.getStatus(), Status.FAIL);
            assertNull(cookieStep.getRequest());
            assertNull(cookieStep.getResponse());
            assertNotNull(cookieStep.getScreenshot());
        }
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void toStringNoDriverTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.navigate().to("https://www.google.com/");
        } finally {
            Step cookieStep = reporter.getSteps().get(2);
            assertEquals(cookieStep.getNumber(), 3);
            assertNotEquals(cookieStep.getTime(), 0.0);
            assertEquals(cookieStep.getAction(), "Navigating to URL 'https://www.google.com/'");
            assertEquals(cookieStep.getExpected(), "Browser loaded URL");
            assertTrue(cookieStep.getActual().startsWith("Unable to navigate to URL: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(cookieStep.getStatus(), Status.FAIL);
            assertNull(cookieStep.getRequest());
            assertNull(cookieStep.getResponse());
            assertNull(cookieStep.getScreenshot());
        }
    }

    @Test
    public void toURLTest() throws MalformedURLException {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.navigate().to(new URL("https://www.google.com/"));
        //assert at proper url
        assertEquals(driver.getCurrentUrl(), "https://www.google.com/");
        //assert reporter has proper information on cookie
        Step cookieStep = reporter.getSteps().get(1);
        assertEquals(cookieStep.getNumber(), 2);
        assertNotEquals(cookieStep.getTime(), 0.0);
        assertEquals(cookieStep.getAction(), "Navigating to URL 'https://www.google.com/'");
        assertEquals(cookieStep.getExpected(), "Browser loaded URL");
        assertEquals(cookieStep.getActual(), "Navigated to URL 'https://www.google.com/'");
        assertEquals(cookieStep.getStatus(), Status.PASS);
        assertNull(cookieStep.getRequest());
        assertNull(cookieStep.getResponse());
        assertNotNull(cookieStep.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void toURLBadUrlTest() throws MalformedURLException {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        try {
            driver.navigate().to(new URL("https://www.google.com"));
        } finally {
            Step cookieStep = reporter.getSteps().get(1);
            assertEquals(cookieStep.getNumber(), 2);
            assertNotEquals(cookieStep.getTime(), 0.0);
            assertEquals(cookieStep.getAction(), "Navigating to URL 'https://www.google.com'");
            assertEquals(cookieStep.getExpected(), "Browser loaded URL");
            assertEquals(cookieStep.getActual(), "Navigated to URL 'https://www.google.com/'");
            assertEquals(cookieStep.getStatus(), Status.FAIL);
            assertNull(cookieStep.getRequest());
            assertNull(cookieStep.getResponse());
            assertNotNull(cookieStep.getScreenshot());
        }
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void toURLNoDriverTest() throws MalformedURLException {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.navigate().to(new URL("https://www.google.com/"));
        } finally {
            Step cookieStep = reporter.getSteps().get(2);
            assertEquals(cookieStep.getNumber(), 3);
            assertNotEquals(cookieStep.getTime(), 0.0);
            assertEquals(cookieStep.getAction(), "Navigating to URL 'https://www.google.com/'");
            assertEquals(cookieStep.getExpected(), "Browser loaded URL");
            assertTrue(cookieStep.getActual().startsWith("Unable to navigate to URL: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(cookieStep.getStatus(), Status.FAIL);
            assertNull(cookieStep.getRequest());
            assertNull(cookieStep.getResponse());
            assertNull(cookieStep.getScreenshot());
        }
    }

    @Test
    public void refreshTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.navigate().refresh();
        Step step = reporter.getSteps().get(1);
        assertEquals(step.getNumber(), 2);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Refreshing the current browser page");
        assertEquals(step.getExpected(), "Browser page displayed");
        assertEquals(step.getActual(), "Refreshed the page");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void refreshNoDriverTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.navigate().refresh();
        } finally {
            Step step = reporter.getSteps().get(2);
            assertEquals(step.getNumber(), 3);
            assertNotEquals(step.getTime(), 0.0);
            assertEquals(step.getAction(), "Refreshing the current browser page");
            assertEquals(step.getExpected(), "Browser page displayed");
            assertTrue(step.getActual().startsWith("Unable to refresh the page: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(step.getStatus(), Status.FAIL);
            assertNull(step.getRequest());
            assertNull(step.getResponse());
            assertNull(step.getScreenshot());
        }
    }
}
