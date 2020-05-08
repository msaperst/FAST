package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriver extends RemoteWebDriver {

    org.openqa.selenium.edge.EdgeDriver edgeDriver;
    Reporter reporter = new Reporter(null);

    public EdgeDriver() {
        Step step = new Step("Launching new Edge instance",
                "New EdgeDriver successfully starts");
        try {
            edgeDriver = new org.openqa.selenium.edge.EdgeDriver();
            reporter = new Reporter(edgeDriver);
            step.setActual("EdgeDriver successfully started");
            step.setStatus(Step.Status.PASS);
        } catch (Exception e) {
            step.setActual("Unable to launch new edge instance: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    public EdgeDriver(EdgeDriverService service) {
        //TODO
    }

    @Deprecated
    public EdgeDriver(Capabilities capabilities) {
        //TODO
    }

    public EdgeDriver(EdgeOptions options) {
        //TODO
    }

    public EdgeDriver(EdgeDriverService service, EdgeOptions options) {
        //TODO
    }

    @Deprecated
    public EdgeDriver(EdgeDriverService service, Capabilities capabilities) {
        //TODO
    }

    @Override
    public org.openqa.selenium.remote.RemoteWebDriver getDriver() {
        return edgeDriver;
    }

    @Override
    public Reporter getReporter() {
        return reporter;
    }
}
