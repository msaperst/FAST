package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriver extends RemoteWebDriver {

    org.openqa.selenium.chrome.ChromeDriver chromeDriver;
    Reporter reporter = new Reporter(null);

    public ChromeDriver() {
        Step step = new Step("Launching new Chrome instance",
                "New ChromeDriver successfully starts");
        try {
            chromeDriver = new org.openqa.selenium.chrome.ChromeDriver();
            reporter = new Reporter(chromeDriver);
            step.setActual("ChromeDriver successfully started");
            step.setStatus(Step.Status.PASS);
        } catch (Exception e) {
            step.setActual("Unable to launch new chrome instance: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    public ChromeDriver(ChromeDriverService service) {
        //TODO
    }

    @Deprecated
    public ChromeDriver(Capabilities capabilities) {
        //TODO
    }

    public ChromeDriver(ChromeOptions options) {
        //TODO
    }

    public ChromeDriver(ChromeDriverService service, ChromeOptions options) {
        //TODO
    }

    @Deprecated
    public ChromeDriver(ChromeDriverService service, Capabilities capabilities) {
        //TODO
    }


    @Override
    public org.openqa.selenium.remote.RemoteWebDriver getDriver() {
        return chromeDriver;
    }

    @Override
    public Reporter getReporter() {
        return reporter;
    }
}
