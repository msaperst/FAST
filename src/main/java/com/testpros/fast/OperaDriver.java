package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.opera.OperaDriverService;
import org.openqa.selenium.opera.OperaOptions;

public class OperaDriver extends RemoteWebDriver {

    org.openqa.selenium.opera.OperaDriver operaDriver;
    Reporter reporter = new Reporter(null);

    public OperaDriver() {
        Step step = new Step("Launching new Opera instance",
                "New OperaDriver successfully starts");
        try {
            operaDriver = new org.openqa.selenium.opera.OperaDriver();
            reporter = new Reporter(operaDriver);
            step.setActual("OperaDriver successfully started");
            step.setStatus(Step.Status.PASS);
        } catch (Exception e) {
            step.setActual("Unable to launch new opera instance: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    public OperaDriver(OperaDriverService service) {
        //TODO
    }

    @Deprecated
    public OperaDriver(Capabilities capabilities) {
        //TODO
    }

    public OperaDriver(OperaOptions options) {
        //TODO
    }

    public OperaDriver(OperaDriverService service, OperaOptions options) {
        //TODO
    }

    @Deprecated
    public OperaDriver(OperaDriverService service, Capabilities capabilities) {
        //TODO
    }

    @Override
    public org.openqa.selenium.remote.RemoteWebDriver getDriver() {
        return operaDriver;
    }

    @Override
    public Reporter getReporter() {
        return reporter;
    }
}
