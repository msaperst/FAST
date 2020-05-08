import com.testpros.fast.*;
import com.testpros.fast.reporter.Step;
import com.testpros.fast.reporter.Step.Status;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FastIT {

    WebDriver driver;
    WebRest rest;
    AppiumDriverLocalService service;

    String wordpressURL = "https://wordpress.com/";
    List<NameValuePair> params = new ArrayList<>();

    @BeforeMethod
    public void setup(Method method) {
        // if an appium test case, setup our appium server
        if (method.getName().startsWith("appium")) {
            service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingAnyFreePort());
            service.start();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
            if (method.getName().startsWith("appiumBrowser")) { //for browser
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
                WebDriverManager.chromedriver().forceCache().version("74").setup();
                capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE,
                        WebDriverManager.chromedriver().getBinaryPath());
            } else {    // for native
                capabilities.setCapability("appPackage", "com.flipkart.android");
                capabilities.setCapability("appActivity", "com.flipkart.android.SplashActivity");
                capabilities.setCapability(MobileCapabilityType.APP, "src/test/resources/flipkart.apk");
                capabilities.setCapability("autoGrantPermissions", "true");
            }
            driver = new AndroidDriver<>(service, capabilities);
        } else { // else, it's a selenium test case, so setup our chrome driver
            WebDriverManager.chromedriver().forceCache().setup();
            driver = new ChromeDriver();
//            WebDriverManager.firefoxdriver().forceCache().setup();
//            driver = new FirefoxDriver();
        }
        rest = new WebRest(driver);

        //setup our login params
        params.add(new BasicNameValuePair("username", Property.getProperty("username")));
        params.add(new BasicNameValuePair("password", Property.getProperty("password")));
        params.add(new BasicNameValuePair("client_id", Property.getProperty("clientId")));
        params.add(new BasicNameValuePair("client_secret", Property.getProperty("clientSecret")));
    }

    @Test
    public void seleniumSampleTest() throws IOException {
        By displayName = By.className("profile-gravatar__user-display-name");

        driver.get(wordpressURL);
        rest.post(wordpressURL + "wp-login.php?action=login-endpoint", params);

        driver.get(wordpressURL + "me");
        String displayNameText = driver.findElement(displayName).getText();
        assertEquals(displayNameText, Property.getProperty("username"),
                "Expected element '" + displayName + "' to have text '" + Property.getProperty("username") + "'",
                "Element '" + displayName + "' has text '" + displayNameText + "'");
    }

    @Test
    public void appiumBrowserSampleTest() throws IOException {
        By displayName = By.id("display_name");

        driver.get(wordpressURL);
        rest.post(wordpressURL + "wp-login.php?action=login-endpoint", params);

        driver.get(wordpressURL + "me");
        String displayNameValue = driver.findElement(displayName).getAttribute("value");
        assertEquals(displayNameValue, Property.getProperty("username"),
                "Expected element '" + displayName + "' to have text '" + Property.getProperty("username") + "'",
                "Element '" + displayName + "' has text '" + displayNameValue + "'");
    }

    @Test
    public void appiumNativeSampleTest() {
        By userId = By.id("mobileNo");
        By password = By.id("et_password");
        By loginButton = By.id("btn_mlogin");
        By existingUserLogin = By.id("btn_mlogin");
        By errorMessage = By.id("pageLevelError");

        driver.findElement(existingUserLogin).click();
        WebElement userIdElement = driver.findElement(userId);
        //userIdElement.clear();
        userIdElement.sendKeys(Property.getProperty("username"));
        driver.findElement(password).sendKeys(Property.getProperty("password"));
        driver.findElement(loginButton).click();
        String errorMessageText = driver.findElement(errorMessage).getText();

        assertEquals(errorMessageText, "Account does not exist",
                "Expected element '" + errorMessage + "' to have text 'Account does not exist'",
                "Element '" + errorMessage + "' has text '" + errorMessageText + "'");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup(Method method) {
        driver.quit();
        if (service != null) {
            service.stop();
        }
        driver.getReporter().simpleOut(method.getName());
    }

    private void assertEquals(Object actual, Object expected, String expectedString, String actualString) {
        Step step = new Step("", expectedString);
        try {
            org.testng.Assert.assertEquals(expected, actual);
            step.setStatus(Status.PASS);
        } catch (AssertionError e){
            step.setStatus(Status.FAIL);
            throw e;
        } finally {
            step.setActual(actualString);
            driver.getReporter().addStep(step);
        }
    }
}
