package integration;

import com.testpros.fast.By;
import com.testpros.fast.WebDriver;
import com.testpros.fast.reporter.FailedStepException;
import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import com.testpros.fast.reporter.Step.Status;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import sample.Property;

import java.util.ArrayList;

import static org.testng.Assert.*;

public class RemoteTargetLocatorIT extends FastTestBase {

    String maxURL = Property.class.getClassLoader().getResource("pages/max.html").toString().replace(":/", ":///");
    String iFrameURL = Property.class.getClassLoader().getResource("pages/iframe.html").toString().replace(":/", ":///");
    String framesURL = Property.class.getClassLoader().getResource("pages/frames.html").toString().replace(":/", ":///");
    String alertsURL = Property.class.getClassLoader().getResource("pages/alerts.html").toString().replace(":/", ":///");

    @Test
    public void frameIndexTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get(iFrameURL);
        assertFalse(driver.isElementPresent(By.id("name")));
        driver.switchTo().frame(0);
        //assert we've properly switched
        assertTrue(driver.isElementPresent(By.id("name")));
        driver.switchTo().parentFrame();
        driver.switchTo().frame(1);
        //assert we've properly switched
        assertFalse(driver.isElementPresent(By.id("name")));
        //assert reporter has proper information on switch
        Step step = reporter.getSteps().get(2);
        assertEquals(step.getNumber(), 3);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Switching to frame with index '0'");
        assertEquals(step.getExpected(), "Frame selected");
        assertEquals(step.getActual(), "Switched to frame");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void frameIndexNoIndexTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();
        
        driver.get(iFrameURL);
        try {
            driver.switchTo().frame(2);
        } finally {
            Step step = reporter.getSteps().get(2);
            assertEquals(step.getNumber(), 3);
            assertNotEquals(step.getTime(), 0.0);
            assertEquals(step.getAction(), "Switching to frame with index '2'");
            assertEquals(step.getExpected(), "Frame selected");
            assertTrue(step.getActual().startsWith("Unable to switch to frame: " +
                    "org.openqa.selenium.NoSuchFrameException: no such frame"));
            assertEquals(step.getStatus(), Status.FAIL);
            assertNull(step.getRequest());
            assertNull(step.getResponse());
            assertNotNull(step.getScreenshot());
        }
    }

    @Test
    public void frameStringTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();
        
        driver.get(iFrameURL);
        assertFalse(driver.isElementPresent(By.id("name")));
        driver.switchTo().frame("frame1");
        //assert we've properly switched
        assertTrue(driver.isElementPresent(By.id("name")));
        driver.switchTo().parentFrame();
        driver.switchTo().frame("frame2");
        //assert we've properly switched
        assertFalse(driver.isElementPresent(By.id("name")));
        //assert reporter has proper information on switch
        Step step = reporter.getSteps().get(2);
        assertEquals(step.getNumber(), 3);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Switching to frame with name or id 'frame1'");
        assertEquals(step.getExpected(), "Frame selected");
        assertEquals(step.getActual(), "Switched to frame");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void frameStringNoStringTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get(iFrameURL);
        try {
            driver.switchTo().frame("noSuchFrame");
        } finally {
            Step step = reporter.getSteps().get(2);
            assertEquals(step.getNumber(), 3);
            assertNotEquals(step.getTime(), 0.0);
            assertEquals(step.getAction(), "Switching to frame with name or id 'noSuchFrame'");
            assertEquals(step.getExpected(), "Frame selected");
            assertTrue(step.getActual().startsWith("Unable to switch to frame: " +
                    "org.openqa.selenium.NoSuchFrameException: No frame element found by name or id noSuchFrame"));
            assertEquals(step.getStatus(), Status.FAIL);
            assertNull(step.getRequest());
            assertNull(step.getResponse());
            assertNotNull(step.getScreenshot());
        }
    }

    @Test
    public void frameElementTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();
        
        driver.get(iFrameURL);
        assertFalse(driver.isElementPresent(By.id("name")));
        driver.switchTo().frame(driver.findElement(By.id("frame1")));
        //assert we've properly switched
        assertTrue(driver.isElementPresent(By.id("name")));
        driver.switchTo().parentFrame();
        driver.switchTo().frame(driver.findElement(By.id("frame2")));
        //assert we've properly switched
        assertFalse(driver.isElementPresent(By.id("name")));
        //assert reporter has proper information on switch
        Step step = reporter.getSteps().get(2);
        assertEquals(step.getNumber(), 3);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(),"Switching to frame with element 'By.id: frame1'");
        assertEquals(step.getExpected(),"Frame selected");
        assertTrue(step.getActual().matches("Switched to frame"));
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void frameElementNoElementTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get(iFrameURL);
        try {
            driver.switchTo().frame(driver.findElement(By.id("title")));
        } finally {
            Step step = reporter.getSteps().get(2);
            assertEquals(step.getNumber(), 3);
            assertNotEquals(step.getTime(), 0.0);
            assertEquals(step.getAction(),"Switching to frame with element 'By.id: title'");
            assertEquals(step.getExpected(),"Frame selected");
            assertTrue(step.getActual().startsWith("Unable to switch to frame: " +
                    "org.openqa.selenium.NoSuchFrameException: no such frame: element is not a frame"));
            assertEquals(step.getStatus(), Status.FAIL);
            assertNull(step.getRequest());
            assertNull(step.getResponse());
            assertNotNull(step.getScreenshot());
        }
    }

    @Test
    public void parentFrameTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();
        
        driver.get(iFrameURL);
        assertFalse(driver.isElementPresent(By.id("name")));
        driver.switchTo().frame(0);
        //assert we've properly switched
        assertTrue(driver.isElementPresent(By.id("name")));
        driver.switchTo().parentFrame();
        //assert we've properly switched
        assertFalse(driver.isElementPresent(By.id("name")));
        //assert reporter has proper information on switch
        Step step = reporter.getSteps().get(3);
        assertEquals(step.getNumber(), 4);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Switching to parent frame");
        assertEquals(step.getExpected(), "Parent frame selected");
        assertEquals(step.getActual(), "Switched to parent frame");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test
    public void parentFrameNoParentTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();
        
        driver.get(iFrameURL);
        assertTrue(driver.isElementPresent(By.id("title")));
        driver.switchTo().parentFrame();
        //assert we've properly switched
        assertTrue(driver.isElementPresent(By.id("title")));
        //assert reporter has proper information on switch
        Step step = reporter.getSteps().get(2);
        assertEquals(step.getNumber(), 3);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Switching to parent frame");
        assertEquals(step.getExpected(), "Parent frame selected");
        assertEquals(step.getActual(), "Switched to parent frame");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test(expectedExceptions = NoSuchSessionException.class)
    public void parentFrameNoDriverTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();
        
        driver.get(iFrameURL);
        driver.quit();
        try {
            driver.switchTo().parentFrame();
        } finally {
            Step step = reporter.getSteps().get(3);
            assertEquals(step.getNumber(), 4);
            assertNotEquals(step.getTime(), 0.0);
            assertEquals(step.getAction(), "Switching to parent frame");
            assertEquals(step.getExpected(), "Parent frame selected");
            assertTrue(step.getActual().startsWith("Unable to switch to parent frame: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(step.getStatus(), Status.FAIL);
            assertNull(step.getRequest());
            assertNull(step.getResponse());
            assertNull(step.getScreenshot());
        }
    }

    @Test
    public void windowTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();
        
        driver.get(iFrameURL);
        assertFalse(driver.isElementPresent(By.id("name")));
        driver.findElement(By.linkText("New Window")).click();
        String windowHandle = new ArrayList<>(driver.getWindowHandles()).get(1);
        driver.switchTo().window(windowHandle);
        //assert we've properly switched
        assertTrue(driver.isElementPresent(By.id("name")));
        //assert reporter has proper information on switch
        Step step = reporter.getSteps().get(3);
        assertEquals(step.getNumber(), 4);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Switching to window with name or handle '" + windowHandle + "'");
        assertEquals(step.getExpected(), "Window selected");
        assertEquals(step.getActual(), "Switched to window with handle '" + windowHandle + "'");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void windowNoWindowTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get(iFrameURL);
        try {
            driver.switchTo().window("noSuchWindow");
        } finally {
            Step step = reporter.getSteps().get(2);
            assertEquals(step.getNumber(), 3);
            assertNotEquals(step.getTime(), 0.0);
            assertEquals(step.getAction(), "Switching to window with name or handle 'noSuchWindow'");
            assertEquals(step.getExpected(), "Window selected");
            assertTrue(step.getActual().startsWith("Unable to switch to window: " +
                    "org.openqa.selenium.NoSuchWindowException: no such window"));
            assertEquals(step.getStatus(), Status.FAIL);
            assertNull(step.getRequest());
            assertNull(step.getResponse());
            assertNotNull(step.getScreenshot());
        }
    }

    @Test
    public void defaultContentFramesTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get(framesURL);
        driver.switchTo().frame("main");
        assertTrue(driver.isElementPresent(By.id("company")));
        driver.switchTo().defaultContent();
        //assert we've properly switched
        assertFalse(driver.isElementPresent(By.id("company")));
        //assert reporter has proper information on switch
        Step step = reporter.getSteps().get(3);
        assertEquals(step.getNumber(), 4);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Switching to default content");
        assertEquals(step.getExpected(), "Default content selected");
        assertEquals(step.getActual(), "Switched to default content");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test
    public void defaultContentIFrameTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get(iFrameURL);
        driver.switchTo().frame("frame1");
        assertTrue(driver.isElementPresent(By.id("name")));
        driver.switchTo().defaultContent();
        //assert we've properly switched
        assertFalse(driver.isElementPresent(By.id("name")));
        //assert reporter has proper information on switch
        Step step = reporter.getSteps().get(3);
        assertEquals(step.getNumber(), 4);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Switching to default content");
        assertEquals(step.getExpected(), "Default content selected");
        assertEquals(step.getActual(), "Switched to default content");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test
    public void defaultContentNoFramesTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get(maxURL);
        driver.switchTo().defaultContent();
        //assert we've properly switched
        assertTrue(driver.isElementPresent(By.id("name")));
        //assert reporter has proper information on switch
        Step step = reporter.getSteps().get(2);
        assertEquals(step.getNumber(), 3);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Switching to default content");
        assertEquals(step.getExpected(), "Default content selected");
        assertEquals(step.getActual(), "Switched to default content");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test(expectedExceptions = NoSuchSessionException.class)
    public void defaultContentNoDriverTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.switchTo().defaultContent();
        } finally {
            Step step = reporter.getSteps().get(2);
            assertEquals(step.getNumber(), 3);
            assertNotEquals(step.getTime(), 0.0);
            assertEquals(step.getAction(), "Switching to default content");
            assertEquals(step.getExpected(), "Default content selected");
            assertTrue(step.getActual().startsWith("Unable to switch to default content: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(step.getStatus(), Status.FAIL);
            assertNull(step.getRequest());
            assertNull(step.getResponse());
            assertNull(step.getScreenshot());
        }
    }

    @Test
    public void activeElementTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get(maxURL);
        //TODO - this isn't working
        driver.findElement(By.id("input")).findElement(By.tagName("input")).sendKeys("123");
        WebElement element = driver.switchTo().activeElement();
        //assert element is switched to
        //TODO
        //assert reporter has proper information on switch
        Step step = reporter.getSteps().get(3);
        assertEquals(step.getNumber(), 4);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Switching to active element");
        assertEquals(step.getExpected(), "Active element selected");
        assertEquals(step.getActual(), "Switched to active element 'unknown locator [1]'");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test
    public void activeElementNoElementTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get(maxURL);
        driver.switchTo().activeElement();
        //assert reporter has proper information on switch
        Step step = reporter.getSteps().get(2);
        assertEquals(step.getNumber(), 3);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Switching to active element");
        assertEquals(step.getExpected(), "Active element selected");
        assertEquals(step.getActual(), "Switched to active element 'unknown locator [1]'");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNotNull(step.getScreenshot());
    }

    @Test(expectedExceptions = NoSuchSessionException.class)
    public void activeElementNoDriverTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.quit();
        try {
            driver.switchTo().activeElement();
        } finally {
            Step step = reporter.getSteps().get(2);
            assertEquals(step.getNumber(), 3);
            assertNotEquals(step.getTime(), 0.0);
            assertEquals(step.getAction(), "Switching to active element");
            assertEquals(step.getExpected(), "Active element selected");
            assertTrue(step.getActual().startsWith("Unable to switch to active element: " +
                    "org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?"));
            assertEquals(step.getStatus(), Status.FAIL);
            assertNull(step.getRequest());
            assertNull(step.getResponse());
            assertNull(step.getScreenshot());
        }
    }

    @Test
    public void alertTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.get(alertsURL);
        driver.findElement(By.id("alertButton")).click();
        Alert alert = driver.switchTo().alert();
        //assert element is switched to
        assertEquals(alert.getText(), "I am an alert!");
        //assert reporter has proper information on switch
        Step step = reporter.getSteps().get(3);
        assertEquals(step.getNumber(), 4);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getAction(), "Switching to active modal dialog");
        assertEquals(step.getExpected(), "Active modal dialog selected");
        assertEquals(step.getActual(), "Switched to active modal dialog with text 'I am an alert!'");
        assertEquals(step.getStatus(), Status.PASS);
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNull(step.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void alertNoAlertTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        try {
            driver.switchTo().alert();
        } finally {
            Step step = reporter.getSteps().get(1);
            assertEquals(step.getNumber(), 2);
            assertNotEquals(step.getTime(), 0.0);
            assertEquals(step.getAction(), "Switching to active modal dialog");
            assertEquals(step.getExpected(), "Active modal dialog selected");
            assertTrue(step.getActual().startsWith("Unable to switch to active modal dialog: " +
                    "org.openqa.selenium.NoAlertPresentException: no such alert"));
            assertEquals(step.getStatus(), Status.FAIL);
            assertNull(step.getRequest());
            assertNull(step.getResponse());
            assertNotNull(step.getScreenshot());
        }
    }
}
