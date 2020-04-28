# F.A.S.T. - Framework for Appium and Selenium Testing
Start writing your Appium and Selenium tests **fast**

Convert **fast** from strait Appium and Selenium tests to FAST

Execute your tests **fast**

## Why Use FAST
* Don't learn a new DSL - re-use the same Selenium interface you are used to
* Using the same Selenium commands, get reporting 'for free'
    - Provides traceability
    - Assists with debugging and triage
    - Build custom reports to match your process and/or system
* Waits - TODO - explain me
* APIs - TODO - explain me
 
## Getting Started
Getting started with FAST should be simple, and well, fast. 
You can do it in a few simple steps.

### From Scratch
1. Create a new project
2. Add the FAST dependency to your project
3. Create a class file in `src/test/java`
3. Start writing tests (look [here](#examples) for some examples)

### From Existing Appium and/or Selenium Tests
1. Add the FAST dependency to your project
2. Change the imports in your project from `org.openqa.selenium.*` to 
`com.testpros.fast.*`
3. Instead of creating your driver from ChromeDriver or another browser, 
create it with a new WebDriver constructor, and pass the ChromeDriver 
(or other browser)
        
        WebDriver driver = new ChromeDriver();
    becomes
    
        WebDriver driver = new WebDriver(new ChromeDriver());
        
4. After quitting your webdriver session (`driver.quit()`) write out your logs

        driver.getReporter().simpleOut("[YOUR TEST NAME HERE]"); 

That's it. Now keep writing tests like you were before, but now you get all 
the additional logging and traceability from FAST.

Interested in using an entire framework built around FAST? 
Checkout ... (coming soon)

## Examples

### Simple Test
If you're not really using any framework capabilities or tools, 
your test case(s) probably look something like the below:
```java
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class WebDriverGenericIT {

    @Test
    public void sampleSeleniumTest() {
        WebDriverManager.chromedriver().forceCache().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("cheese");
        element.submit();
        element = driver.findElement(By.name("q"));
        assertEquals(element.getAttribute("value"), "cheese");
        driver.quit();
    }
}
```
After following the above instructions, it should look like this:
```java
import io.github.bonigarcia.wdm.WebDriverManager;
import com.testpros.fast.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class FASTGenericIT {
    
    @Test
    public void sampleSeleniumTest() {
        WebDriverManager.chromedriver().forceCache().setup();
        WebDriver driver = new WebDriver(new ChromeDriver());
        driver.get("https://google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("cheese");
        element.submit();
        element = driver.findElement(By.name("q"));
        assertEquals(element.getAttribute("value"), "cheese");
        driver.quit();
        driver.getReporter().simpleOut("Sample Generic FAST Test");
    }
}
```

### Frameworks
It's just as easy to convert your tests when you're using a framework 
like TestNG or JUnit. The same steps apply, what you start off with:
```java
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;

public class WebDriverTestNGIT {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().forceCache().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void sampleSeleniumTest() {
        driver.get("https://google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("cheese");
        element.submit();
        element = driver.findElement(By.name("q"));
        assertEquals(element.getAttribute("value"), "cheese");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        driver.quit();
    }
}
```
becomes:
```java
import io.github.bonigarcia.wdm.WebDriverManager;
import com.testpros.fast.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;

public class FASTTestNGIT {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().forceCache().setup();
        driver = new WebDriver(new ChromeDriver());
    }

    @Test
    public void sampleSeleniumTest() {
        driver.get("https://google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("cheese");
        element.submit();
        element = driver.findElement(By.name("q"));
        assertEquals(element.getAttribute("value"), "cheese");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup(Method method) {
        driver.quit();
        driver.getReporter().simpleOut(method.getName());
    }
}
```
One thing to note about the above, is that Dependency Injection was used
to pass the method under test to the cleanup method, so that the correct
test case name could be recorded in the reporter. 



# TODO
- waits
  - custom - still need negatives
  - built ins (now autowaits for element present, still needs to wait for custom actions (e.g. editable for sendKeys))
- APIs