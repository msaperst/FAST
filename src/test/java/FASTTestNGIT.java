import com.testpros.fast.By;
import com.testpros.fast.WebDriver;
import com.testpros.fast.WebElement;
import com.testpros.fast.WebRest;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FASTTestNGIT {

    WebDriver driver;
    WebRest rest;
    AppiumDriverLocalService service;

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
            driver = new WebDriver(new AndroidDriver<>(service, capabilities));
        } else { // else, it's a selenium test case, so setup our chrome driver
            WebDriverManager.chromedriver().forceCache().setup();
            driver = new WebDriver(new ChromeDriver());
        }
        rest = new WebRest(driver);
    }

    @Test
    public void seleniumSampleTest() throws IOException {
        driver.get("https://wordpress.com/");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", Property.getProperty("username")));
        params.add(new BasicNameValuePair("password", Property.getProperty("password")));
        params.add(new BasicNameValuePair("client_id", Property.getProperty("clientId")));
        params.add(new BasicNameValuePair("client_secret", Property.getProperty("clientSecret")));
        rest.post("https://wordpress.com/wp-login.php?action=login-endpoint", params);

        driver.get("https://wordpress.com/me");
        assertEquals(driver.findElement(By.className("profile-gravatar__user-display-name")).getText(), Property.getProperty("username"));
    }

    @Test
    public void appiumBrowserSampleTest() throws IOException {
        driver.get("https://wordpress.com/");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", Property.getProperty("username")));
        params.add(new BasicNameValuePair("password", Property.getProperty("password")));
        params.add(new BasicNameValuePair("client_id", Property.getProperty("clientId")));
        params.add(new BasicNameValuePair("client_secret", Property.getProperty("clientSecret")));
        rest.post("https://wordpress.com/wp-login.php?action=login-endpoint", params);

        driver.get("https://wordpress.com/me");
        assertEquals(driver.findElement(By.id("display_name")).getAttribute("value"), Property.getProperty("username"));
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
        userIdElement.clear();
        userIdElement.sendKeys("someone@testvagrant.com");
        driver.findElement(password).sendKeys("testvagrant123");
        driver.findElement(loginButton).click();
        assertTrue(driver.findElement(errorMessage).getText().equalsIgnoreCase("Account does not exist"));
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup(Method method) {
        driver.quit();
        if (service != null) {
            service.stop();
        }
        driver.getReporter().simpleOut(method.getName());
    }
}
