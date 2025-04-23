package Railway;
import Common.Constant.Constant;
import PageObjects.Railway.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

import static Common.Constant.Constant.*;

public class LoginTest {

    // Thiết lập môi trường trước khi mỗi test case chạy
    @BeforeMethod
    public void beforeTest() {
        System.out.println("Pre-condition: Setup WebDriver");

        try {
            WebDriverManager.chromedriver().clearDriverCache().setup(); // Cài đặt ChromeDriver, xóa cache cũ
            System.out.println("ChromeDriver setup done");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox"); // Cấu hình để tránh một số lỗi trên hệ điều hành Linux
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-blink-features=AutomationControlled"); // Tránh bị phát hiện tự động
            options.addArguments("--remote-allow-origins=*"); // Cấu hình remote

            WEBDRIVER = new ChromeDriver(options); // Tạo mới ChromeDriver
            System.out.println("ChromeDriver launched");

            WEBDRIVER.manage().window().maximize(); // Tự động phóng to cửa sổ trình duyệt
            WEBDRIVER.get("http://railwayb1.somee.com"); // Truy cập vào trang Railway
            System.out.println("Navigated to site");
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi nếu có
            Assert.fail("Failed in beforeTest: " + e.getMessage()); // Nếu có lỗi thì test fail
        }
    }

    // Sau khi mỗi test case hoàn thành, đóng WebDriver
    @AfterMethod
    public void afterMethod() {
        System.out.println("Post-condition");
        WEBDRIVER.quit(); // Đóng cửa sổ trình duyệt
    }

    // TC01 - Kiểm tra người dùng có thể đăng nhập thành công khi nhập đúng tên đăng nhập và mật khẩu
    @Test
    public void TC01(){
        System.out.println("TC01 - User can log into Railway with valid username and password");
        HomePage homePage = new HomePage();
        homePage.open(); // Mở trang chủ
        LoginPage loginPage = homePage.gotoLoginPage(); // Chuyển đến trang đăng nhập
        String actualMsg = loginPage.login(USERNAME, PASSWORD).getWelcomeMessage(); // Đăng nhập và lấy thông báo chào mừng
        String expectMsg = "Welcome " + USERNAME;
        Assert.assertEquals(actualMsg, expectMsg, "Welcome message is not displayed as expected"); // Kiểm tra thông báo chào mừng
    }

    // TC02 - Kiểm tra không thể đăng nhập khi không nhập tên đăng nhập
    @Test
    public void TC02() {
        System.out.println("TC02 - User can't login with blank Username textbox");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login("", PASSWORD); // Đăng nhập với username rỗng
        String actualMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedMsg = "There was a problem with your login and/or errors exist in your form.";
        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected"); // Kiểm tra thông báo lỗi
    }

    // TC03 - Kiểm tra không thể đăng nhập khi nhập mật khẩu sai
    @Test
    public void TC03() {
        System.out.println("TC03 - User cannot log into Railway with invalid password");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(USERNAME, ""); // Đăng nhập với mật khẩu rỗng
        String actualMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedMsg = "There was a problem with your login and/or errors exist in your form.";
        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected"); // Kiểm tra thông báo lỗi
    }

    // TC04 - Kiểm tra trang đăng nhập hiển thị khi người dùng chưa đăng nhập nhấn vào tab 'Book ticket'
    @Test
    public void TC04() {
        System.out.println("TC04 - Login page displays when un-logged user clicks on 'Book ticket' tab");
        HomePage homePage = new HomePage();
        homePage.open();
        WebElement bookTicketTab = WEBDRIVER.findElement(By.xpath("//div[@id='menu']//a[@href='/Page/BookTicketPage.cshtml']"));
        bookTicketTab.click(); // Nhấn vào tab 'Book ticket'
        WebElement loginForm = WEBDRIVER.findElement(By.xpath("//div[@class='content']//form[@id='LoginForm']"));
        Assert.assertTrue(loginForm.isDisplayed(), "Login page did not display when un-logged user clicked on 'Book ticket' tab");
    }

    // TC05 - Kiểm tra hệ thống hiển thị thông báo khi người dùng nhập sai mật khẩu quá nhiều lần
    @Test
    public void TC05() {
        System.out.println("TC05 - System shows message when user enters wrong password several times");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();

        WEBDRIVER.findElement(By.id("username")).sendKeys(USERNAME);
        for (int i = 0; i < 5; i++) {
            WEBDRIVER.findElement(By.id("password")).clear();
            WEBDRIVER.findElement(By.id("password")).sendKeys("invalidPassword"); // Nhập mật khẩu sai
            WEBDRIVER.findElement(By.cssSelector("input[type='submit']")).click();
        }

        String actualMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedMsg = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected"); // Kiểm tra thông báo lỗi
    }

    // TC06 - Kiểm tra các trang bổ sung hiển thị sau khi người dùng đăng nhập thành công
    @Test
    public void TC06() {
        System.out.println("TC06 - Additional pages display once user logged in");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(USERNAME, PASSWORD);

        Assert.assertTrue(homePage.isMyTicketTabDisplayed(), "My ticket tab is not displayed");
        Assert.assertTrue(homePage.isChangePasswordTabDisplayed(), "Change password tab is not displayed");
        Assert.assertTrue(homePage.isLogoutTabDisplayed(), "Logout tab is not displayed");

        MyTicketPage myTicketPage = homePage.clickMyTicketTab();
        Assert.assertTrue(myTicketPage.isMyTicketPageDisplayed(), "User is not redirected to My ticket page");

        ChangePasswordPage changePasswordPage = homePage.clickChangePasswordTab();
        Assert.assertTrue(changePasswordPage.isChangePasswordPageDisplayed(), "User is not redirected to Change password page");
    }

    // TC07 - Kiểm tra người dùng có thể tạo tài khoản mới
    @Test
    public void TC07() {
        System.out.println("TC07 - User can create new account");
        HomePage homePage = new HomePage();
        homePage.open();
        WEBDRIVER.findElement(By.linkText("Register")).click();

        String email = "htthythuy@gmail.com";
        String password = "httt123456";
        String passport = "123456789";

        WEBDRIVER.findElement(By.id("email")).sendKeys(email);
        WEBDRIVER.findElement(By.id("password")).sendKeys(password);
        WEBDRIVER.findElement(By.id("confirmPassword")).sendKeys(password);
        WEBDRIVER.findElement(By.id("pid")).sendKeys(passport);
        WEBDRIVER.findElement(By.xpath("//input[@value='Register']")).click();

        try {
            WebElement successMessage = WEBDRIVER.findElement(By.xpath("//div[@class='success-message']"));
            String expectedMsg = "Thank you for registering your account";
            Assert.assertEquals(successMessage.getText(), expectedMsg);
        } catch (NoSuchElementException e) {
            Assert.fail("Failed to create new account. No success message found.");
        }
    }

    @Test
    public void TC08() {
        System.out.println("TC08 - User can't login with an account that hasn't been activated");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();

        // Giả sử user này đã đăng ký nhưng chưa activate
        String inactiveUser = "inactive_user@mailinator.com";
        String inactivePass = "12345678";

        loginPage.login(inactiveUser, inactivePass);
        String actualMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedMsg = "Invalid username or password. Please try again.";
        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected for inactive account");
    }

    @Test
    public void TC09() {
        System.out.println("TC09 - User can't login with incorrect username and password");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();

        loginPage.login("wronguser@mail.com", "wrongpass");
        String actualMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedMsg = "Invalid username or password. Please try again.";
        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected for invalid credentials");
    }

    @Test
    public void TC10() {
        System.out.println("TC10 - Register fails when confirm password is not the same as password");
        HomePage homePage = new HomePage();
        homePage.open();
        WEBDRIVER.findElement(By.linkText("Register")).click();

        WEBDRIVER.findElement(By.id("email")).sendKeys("abcxyz@mailinator.com");
        WEBDRIVER.findElement(By.id("password")).sendKeys("12345678");
        WEBDRIVER.findElement(By.id("confirmPassword")).sendKeys("87654321");
        WEBDRIVER.findElement(By.id("pid")).sendKeys("123456789");
        WEBDRIVER.findElement(By.xpath("//input[@value='Register']")).click();

        WebElement confirmPasswordError = WEBDRIVER.findElement(By.xpath("//label[@for='confirmPassword' and @class='validation-error']"));
        String expectedMsg = "The password confirmation does not match the password.";
        Assert.assertEquals(confirmPasswordError.getText(), expectedMsg, "Confirm password validation message not displayed correctly");
    }

    @Test
    public void TC11() {
        System.out.println("TC11 - Register fails when password and PID fields are empty");
        HomePage homePage = new HomePage();
        homePage.open();
        WEBDRIVER.findElement(By.linkText("Register")).click();

        WEBDRIVER.findElement(By.id("email")).sendKeys("abcxyz@mailinator.com");
        // Bỏ trống password và PID
        WEBDRIVER.findElement(By.id("confirmPassword")).sendKeys("");
        WEBDRIVER.findElement(By.xpath("//input[@value='Register']")).click();

        WebElement passwordError = WEBDRIVER.findElement(By.xpath("//label[@for='password' and @class='validation-error']"));
        WebElement pidError = WEBDRIVER.findElement(By.xpath("//label[@for='pid' and @class='validation-error']"));

        Assert.assertEquals(passwordError.getText(), "Invalid password length", "Password error not displayed correctly");
        Assert.assertEquals(pidError.getText(), "Invalid ID length", "PID error not displayed correctly");
    }

    @Test
    public void TC12() {
        System.out.println("TC12 - User can request password reset link");
        HomePage homePage = new HomePage();
        homePage.open();
        WEBDRIVER.findElement(By.linkText("Login")).click();
        WEBDRIVER.findElement(By.linkText("Forgot Password page")).click();

        WEBDRIVER.findElement(By.id("email")).sendKeys("tempuser@mailinator.com");
        WEBDRIVER.findElement(By.xpath("//input[@value='Send Instructions']")).click();

        WebElement successMsg = WEBDRIVER.findElement(By.xpath("//div[@class='success-message']"));
        String expectedMsg = "Instructions to reset your password have been sent to your email.";
        Assert.assertEquals(successMsg.getText(), expectedMsg, "Password reset message not displayed correctly");
    }

    @Test
    public void TC13() {
        System.out.println("TC13 - User can reset password via reset link");
        // Giả định bạn đã lấy được reset link từ Mailinator (ở đây hardcode để minh hoạ)
        WEBDRIVER.get("https://railway.app/reset?token=123456");

        WEBDRIVER.findElement(By.id("newPassword")).sendKeys("newpass123");
        WEBDRIVER.findElement(By.id("confirmPassword")).sendKeys("newpass123");
        WEBDRIVER.findElement(By.xpath("//input[@value='Reset Password']")).click();

        WebElement successMsg = WEBDRIVER.findElement(By.xpath("//div[@class='success-message']"));
        String expectedMsg = "Your password has been updated!";
        Assert.assertEquals(successMsg.getText(), expectedMsg, "Reset password success message not displayed");
    }

    @Test
    public void TC14() {
        System.out.println("TC14 - Login fails when email is missing '@' symbol");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();

        loginPage.login("invalidemail.com", "123456");
        WebElement error = WEBDRIVER.findElement(By.id("email-error"));
        Assert.assertTrue(error.getText().contains("Invalid email format"), "Email validation not triggered");
    }

    @Test
    public void TC15() {
        System.out.println("TC15 - Login fails when email exceeds maximum length");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();

        String longEmail = "a".repeat(300) + "@gmail.com";
        loginPage.login(longEmail, "123456");
        WebElement error = WEBDRIVER.findElement(By.id("email-error"));
        Assert.assertTrue(error.getText().contains("Email is too long"), "Email length validation not triggered");
    }

    @Test
    public void TC16() {
        System.out.println("TC16 - User can cancel a ticket");

        // Step 1: Đăng nhập
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Đặt một vé mới
        BookTicketPage bookTicketPage = homePage.clickBookTicketTab();
        bookTicketPage.selectDepartDate(5); // chọn ngày cách hôm nay 5 ngày
        bookTicketPage.selectDepartFrom("Quảng Ngãi");
        bookTicketPage.selectArriveAt("Huế");
        bookTicketPage.selectSeatType("Soft seat");
        bookTicketPage.selectTicketAmount("1");
        bookTicketPage.clickBookTicketButton();

        // Step 3: Chuyển đến trang "My Ticket"
        WebElement myTicketTab = WEBDRIVER.findElement(By.linkText("My ticket"));
        myTicketTab.click();

        // Step 4: Tìm nút Cancel đầu tiên và huỷ vé
        List<WebElement> cancelButtons = WEBDRIVER.findElements(By.xpath("//input[@value='Cancel']"));
        if (!cancelButtons.isEmpty()) {
            WebElement cancelButton = cancelButtons.get(0);

            // Lấy ID của vé từ thuộc tính onclick
            String onClickValue = cancelButton.getAttribute("onclick");
            String idString = onClickValue.split("\\(")[1].split("\\)")[0].trim();
            int id = Integer.parseInt(idString);

            // Scroll và click cancel
            ((JavascriptExecutor) WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", cancelButton);
            cancelButton.click();

            // Xử lý alert xác nhận
            try {
                Alert alert = WEBDRIVER.switchTo().alert();
                alert.accept();
            } catch (NoAlertPresentException e) {
                System.out.println("No alert present.");
            }

            // Chờ hệ thống xử lý
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Kiểm tra ID vé đã bị huỷ chưa
            boolean isTicketCancelled = !WEBDRIVER.getPageSource().contains(Integer.toString(id));
            Assert.assertTrue(isTicketCancelled, "Ticket cancellation failed for ID: " + id);
            System.out.println("Ticket with ID " + id + " has been successfully cancelled.");
        } else {
            Assert.fail("No tickets available to cancel.");
        }
    }


}
