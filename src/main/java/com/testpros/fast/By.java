// Licensed to the Software Freedom Conservancy (SFC) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The SFC licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package com.testpros.fast;

import io.appium.java_client.MobileSelector;

/**
 * Mechanism used to locate elements within a document. In order to create your own locating
 * mechanisms, it is possible to subclass this class and override the protected methods as required,
 * though it is expected that all subclasses rely on the basic finding mechanisms provided
 * through static methods of this class:
 *
 * <code>
 * public WebElement findElement(WebDriver driver) {
 * WebElement element = driver.findElement(By.id(getSelector()));
 * if (element == null)
 * element = driver.findElement(By.name(getSelector());
 * return element;
 * }
 * </code>
 */
//public class By extends io.appium.java_client.MobileBy {
//public abstract class By extends org.openqa.selenium.By {
public class By {

    org.openqa.selenium.By by;

    public By(org.openqa.selenium.By by) {
        this.by = by;
    }

    public org.openqa.selenium.By getBy() {
        return by;
    }
    
    /**
     * @param id The value of the "id" attribute to search for.
     * @return A By which locates elements by the value of the "id" attribute.
     */
    public static By id(String id) {
        return new By(org.openqa.selenium.By.id(id));
    }

    /**
     * @param linkText The exact text to match against.
     * @return A By which locates A elements by the exact text it displays.
     */
    public static By linkText(String linkText) {
        return new By(org.openqa.selenium.By.linkText(linkText));
    }

    /**
     * @param partialLinkText The partial text to match against
     * @return a By which locates elements that contain the given link text.
     */
    public static By partialLinkText(String partialLinkText) {
        return new By(org.openqa.selenium.By.partialLinkText(partialLinkText));
    }

    /**
     * @param name The value of the "name" attribute to search for.
     * @return A By which locates elements by the value of the "name" attribute.
     */
    public static By name(String name) {
        return new By(org.openqa.selenium.By.name(name));
    }


    /**
     * @param tagName The element's tag name.
     * @return A By which locates elements by their tag name.
     */
    public static By tagName(String tagName) {
        return new By(org.openqa.selenium.By.tagName(tagName));
    }

    /**
     * @param xpathExpression The XPath to use.
     * @return A By which locates elements via XPath.
     */
    public static By xpath(String xpathExpression) {
        return new By(org.openqa.selenium.By.xpath(xpathExpression));
    }

    /**
     * Find elements based on the value of the "class" attribute. If an element has multiple classes, then
     * this will match against each of them. For example, if the value is "one two onone", then the
     * class names "one" and "two" will match.
     *
     * @param className The value of the "class" attribute to search for.
     * @return A By which locates elements by the value of the "class" attribute.
     */
    public static By className(String className) {
        return new By(org.openqa.selenium.By.className(className));
    }

    /**
     * Find elements via the driver's underlying W3C Selector engine. If the browser does not
     * implement the Selector API, a best effort is made to emulate the API. In this case, we strive
     * for at least CSS2 support, but offer no guarantees.
     *
     * @param cssSelector CSS expression.
     * @return A By which locates elements by CSS.
     */
    public static By cssSelector(String cssSelector) {
        return new By(org.openqa.selenium.By.cssSelector(cssSelector));
    }

    /**
     * Read http://developer.android.com/intl/ru/tools/testing-support-library/
     * index.html#uia-apis
     *
     * @param uiautomatorText is Android UIAutomator string
     * @return an instance of {@link By.ByAndroidUIAutomator}
     */
    public static By AndroidUIAutomator(final String uiautomatorText) {
        return new By(io.appium.java_client.MobileBy.AndroidUIAutomator(uiautomatorText));
    }

    /**
     * About Android accessibility
     * https://developer.android.com/intl/ru/training/accessibility/accessible-app.html
     * About iOS accessibility
     * https://developer.apple.com/library/ios/documentation/UIKit/Reference/
     * UIAccessibilityIdentification_Protocol/index.html
     *
     * @param accessibilityId id is a convenient UI automation accessibility Id.
     * @return an instance of {@link By.ByAndroidUIAutomator}
     */
    public static By AccessibilityId(final String accessibilityId) {
        return new By(io.appium.java_client.MobileBy.AccessibilityId(accessibilityId));
    }

    /**
     * This locator strategy is available in XCUITest Driver mode.
     *
     * @param iOSClassChainString is a valid class chain locator string.
     *                            See <a href="https://github.com/facebook/WebDriverAgent/wiki/Queries">
     *                            the documentation</a> for more details
     * @return an instance of {@link By.ByIosClassChain}
     */
    public static By iOSClassChain(final String iOSClassChainString) {
        return new By(io.appium.java_client.MobileBy.iOSClassChain(iOSClassChainString));
    }

    /**
     * This locator strategy is only available in Espresso Driver mode.
     *
     * @param dataMatcherString is a valid class chain locator string.
     *                          See <a href="https://github.com/appium/appium-espresso-driver/pull/386">
     *                          the documentation</a> for more details
     * @return an instance of {@link By.ByAndroidDataMatcher}
     */
    public static By androidDataMatcher(final String dataMatcherString) {
        return new By(io.appium.java_client.MobileBy.androidDataMatcher(dataMatcherString));
    }

    /**
     * This locator strategy is available in XCUITest Driver mode.
     *
     * @param iOSNsPredicateString is an an iOS NsPredicate String
     * @return an instance of {@link By.ByIosNsPredicate}
     */
    public static By iOSNsPredicateString(final String iOSNsPredicateString) {
        return new By(io.appium.java_client.MobileBy.iOSNsPredicateString(iOSNsPredicateString));
    }

    public static By windowsAutomation(final String windowsAutomation) {
        return new By(io.appium.java_client.MobileBy.windowsAutomation(windowsAutomation));
    }

    /**
     * This locator strategy is available in Espresso Driver mode.
     *
     * @param tag is an view tag string
     * @return an instance of {@link ByAndroidViewTag}
     * @since Appium 1.8.2 beta
     */
    public static By AndroidViewTag(final String tag) {
        return new By(io.appium.java_client.MobileBy.AndroidViewTag(tag));
    }

    /**
     * This locator strategy is available only if OpenCV libraries and
     * NodeJS bindings are installed on the server machine.
     *
     * @param b64Template base64-encoded template image string. Supported image formats are the same
     *                    as for OpenCV library.
     * @return an instance of {@link ByImage}
     * @see <a href="https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/image-comparison.md">
     * The documentation on Image Comparison Features</a>
     * @see <a href="https://github.com/appium/appium-base-driver/blob/master/lib/basedriver/device-settings.js">
     * The settings available for lookup fine-tuning</a>
     * @since Appium 1.8.2
     */
    public static By image(final String b64Template) {
        return new By(io.appium.java_client.MobileBy.image(b64Template));
    }

    /**
     * This type of locator requires the use of the 'customFindModules' capability and a
     * separately-installed element finding plugin.
     *
     * @param selector selector to pass to the custom element finding plugin
     * @return an instance of {@link ByCustom}
     * @since Appium 1.9.2
     */
    public static By custom(final String selector) {
        return new By(io.appium.java_client.MobileBy.custom(selector));
    }
}
