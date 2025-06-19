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
        String expectMsg = "Welcome to Safe Railway";
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
        WebElement loginForm = driver.findElement(By.xpath("//h1[contains(text(),'Login page')]"));
        Assert.assertTrue(loginForm.isDisplayed());
    }

    //TC05_BUG
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

//        Assert.assertEquals(
//                loginPage.getLblLoginErrorMsg().getText(),
//                "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes."
//        );
        String actualMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectMsg = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
        Assert.assertEquals(actualMsg, expectMsg, "Thông điệp lỗi không khớp!");
    }


//    @Test
//    public void TC05() {
//        HomePage homePage = new HomePage(driver);
//        driver.manage().deleteAllCookies(); // Xóa cookie để reset trạng thái
//        driver.manage().window().maximize(); // Phóng to cửa sổ
//        homePage.open();
//
//        LoginPage loginPage = homePage.gotoLoginPage();
//
//        // Nhập username
//        WebElement usernameField = driver.findElement(By.id("username"));
//        usernameField.sendKeys(USERNAME);
//
//        // Thử đăng nhập 4 lần với mật khẩu sai
//        for (int i = 0; i < 4; i++) {
//            // Tìm lại passwordField và submitButton mỗi lần để tránh stale element
//            WebElement passwordField = driver.findElement(By.id("password"));
//            WebElement submitButton = driver.findElement(By.cssSelector("input[type='submit']"));
//
//            passwordField.clear();
//            passwordField.sendKeys("invalidPassword");
//            submitButton.click();
//
//            // Đợi 1 giây để trang ổn định
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // Kiểm tra thông điệp lỗi sau 4 lần thử
//        String actualMsg = loginPage.getLblLoginErrorMsg().getText();
//        String expectMsg = "Invalid username or password. Please try again.";
//        Assert.assertEquals(actualMsg, expectMsg, "Thông điệp lỗi không khớp!");
//    }

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
        loginPage.login("@mailinator.com", "12345678");
        Assert.assertEquals(
                loginPage.getLblLoginErrorMsg().getText(),
                "Invalid username or password. Please try again."
        );
    }
}
