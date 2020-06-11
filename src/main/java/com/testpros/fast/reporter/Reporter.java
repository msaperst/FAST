package com.testpros.fast.reporter;

import com.testpros.fast.reporter.Step.Status;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reporter {
    private final WebDriver driver;
    private final Date startTime;
    private Date endTime;
    private List<Step> steps;

    public Reporter(WebDriver driver) {
        this.driver = driver;
        steps = new ArrayList<>();
        startTime = new Date();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void addStep(Step step) {
        addStep(step, true);
    }

    public void addStep(Step step, boolean screenshot) {
        step.setNumber(steps.size() + 1);
        try {
            if (screenshot) {
                step.takeScreenshot(driver);
            }
        } finally {
            steps.add(step);
        }
        if (step.getStatus() == Status.FAIL) {
            throw new FailedStepException("Expected to " + step.getExpected() + ", instead " + step.getActual());
        }
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Status getStatus() {
        for (Step step : steps) {
            if (step.getStatus() == Status.FAIL) {
                return step.getStatus();
            }
        }
        for (Step step : steps) {
            if (step.getStatus() == Status.CHECK) {
                return step.getStatus();
            }
        }
        return Status.PASS;
    }

    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime() {
        if( this.endTime == null ) {
            this.endTime = new Date();
        }
    }

    /**
     * Provides the run time of the test in milliseconds
     * @return number of milliseconds from when the test started {@link #getStartTime}
     * to when the test ended {@link #getEndTime()}
     */
    public long getRunTime() {
        return endTime.getTime() - startTime.getTime();
    }

    public void simpleOut(String testTitle) {
        //TODO - cleanup driver name
        System.out.println(testTitle + " | " + driver.toString() + " | " + getStatus().toString() +
                " | " + (getRunTime()) + " milliseconds");
        System.out.println("------------------------------------");
        System.out.println("Step | Action | Expected | Actual | Status | Time");
        for (Step step : steps) {
            System.out.println(step.getNumber() + " | " + step.getAction() + " | " +
                    step.getExpected() + " | " + step.getActual() + " | " + step.getStatus() +
                    " | " + step.getTime() + " milliseconds");
        }
    }
}
