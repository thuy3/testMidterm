package Railway;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import Common.Constant.Constant;
import PageObjects.Railway.*;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.util.List;

import static Common.Constant.Constant.*;
public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        //WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://railwayb1.somee.com");
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

