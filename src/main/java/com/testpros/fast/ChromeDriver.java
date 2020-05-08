package com.testpros.fast;

import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriver extends RemoteWebDriver {

    public ChromeDriver() {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.chrome.ChromeDriver();
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public ChromeDriver(ChromeDriverService service) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.chrome.ChromeDriver(service);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Deprecated
    public ChromeDriver(Capabilities capabilities) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.chrome.ChromeDriver(capabilities);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public ChromeDriver(ChromeOptions options) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.chrome.ChromeDriver(options);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public ChromeDriver(ChromeDriverService service, ChromeOptions options) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.chrome.ChromeDriver(service, options);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Deprecated
    public ChromeDriver(ChromeDriverService service, Capabilities capabilities) {
        Step step = setupStep();
        try {
            remoteWebDriver = new org.openqa.selenium.chrome.ChromeDriver(service, capabilities);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Override
    String getDeviceName() {
        return "Chrome";
    }
}
