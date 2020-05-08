package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.safari.SafariDriverService;
import org.openqa.selenium.safari.SafariOptions;

public class SafariDriver extends RemoteWebDriver {

    org.openqa.selenium.safari.SafariDriver safariDriver;
    Reporter reporter = new Reporter(null);

    @Deprecated
    public SafariDriver(Capabilities desiredCapabilities) {
        Step step = new Step("Launching new Safari instance",
                "New SafariDriver successfully starts");
        try {
            safariDriver = new org.openqa.selenium.safari.SafariDriver();
            reporter = new Reporter(safariDriver);
            step.setActual("SafariDriver successfully started");
            step.setStatus(Step.Status.PASS);
        } catch (Exception e) {
            step.setActual("Unable to launch new safari instance: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    public SafariDriver(SafariOptions safariOptions) {
        //TODO
    }

    public SafariDriver(SafariDriverService safariService) {
        //TODO
    }

    public SafariDriver(SafariDriverService safariServer, SafariOptions safariOptions) {
        //TODO
    }

    @Override
    public org.openqa.selenium.remote.RemoteWebDriver getDriver() {
        return safariDriver;
    }

    @Override
    public Reporter getReporter() {
        return reporter;
    }
}
