package com.testpros.fast;

import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.opera.OperaDriverService;
import org.openqa.selenium.opera.OperaOptions;

public class OperaDriver extends RemoteWebDriver {

    public OperaDriver() {
        Step step = setupStep();
        try {
            seleniumRemoteWebDriver = new org.openqa.selenium.opera.OperaDriver();
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public OperaDriver(OperaDriverService service) {
        this.service = service;
        Step step = setupStep();
        try {
            seleniumRemoteWebDriver = new org.openqa.selenium.opera.OperaDriver(service);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Deprecated
    public OperaDriver(Capabilities capabilities) {
        this.capabilities = capabilities;
        Step step = setupStep();
        try {
            seleniumRemoteWebDriver = new org.openqa.selenium.opera.OperaDriver(capabilities);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public OperaDriver(OperaOptions options) {
        this.options = options;
        Step step = setupStep();
        try {
            seleniumRemoteWebDriver = new org.openqa.selenium.opera.OperaDriver(options);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public OperaDriver(OperaDriverService service, OperaOptions options) {
        this.service = service;
        this.options = options;
        Step step = setupStep();
        try {
            seleniumRemoteWebDriver = new org.openqa.selenium.opera.OperaDriver(service, options);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Deprecated
    public OperaDriver(OperaDriverService service, Capabilities capabilities) {
        this.service = service;
        this.capabilities = capabilities;
        Step step = setupStep();
        try {
            seleniumRemoteWebDriver = new org.openqa.selenium.opera.OperaDriver(service, capabilities);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Override
    String getDeviceName() {
        return "Opera";
    }
}
