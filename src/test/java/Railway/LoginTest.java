package Railway;

import PageObjects.Railway.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import static Common.Constant.Constant.*;

public class LoginTest extends BaseTest {

    @Test
    public void TC01() {
        HomePage homePage = new HomePage(driver);
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        String actualMsg = loginPage.login(USERNAME, PASSWORD).getWelcomeMessage();
        String expectMsg = "Welcome " + USERNAME;
        Assert.assertEquals(actualMsg, expectMsg);
    }

    @Test
    public void TC02() {
        HomePage homePage = new HomePage(driver);
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login("", PASSWORD);
        Assert.assertEquals(
                loginPage.getLblLoginErrorMsg().getText(),
                "There was a problem with your login and/or errors exist in your form."
        );
    }

    @Test
    public void TC03() {
        HomePage homePage = new HomePage(driver);
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(USERNAME, "");
        Assert.assertEquals(
                loginPage.getLblLoginErrorMsg().getText(),
                "There was a problem with your login and/or errors exist in your form."
        );
    }

    @Test
    public void TC04() {
        HomePage homePage = new HomePage(driver);
        homePage.open();

        driver.findElement(By.xpath("//div[@id='menu']//a[@href='/Page/BookTicketPage.cshtml']")).click();
        WebElement loginForm = driver.findElement(By.xpath("//div[@class='content']//form[@id='LoginForm']"));
        Assert.assertTrue(loginForm.isDisplayed());
    }

    @Test
    public void TC05() {
        HomePage homePage = new HomePage(driver);
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        driver.findElement(By.id("username")).sendKeys(USERNAME);

        for (int i = 0; i < 5; i++) {
            driver.findElement(By.id("password")).clear();
            driver.findElement(By.id("password")).sendKeys("invalidPassword");
            driver.findElement(By.cssSelector("input[type='submit']")).click();
        }

        Assert.assertEquals(
                loginPage.getLblLoginErrorMsg().getText(),
                "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes."
        );
    }

    @Test
    public void TC06() {
        HomePage homePage = new HomePage(driver);
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(USERNAME, PASSWORD);

        Assert.assertTrue(homePage.isMyTicketTabDisplayed());
        Assert.assertTrue(homePage.isChangePasswordTabDisplayed());
        Assert.assertTrue(homePage.isLogoutTabDisplayed());
        Assert.assertTrue(homePage.clickMyTicketTab().isMyTicketPageDisplayed());
        Assert.assertTrue(homePage.clickChangePasswordTab().isChangePasswordPageDisplayed());
    }

    @Test
    public void TC08() {
        HomePage homePage = new HomePage(driver);
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login("inactive_user@mailinator.com", "12345678");
        Assert.assertEquals(
                loginPage.getLblLoginErrorMsg().getText(),
                "Invalid username or password. Please try again."
        );
    }
}
