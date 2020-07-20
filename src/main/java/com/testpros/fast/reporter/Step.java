package com.testpros.fast.reporter;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.util.Date;

public class Step {

    private final String action;
    private final String expected;
    private final long startTime;
    private int number;
    private String actual;
    private String screenshot;  //TODO - should expand on this; ideally would have a before and an after,
                                    // either of element of whole screen if no element
    private RestRequest request;
    private CloseableHttpResponse response;
    private double time;
    private Status status;

    public Step(String action, String expected) {
        this.action = action;
        this.expected = expected;
        startTime = new Date().getTime();
    }

    public Step(String action, String expected, RestRequest request) {
        this.action = action;
        this.expected = expected;
        this.request = request;
        startTime = new Date().getTime();
    }

    public RestRequest getRequest() {
        return request;
    }

    public CloseableHttpResponse getResponse() {
        return response;
    }

    public void setResponse(CloseableHttpResponse response) {
        this.response = response;
    }

    public int getNumber() {
        return number;
    }

    void setNumber(int number) {
        this.number = number;
    }

    public String getAction() {
        return action;
    }

    public String getExpected() {
        return expected;
    }

    public String getActual() {
        return actual;
    }

    public void setPassed() {
        setStatus(Status.PASS);
    }

    public void setFailed() {
        setStatus(Status.FAIL);
    }

    public void setPassed(String actual) {
        setResult(actual, Status.PASS);
    }

    public void setFailed(String actual) {
        setResult(actual, Status.FAIL);
    }

    public void setResult(String actual, Status status) {
        setActual(actual);
        setStatus(status);
    }

    public void setActual(String actual) {
        setTime();
        this.actual = actual;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public double getTime() {
        return time;
    }

    public void setTime() {
        long stopTime = new Date().getTime();
        if (time == 0) {
            time = stopTime - startTime;
        }
    }

    public Status getStatus() {
        return status;
    }

    private void setStatus(Status status) {
        setTime();
        this.status = status;
    }

    public void takeScreenshot(WebDriver driver) {
        try {
            screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch(Exception e) {
            screenshot = "Unable to capture screenshot: " + e.getMessage();
        }
    }

    public enum Status {
        PASS, FAIL, CHECK
    }

    /**
     * Determines if any popup is present on the page
     *
     * @return Boolean: is a popup present on the page
     */
    private boolean isActiveModalDialog(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
