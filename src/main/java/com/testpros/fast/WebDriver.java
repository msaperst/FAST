package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.service.DriverService;

import java.net.URL;
import java.util.List;

public interface WebDriver extends org.openqa.selenium.WebDriver {
    org.openqa.selenium.WebDriver getDriver();

    Capabilities getCapabilities();

    DriverService getService();

    MutableCapabilities getOptions();

    CommandExecutor getExecutor();

    URL getRemoteAddress();

    HttpClient.Factory getHttpClientFactory();

    AppiumServiceBuilder getBuilder();

    int getPort();

    Reporter getReporter();

    List<WebElement> findElements(By by);

    WebElement findElement(By by);

    boolean isElementPresent(By by);

    void waitForElementPresent(By by);
}
