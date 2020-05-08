package com.testpros.fast;

import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;

public class InternetExplorerDriver extends RemoteWebDriver {

    public InternetExplorerDriver() {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.ie.InternetExplorerDriver();
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Deprecated
    public InternetExplorerDriver(Capabilities capabilities) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.ie.InternetExplorerDriver(capabilities);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public InternetExplorerDriver(InternetExplorerOptions options) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.ie.InternetExplorerDriver(options);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Deprecated
    public InternetExplorerDriver(int port) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.ie.InternetExplorerDriver(port);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public InternetExplorerDriver(InternetExplorerDriverService service) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.ie.InternetExplorerDriver(service);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Deprecated
    public InternetExplorerDriver(InternetExplorerDriverService service, Capabilities capabilities) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.ie.InternetExplorerDriver(service, capabilities);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public InternetExplorerDriver(InternetExplorerDriverService service, InternetExplorerOptions options) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.ie.InternetExplorerDriver(service, options);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Deprecated
    public InternetExplorerDriver(InternetExplorerDriverService service, Capabilities capabilities, int port) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.ie.InternetExplorerDriver(service, capabilities, port);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Override
    String getDeviceName() {
        return "Internet Explorer";
    }
}
