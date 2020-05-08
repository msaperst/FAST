package com.testpros.fast;

import com.testpros.fast.reporter.Reporter;

public interface WebDriver extends org.openqa.selenium.WebDriver {
    public Reporter getReporter();
}
