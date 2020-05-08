package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

public class IOSDriver<T extends WebElement> extends RemoteWebDriver {

    io.appium.java_client.ios.IOSDriver iOSDriver;
    Reporter reporter = new Reporter(null);

    public IOSDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        Step step = new Step("Launching new IOS instance",
                "New IOSDriver successfully starts");
        try {
            iOSDriver = new io.appium.java_client.ios.IOSDriver<>(executor, capabilities);
            reporter = new Reporter(iOSDriver);
            step.setActual("IOSDriver successfully started");
            step.setStatus(Step.Status.PASS);
        } catch (Exception e) {
            step.setActual("Unable to launch new iOS instance: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    public IOSDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        //TODO
    }

    public IOSDriver(URL remoteAddress, HttpClient.Factory httpClientFactory,
                     Capabilities desiredCapabilities) {
        //TODO
    }

    public IOSDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        Step step = new Step("Launching new IOS instance",
                "New IOSDriver successfully starts");
        try {
            iOSDriver = new io.appium.java_client.ios.IOSDriver<>(service, desiredCapabilities);
            reporter = new Reporter(iOSDriver);
            step.setActual("IOSDriver successfully started");
            step.setStatus(Step.Status.PASS);
        } catch (Exception e) {
            step.setActual("Unable to launch new iOS instance: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    public IOSDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                     Capabilities desiredCapabilities) {
        //TODO
    }

    public IOSDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        //TODO
    }

    public IOSDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                     Capabilities desiredCapabilities) {
        //TODO
    }

    public IOSDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        //TODO
    }

    public IOSDriver(Capabilities desiredCapabilities) {
        //TODO
    }

    @Override
    public org.openqa.selenium.remote.RemoteWebDriver getDriver() {
        return iOSDriver;
    }

    @Override
    public Reporter getReporter() {
        return reporter;
    }
}
