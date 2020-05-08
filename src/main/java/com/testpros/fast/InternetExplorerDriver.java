package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;

public class InternetExplorerDriver extends RemoteWebDriver {

    org.openqa.selenium.ie.InternetExplorerDriver internetExplorerDriver;
    Reporter reporter = new Reporter(null);

    public InternetExplorerDriver() {
        Step step = new Step("Launching new InternetExplorer instance",
                "New InternetExplorerDriver successfully starts");
        try {
            internetExplorerDriver = new org.openqa.selenium.ie.InternetExplorerDriver();
            reporter = new Reporter(internetExplorerDriver);
            step.setActual("InternetExplorerDriver successfully started");
            step.setStatus(Step.Status.PASS);
        } catch (Exception e) {
            step.setActual("Unable to launch new internetexplorer instance: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    @Deprecated
    public InternetExplorerDriver(Capabilities capabilities) {
        //TODO
    }

    public InternetExplorerDriver(InternetExplorerOptions options) {
        //TODO
    }

    @Deprecated
    public InternetExplorerDriver(int port) {
        //TODO
    }

    public InternetExplorerDriver(InternetExplorerDriverService service) {
        //TODO
    }

    @Deprecated
    public InternetExplorerDriver(InternetExplorerDriverService service, Capabilities capabilities) {
        //TODO
    }

    public InternetExplorerDriver(InternetExplorerDriverService service, InternetExplorerOptions options) {
        //TODO
    }
    @Deprecated
    public InternetExplorerDriver(
            InternetExplorerDriverService service,
            Capabilities capabilities,
            int port) {
        //TODO
    }




    @Override
    public org.openqa.selenium.remote.RemoteWebDriver getDriver() {
        return internetExplorerDriver;
    }

    @Override
    public Reporter getReporter() {
        return reporter;
    }
}
