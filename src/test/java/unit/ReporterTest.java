package unit;

import com.testpros.fast.reporter.FailedStepException;
import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import com.testpros.fast.reporter.Step.Status;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

public class ReporterTest {

    Step passed = new Step("foo", "bar");
    Step failed = new Step("foo", "bar");
    Step checked = new Step("foo", "bar");

    @BeforeClass
    public void setupSteps() {
        failed.setFailed();
        checked.setResult("", Status.CHECK);
    }

    @Test
    public void constructorTest() {
        Reporter reporter = new Reporter(null);
        assertNull(reporter.getDriver());
        assertEquals(reporter.getSteps().size(), 0);
        assertEquals(reporter.getStartTime().toString(), new Date().toString());
        assertNull(reporter.getEndTime());
    }

    @Test
    public void addStepPassedTest() {
        Reporter reporter = new Reporter(null);
        reporter.addStep(passed, false);
        assertEquals(reporter.getSteps().size(), 1);
        assertEquals(reporter.getSteps().get(0), passed);
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void addStepFailureTest() {
        Reporter reporter = new Reporter(null);
        try {
            reporter.addStep(failed, false);
        } finally {
            assertEquals(reporter.getSteps().size(), 1);
            assertEquals(reporter.getSteps().get(0), failed);
        }
    }

    @Test
    public void getStatusEmptyTest() {
        Reporter reporter = new Reporter(null);
        assertEquals(reporter.getStatus(), Status.PASS);
    }

    @Test
    public void getStatusPassedTest() {
        Reporter reporter = new Reporter(null);
        reporter.addStep(passed, false);
        assertEquals(reporter.getStatus(), Status.PASS);
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void getStatusFailedTest() {
        Reporter reporter = new Reporter(null);
        try {
            reporter.addStep(failed, false);
        } finally {
            assertEquals(reporter.getStatus(), Status.FAIL);
        }
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void getStatusMultiple0Test() {
        Reporter reporter = new Reporter(null);
        reporter.addStep(passed, false);
        try {
            reporter.addStep(failed, false);
        } finally {
            assertEquals(reporter.getStatus(), Status.FAIL);
        }
    }

    @Test(expectedExceptions = FailedStepException.class)
    public void getStatusMultiple1Test() {
        Reporter reporter = new Reporter(null);
        reporter.addStep(passed, false);
        try {
            reporter.addStep(failed, false);
        } finally {
            reporter.addStep(checked, false);
        }
        assertEquals(reporter.getStatus(), Status.FAIL);
    }

    @Test
    public void getStatusMultiple2Test() {
        Reporter reporter = new Reporter(null);
        reporter.addStep(passed, false);
        reporter.addStep(checked, false);
        assertEquals(reporter.getStatus(), Status.CHECK);
    }

    @Test
    public void capitalizeFirstLetterNullTest() {
        assertNull(Reporter.capitalizeFirstLetter(null));
    }

    @Test
    public void capitalizeFirstLetterEmptyTest() {
        assertEquals(Reporter.capitalizeFirstLetter(""), "");
    }

    @Test
    public void capitalizeFirstLetterTest() {
        assertEquals(Reporter.capitalizeFirstLetter("abc"), "Abc");
    }

    @Test
    public void capitalizeFirstLetterNoLetterTest() {
        assertEquals(Reporter.capitalizeFirstLetter("1abc"), "1abc");
    }

    @Test
    public void setEndTimeEmptyTest() {
        Reporter reporter = new Reporter(null);
        reporter.setEndTime();
        assertEquals(reporter.getEndTime().toString(), new Date().toString());
    }

    @Test
    public void setEndTimeAlreadySetTest() throws InterruptedException {
        Reporter reporter = new Reporter(null);
        reporter.setEndTime();
        Thread.sleep(1000);
        reporter.setEndTime();
        assertNotEquals(reporter.getEndTime().toString(), new Date().toString());
    }

    @Test
    public void getRunTimeTest() throws InterruptedException {
        Reporter reporter = new Reporter(null);
        Thread.sleep(1000);
        reporter.setEndTime();
        assertEquals(reporter.getRunTime(), 1000);
    }
}
