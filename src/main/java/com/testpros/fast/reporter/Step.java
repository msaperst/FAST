package com.testpros.fast.reporter;

import org.apache.http.client.methods.CloseableHttpResponse;
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
    private String screenshot;
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


    public void setStatus(Status status) {
        setTime();
        this.status = status;
    }

    public void takeScreenshot(WebDriver driver) {
        screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

    public enum Status {
        PASS, FAIL, CHECK
    }
}
