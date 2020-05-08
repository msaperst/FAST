package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.firefox.XpiDriverService;

public class FirefoxDriver extends RemoteWebDriver {

    org.openqa.selenium.firefox.FirefoxDriver firefoxDriver;
    Reporter reporter = new Reporter(null);

    public FirefoxDriver() {
        Step step = new Step("Launching new Firefox instance",
                "New FirefoxDriver successfully starts");
        try {
            firefoxDriver = new org.openqa.selenium.firefox.FirefoxDriver();
            reporter = new Reporter(firefoxDriver);
            step.setActual("FirefoxDriver successfully started");
            step.setStatus(Step.Status.PASS);
        } catch (Exception e) {
            step.setActual("Unable to launch new firefox instance: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    @Deprecated
    public FirefoxDriver(Capabilities capabilities) {
        //TODO
    }

    @Deprecated
    public FirefoxDriver(GeckoDriverService service, Capabilities desiredCapabilities) {
        //TODO
    }

    public FirefoxDriver(FirefoxOptions options) {
        //TODO
    }

    public FirefoxDriver(GeckoDriverService service) {
        //TODO
    }

    public FirefoxDriver(XpiDriverService service) {
        //TODO
    }

    public FirefoxDriver(GeckoDriverService service, FirefoxOptions options) {
        //TODO
    }

    public FirefoxDriver(XpiDriverService service, FirefoxOptions options) {
        //TODO
    }

    @Override
    public org.openqa.selenium.remote.RemoteWebDriver getDriver() {
        return firefoxDriver;
    }

    @Override
    public Reporter getReporter() {
        return reporter;
    }
}
