package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import com.testpros.fast.reporter.Step;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

public class AndroidDriver extends RemoteWebDriver {

    io.appium.java_client.android.AndroidDriver androidDriver;
    Reporter reporter = new Reporter(null);

    public AndroidDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        Step step = new Step("Launching new Android instance",
                "New AndroidDriver successfully starts");
        try {
            androidDriver = new io.appium.java_client.android.AndroidDriver<>(executor, capabilities);
            reporter = new Reporter(androidDriver);
            step.setActual("AndroidDriver successfully started");
            step.setStatus(Step.Status.PASS);
        } catch (Exception e) {
            step.setActual("Unable to launch new android instance: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    public AndroidDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        //TODO
    }

    public AndroidDriver(URL remoteAddress, HttpClient.Factory httpClientFactory,
                         Capabilities desiredCapabilities) {
        //TODO
    }

    public AndroidDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        Step step = new Step("Launching new Android instance",
                "New AndroidDriver successfully starts");
        try {
            androidDriver = new io.appium.java_client.android.AndroidDriver<>(service, desiredCapabilities);
            reporter = new Reporter(androidDriver);
            step.setActual("AndroidDriver successfully started");
            step.setStatus(Step.Status.PASS);
        } catch (Exception e) {
            step.setActual("Unable to launch new android instance: " + e);
            step.setStatus(Step.Status.FAIL);
        } finally {
            reporter.addStep(step);
        }
    }

    public AndroidDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                         Capabilities desiredCapabilities) {
        //TODO
    }

    public AndroidDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        //TODO
    }

    public AndroidDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                         Capabilities desiredCapabilities) {
        //TODO
    }

    public AndroidDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        //TODO
    }

    public AndroidDriver(Capabilities desiredCapabilities) {
        //TODO
    }

    @Override
    public org.openqa.selenium.remote.RemoteWebDriver getDriver() {
        return androidDriver;
    }

    @Override
    public Reporter getReporter() {
        return reporter;
    }
}
