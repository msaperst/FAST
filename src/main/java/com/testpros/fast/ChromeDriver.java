package com.testpros.fast;

import com.testpros.fast.reporter.Step;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriver extends RemoteWebDriver {

    public ChromeDriver() {
        Step step = setupStep();
        try {
            seleniumRemoteWebDriver = new org.openqa.selenium.chrome.ChromeDriver();
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public ChromeDriver(ChromeDriverService service) {
        this.service = service;
        Step step = setupStep();
        try {
            seleniumRemoteWebDriver = new org.openqa.selenium.chrome.ChromeDriver(service);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Deprecated
    public ChromeDriver(Capabilities capabilities) {
        this.capabilities = capabilities;
        Step step = setupStep();
        try {
            seleniumRemoteWebDriver = new org.openqa.selenium.chrome.ChromeDriver(capabilities);
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
            seleniumRemoteWebDriver = new org.openqa.selenium.chrome.ChromeDriver(options);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    public ChromeDriver(ChromeDriverService service, ChromeOptions options) {
        this.service = service;
        this.options = options;
        Step step = setupStep();
        try {
            seleniumRemoteWebDriver = new org.openqa.selenium.chrome.ChromeDriver(service, options);
            passStep(step);
        } catch (Exception e) {
            failStep(step, e);
        } finally {
            reporter.addStep(step);
        }
    }

    @Deprecated
    public ChromeDriver(ChromeDriverService service, Capabilities capabilities) {
        this.service = service;
        this.capabilities = capabilities;
        Step step = setupStep();
        try {
            seleniumRemoteWebDriver = new org.openqa.selenium.chrome.ChromeDriver(service, capabilities);
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
