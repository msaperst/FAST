package com.testpros.fast;

import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.firefox.XpiDriverService;

public class FirefoxDriver extends RemoteWebDriver {

    public FirefoxDriver() {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.firefox.FirefoxDriver();
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Deprecated
    public FirefoxDriver(Capabilities capabilities) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.firefox.FirefoxDriver(capabilities);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Deprecated
    public FirefoxDriver(GeckoDriverService service, Capabilities desiredCapabilities) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.firefox.FirefoxDriver(service, desiredCapabilities);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public FirefoxDriver(FirefoxOptions options) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.firefox.FirefoxDriver(options);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public FirefoxDriver(GeckoDriverService service) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.firefox.FirefoxDriver(service);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public FirefoxDriver(XpiDriverService service) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.firefox.FirefoxDriver(service);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public FirefoxDriver(GeckoDriverService service, FirefoxOptions options) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.firefox.FirefoxDriver(service, options);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public FirefoxDriver(XpiDriverService service, FirefoxOptions options) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.firefox.FirefoxDriver(service, options);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Override
    String getDeviceName() {
        return "Firefox";
    }
}
