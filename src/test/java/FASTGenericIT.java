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
import org.apache.http.auth.AuthenticationException;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FASTGenericIT {

    @Test
    public void seleniumSampleTest() throws IOException, AuthenticationException {
        WebDriverManager.chromedriver().forceCache().setup();
        WebDriver driver = new WebDriver(new ChromeDriver());

        driver.get("https://wordpress.com/");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", Property.getProperty("username")));
        params.add(new BasicNameValuePair("password", Property.getProperty("password")));
        params.add(new BasicNameValuePair("client_id", Property.getProperty("clientId")));
        params.add(new BasicNameValuePair("client_secret", Property.getProperty("clientSecret")));
        new WebRest(driver).post("https://wordpress.com/wp-login.php?action=login-endpoint", params);

        driver.get("https://wordpress.com/me");
        assertEquals(driver.findElement(By.className("profile-gravatar__user-display-name")).getText(), Property.getProperty("username"));
        driver.quit();
        driver.getReporter().simpleOut("Selenium Sample Generic FAST Test");
    }

    @Test
    public void appiumBrowserSampleTest() throws IOException, AuthenticationException {
        AppiumDriverLocalService service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingAnyFreePort());
        service.start();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
        WebDriverManager.chromedriver().forceCache().version("74").setup();
        capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE,
                WebDriverManager.chromedriver().getBinaryPath());
        WebDriver driver = new WebDriver(new AndroidDriver<>(service, capabilities));

        driver.get("https://wordpress.com/");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", Property.getProperty("username")));
        params.add(new BasicNameValuePair("password", Property.getProperty("password")));
        params.add(new BasicNameValuePair("client_id", Property.getProperty("clientId")));
        params.add(new BasicNameValuePair("client_secret", Property.getProperty("clientSecret")));
        new WebRest(driver).post("https://wordpress.com/wp-login.php?action=login-endpoint", params);

        driver.get("https://wordpress.com/me");
        assertEquals(driver.findElement(By.id("display_name")).getAttribute("value"), Property.getProperty("username"));
        driver.quit();
        service.stop();
        driver.getReporter().simpleOut("Appium Browser Sample Generic FAST Test");
    }

    @Test
    public void appiumNativeSampleTest() {
        By userId = By.id("mobileNo");
        By password = By.id("et_password");
        By loginButton = By.id("btn_mlogin");
        By existingUserLogin = By.id("btn_mlogin");
        By errorMessage = By.id("pageLevelError");

        AppiumDriverLocalService service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingAnyFreePort());
        service.start();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appPackage", "com.flipkart.android");
        capabilities.setCapability("appActivity", "com.flipkart.android.SplashActivity");
        capabilities.setCapability(MobileCapabilityType.APP, "src/test/resources/flipkart.apk");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
        capabilities.setCapability("autoGrantPermissions", "true");
        WebDriver driver = new WebDriver(new AndroidDriver<>(service, capabilities));

        driver.findElement(existingUserLogin).click();
        WebElement userIdElement = driver.findElement(userId);
        userIdElement.clear();
        userIdElement.sendKeys("someone@testvagrant.com");
        driver.findElement(password).sendKeys("testvagrant123");
        driver.findElement(loginButton).click();
        assertTrue(driver.findElement(errorMessage).getText().equalsIgnoreCase("Account does not exist"));

        driver.quit();
        service.stop();
        driver.getReporter().simpleOut("Appium Native Sample Generic FAST Test");
    }
}
