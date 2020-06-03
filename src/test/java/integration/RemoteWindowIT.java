package integration;

import com.testpros.fast.ChromeDriver;
import com.testpros.fast.WebDriver;
import com.testpros.fast.reporter.FailedStepException;
import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import com.testpros.fast.reporter.Step.Status;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RemoteWindowIT extends FastTestBase {

    private final Dimension screenSize = new Dimension(600, 400);
    private final String dimensionSize = "(600, 400)";

    private final Point screenPosition = new Point(100, 100);
    private final String pointPosition = "(100, 100)";

    @Override
    @BeforeMethod
    public void setupDevice() {
        WebDriverManager.chromedriver().forceCache().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        drivers.set(new ChromeDriver(chromeOptions));
    }

    @Test
    public void setSizeTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.manage().window().setSize(screenSize);
        //assert window size is changed
        assertEquals(driver.manage().window().getSize(), screenSize);
        //assert reporter has proper information on screen size
        Step sizeStep = reporter.getSteps().get(1);
        assertEquals(sizeStep.getNumber(), 2);
        assertNotEquals(sizeStep.getTime(), 0.0);
        assertEquals(sizeStep.getAction(), "Setting window size to '" + dimensionSize + "'");
        assertEquals(sizeStep.getExpected(), "Window size changed");
        assertEquals(sizeStep.getActual(), "Window resized to '" + dimensionSize + "'");
        assertEquals(sizeStep.getStatus(), Status.PASS);
        assertNull(sizeStep.getRequest());
        assertNull(sizeStep.getResponse());
        assertNotNull(sizeStep.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void setSizeNoDriverTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.manage().window().setSize(screenSize);
        } finally {
            Step sizeStep = reporter.getSteps().get(2);
            assertEquals(sizeStep.getNumber(), 3);
            assertNotEquals(sizeStep.getTime(), 0.0);
            assertEquals(sizeStep.getAction(), "Setting window size to '" + dimensionSize + "'");
            assertEquals(sizeStep.getExpected(), "Window size changed");
            assertTrue(sizeStep.getActual().startsWith("Unable to resize the window: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(sizeStep.getStatus(), Status.FAIL);
            assertNull(sizeStep.getRequest());
            assertNull(sizeStep.getResponse());
            assertNull(sizeStep.getScreenshot());
        }
    }

    @Test
    public void setPositionTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.manage().window().setPosition(screenPosition);
        //assert window size is changed
        assertEquals(driver.manage().window().getPosition(), screenPosition);
        //assert reporter has proper information on screen size
        Step sizeStep = reporter.getSteps().get(1);
        assertEquals(sizeStep.getNumber(), 2);
        assertNotEquals(sizeStep.getTime(), 0.0);
        assertEquals(sizeStep.getAction(), "Setting window position to '" + pointPosition + "'");
        assertEquals(sizeStep.getExpected(), "Window position changed");
        assertEquals(sizeStep.getActual(), "Window moved to '" + pointPosition + "'");
        assertEquals(sizeStep.getStatus(), Status.PASS);
        assertNull(sizeStep.getRequest());
        assertNull(sizeStep.getResponse());
        assertNotNull(sizeStep.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void setPositionNoDriverTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        driver.manage().window().setPosition(screenPosition);
        //assert window size is changed
        assertEquals(driver.manage().window().getPosition(), screenPosition);
        //assert reporter has proper information on screen size
        Step sizeStep = reporter.getSteps().get(2);
        assertEquals(sizeStep.getNumber(), 3);
        assertNotEquals(sizeStep.getTime(), 0.0);
        assertEquals(sizeStep.getAction(), "Setting window position to '" + pointPosition + "'");
        assertEquals(sizeStep.getExpected(), "Window position changed");
        assertTrue(sizeStep.getActual().startsWith("Unable to moved the window: " +
                "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
        assertEquals(sizeStep.getStatus(), Status.FAIL);
        assertNull(sizeStep.getRequest());
        assertNull(sizeStep.getResponse());
        assertNull(sizeStep.getScreenshot());
    }

    @Test
    public void getSizeTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.manage().window().setSize(screenSize);
        assertEquals(driver.manage().window().getSize(), screenSize);
        //assert reporter doesn't show getter information
        assertEquals(reporter.getSteps().size(), 2);
    }

    @Test
    public void getPositionTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.manage().window().setPosition(screenPosition);
        assertEquals(driver.manage().window().getPosition(), screenPosition);
        //assert reporter doesn't show getter information
        assertEquals(reporter.getSteps().size(), 2);
    }

    @Test
    public void maximizeTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        Dimension originalSize = driver.manage().window().getSize();
        driver.manage().window().maximize();
        //assert window size is changed
        assertNotEquals(driver.manage().window().getSize(), originalSize);
        //assert reporter has proper information on screen size
        Step sizeStep = reporter.getSteps().get(1);
        assertEquals(sizeStep.getNumber(), 2);
        assertNotEquals(sizeStep.getTime(), 0.0);
        assertEquals(sizeStep.getAction(), "Maximizing the window");
        assertEquals(sizeStep.getExpected(), "Window maximized");
        assertTrue(sizeStep.getActual().startsWith("Window maximized with new size of '"));
        assertEquals(sizeStep.getStatus(), Status.PASS);
        assertNull(sizeStep.getRequest());
        assertNull(sizeStep.getResponse());
        assertNotNull(sizeStep.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void maximizeNoDriverTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.manage().window().maximize();
        } finally {
            Step sizeStep = reporter.getSteps().get(2);
            assertEquals(sizeStep.getNumber(), 3);
            assertNotEquals(sizeStep.getTime(), 0.0);
            assertEquals(sizeStep.getAction(), "Maximizing the window");
            assertEquals(sizeStep.getExpected(), "Window maximized");
            assertTrue(sizeStep.getActual().startsWith("Unable to maximize the window: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(sizeStep.getStatus(), Status.FAIL);
            assertNull(sizeStep.getRequest());
            assertNull(sizeStep.getResponse());
            assertNull(sizeStep.getScreenshot());
        }
    }

    @Test
    public void fullscreenTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        Dimension originalSize = driver.manage().window().getSize();
        driver.manage().window().fullscreen();
        //assert window size is changed
        assertNotEquals(driver.manage().window().getSize(), originalSize);
        //assert reporter has proper information on screen size
        Step sizeStep = reporter.getSteps().get(1);
        assertEquals(sizeStep.getNumber(), 2);
        assertNotEquals(sizeStep.getTime(), 0.0);
        assertEquals(sizeStep.getAction(), "Setting the window to fullscreen");
        assertEquals(sizeStep.getExpected(), "Window set to fullscreen");
        assertTrue(sizeStep.getActual().startsWith("Window at fullscreen with new size of '"));
        assertEquals(sizeStep.getStatus(), Status.PASS);
        assertNull(sizeStep.getRequest());
        assertNull(sizeStep.getResponse());
        assertNotNull(sizeStep.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void fullscreenNoDriverTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.manage().window().fullscreen();
        } finally {
            Step sizeStep = reporter.getSteps().get(2);
            assertEquals(sizeStep.getNumber(), 3);
            assertNotEquals(sizeStep.getTime(), 0.0);
            assertEquals(sizeStep.getAction(), "Setting the window to fullscreen");
            assertEquals(sizeStep.getExpected(), "Window set to fullscreen");
            assertTrue(sizeStep.getActual().startsWith("Unable to set the window to fullscreen: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(sizeStep.getStatus(), Status.FAIL);
            assertNull(sizeStep.getRequest());
            assertNull(sizeStep.getResponse());
            assertNull(sizeStep.getScreenshot());
        }
    }
}
