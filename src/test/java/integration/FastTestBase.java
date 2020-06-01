package integration;

import com.testpros.fast.ChromeDriver;
import com.testpros.fast.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class FastTestBase {

    //TODO - allow other devices
    protected ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    @BeforeMethod
    public void setupDevice() {
        WebDriverManager.chromedriver().forceCache().setup();
        drivers.set(new ChromeDriver());
    }

    @AfterMethod
    public void destroyDevice() {
        drivers.get().quit();
    }
}
