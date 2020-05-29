import com.testpros.fast.reporter.RestRequest;
import com.testpros.fast.reporter.Step;
import com.testpros.fast.reporter.Step.Status;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class StepTest {

    @Test
    public void newStepTest() {
        Step step = new Step("Action", "Expected");
        assertEquals(step.getAction(), "Action");
        assertEquals(step.getExpected(), "Expected");
        assertEquals(step.getTime(), 0.0);
        assertEquals(step.getNumber(), 0);
        assertNull(step.getActual());
        assertNull(step.getRequest());
        assertNull(step.getResponse());
        assertNull(step.getStatus());
        assertNull(step.getScreenshot());
    }

    @Test
    public void newAPIStepTest() {
        RestRequest restRequest = new RestRequest();
        Step step = new Step("Action", "Expected", restRequest);
        assertEquals(step.getAction(), "Action");
        assertEquals(step.getExpected(), "Expected");
        assertEquals(step.getRequest(), restRequest);
        assertEquals(step.getTime(), 0.0);
        assertEquals(step.getNumber(), 0);
        assertNull(step.getActual());
        assertNull(step.getResponse());
        assertNull(step.getStatus());
        assertNull(step.getScreenshot());
    }

    @Test
    public void setPassedTest() throws InterruptedException {
        Step step = new Step("Action", "Expected");
        Thread.sleep(1);
        step.setPassed();
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getStatus(), Status.PASS);
    }

    @Test
    public void setFailedTest() throws InterruptedException {
        Step step = new Step("Action", "Expected");
        Thread.sleep(1);
        step.setFailed();
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getStatus(), Status.FAIL);
    }

    @Test
    public void setPassedActualTest() throws InterruptedException {
        Step step = new Step("Action", "Expected");
        Thread.sleep(1);
        step.setPassed("Actual");
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getActual(), "Actual");
        assertEquals(step.getStatus(), Status.PASS);
    }

    @Test
    public void setFailedActualTest() throws InterruptedException {
        Step step = new Step("Action", "Expected");
        Thread.sleep(1);
        step.setFailed("Actual");
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getActual(), "Actual");
        assertEquals(step.getStatus(), Status.FAIL);
    }

    @Test
    public void setResultTest() throws InterruptedException {
        Step step = new Step("Action", "Expected");
        Thread.sleep(1);
        step.setResult("Actual", Status.PASS);
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getActual(), "Actual");
        assertEquals(step.getStatus(), Status.PASS);
    }

    @Test
    public void setActualTest() throws InterruptedException {
        Step step = new Step("Action", "Expected");
        Thread.sleep(1);
        step.setActual("Actual");
        assertNotEquals(step.getTime(), 0.0);
        assertEquals(step.getActual(), "Actual");
        assertNull(step.getStatus());
    }

    @Test
    public void setTimeTest() throws InterruptedException {
        Step step = new Step("Action", "Expected");
        Thread.sleep(1);
        step.setTime();
        Double time = step.getTime();
        Thread.sleep(1);
        step.setTime();
        assertEquals(step.getTime(), time);
    }


}
