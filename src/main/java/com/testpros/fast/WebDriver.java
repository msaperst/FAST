package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;

import java.util.List;

public interface WebDriver extends org.openqa.selenium.WebDriver {
    Reporter getReporter();

    List<WebElement> findElements(By by);

    WebElement findElement(By by);

    boolean isElementPresent(By by);

    void waitForElementPresent(By by);
}
