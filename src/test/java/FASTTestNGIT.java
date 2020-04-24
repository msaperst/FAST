import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.testpros.fast.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FASTTestNGIT {

    WebDriver driver;
    AppiumDriverLocalService service;

    @BeforeMethod
    public void setup(Method method) {
        // if an appium test case, setup our appium server
        if( method.getName().startsWith("appium" )) {
            service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingAnyFreePort());
            service.start();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
            if( method.getName().startsWith("appiumBrowser")) {
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
                WebDriverManager.chromedriver().forceCache().version("74").setup();
                capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE,
                        WebDriverManager.chromedriver().getBinaryPath());
            } else {
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
    }

    @Test
    public void seleniumSampleTest() {
        driver.get("https://google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("cheese");
        element.submit();
        element = driver.findElement(By.name("q"));
        assertEquals(element.getAttribute("value"), "cheese");
    }

    @Test
    public void appiumBrowserSampleTest() {
        driver.get("https://google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("cheese");
        element.sendKeys(Keys.ENTER);
        element = driver.findElement(By.name("q"));
        assertEquals(element.getAttribute("value"), "cheese");
    }

    By userId = By.id("mobileNo");
    By password = By.id("et_password");
    By loginButton = By.id("btn_mlogin");
    By existingUserLogin = By.id("btn_mlogin");
    By errorMessage = By.id("pageLevelError");

    @Test
    public void appiumNativeSampleTest() {
        driver.findElement(existingUserLogin).click();
        org.openqa.selenium.WebElement userIdElement = driver.findElement(userId);
        userIdElement.clear();
        userIdElement.sendKeys("someone@testvagrant.com");
        driver.findElement(password).sendKeys("testvagrant123");
        driver.findElement(loginButton).click();
        assertTrue(driver.findElement(errorMessage).getText().equalsIgnoreCase("Account does not exist"));
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup(Method method) {
        driver.quit();
        if( service != null ) {
            service.stop();
        }
        driver.getReporter().simpleOut(method.getName());
    }
}
