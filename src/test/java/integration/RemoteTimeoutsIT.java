package integration;

import com.testpros.fast.WebDriver;
import com.testpros.fast.reporter.FailedStepException;
import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import com.testpros.fast.reporter.Step.Status;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class RemoteTimeoutsIT extends FastTestBase {

    @Test
    public void implicitlyWaitTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //assert reporter has proper information on implicit wait
        Step sizeStep = reporter.getSteps().get(1);
        assertEquals(sizeStep.getNumber(), 2);
        assertNotEquals(sizeStep.getTime(), 0.0);
        assertEquals(sizeStep.getAction(), "Setting implicit wait to '5 seconds'");
        assertEquals(sizeStep.getExpected(), "Implicit wait changed");
        assertEquals(sizeStep.getActual(), "Updated implicit wait");
        assertEquals(sizeStep.getStatus(), Status.PASS);
        assertNull(sizeStep.getRequest());
        assertNull(sizeStep.getResponse());
        assertNull(sizeStep.getScreenshot());
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void setScriptTimeoutTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.manage().timeouts().setScriptTimeout(1, TimeUnit.SECONDS);
        try {
            ((JavascriptExecutor) driver).executeAsyncScript("setTimeout(function(){ alert('Hello'); }, 3000);");
        } finally {
            //assert the page load timeout was actually changed
            assertTrue(reporter.getSteps().get(2).getTime() < 2000);
            assertTrue(reporter.getSteps().get(2).getTime() > 1000);
            assertEquals(reporter.getSteps().get(2).getStatus(), Status.FAIL);
            //assert reporter has proper information on implicit wait
            Step step = reporter.getSteps().get(1);
            assertEquals(step.getNumber(), 2);
            assertNotEquals(step.getTime(), 0.0);
            assertEquals(step.getAction(), "Setting script timeout to '1 seconds'");
            assertEquals(step.getExpected(), "Script timeout changed");
            assertEquals(step.getActual(), "Updated script timeout");
            assertEquals(step.getStatus(), Status.PASS);
            assertNull(step.getRequest());
            assertNull(step.getResponse());
            assertNull(step.getScreenshot());
        }
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void pageLoadTimeoutTest() {
        WebDriver driver = drivers.get();
        Reporter reporter = driver.getReporter();

        driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.SECONDS);
        try {
            driver.get("https://www.badURL.com/");
        } finally {
            //assert the page load timeout was actually changed
            assertTrue(reporter.getSteps().get(2).getTime() < 2000);
            assertTrue(reporter.getSteps().get(2).getTime() > 1000);
            assertEquals(reporter.getSteps().get(2).getStatus(), Status.FAIL);
            //assert reporter has proper information on implicit wait
            Step step = reporter.getSteps().get(1);
            assertEquals(step.getNumber(), 2);
            assertNotEquals(step.getTime(), 0.0);
            assertEquals(step.getAction(), "Setting page load timeout to '1 seconds'");
            assertEquals(step.getExpected(), "Page load timeout changed");
            assertEquals(step.getActual(), "Updated page load timeout");
            assertEquals(step.getStatus(), Status.PASS);
            assertNull(step.getRequest());
            assertNull(step.getResponse());
            assertNull(step.getScreenshot());
        }
    }
}
