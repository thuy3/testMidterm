package Railway;

import org.testng.annotations.Test;
import PageObjects.Railway.*;
import org.openqa.selenium.*;
import org.testng.Assert;

import static Common.Constant.Constant.*;
public class RegisterTest extends BaseTest {
    @Test
    public void TC07_Register_Successfully() {
        RegisterPage register = new RegisterPage(driver);
        register.goToRegisterPage();
        register.fillRegisterForm("testuser@mailinator.com", "12345678", "12345678", "123456789");
        register.submit();
        register.verifyRegistrationSuccess();
    }

    @Test
    public void TC10_ConfirmPassword_NotMatch() {
        RegisterPage register = new RegisterPage(driver);
        register.goToRegisterPage();
        register.fillRegisterForm("testuser@mailinator.com", "12345678", "87654321", "123456789");
        register.submit();
        register.verifyErrorMessage("The two passwords do not match");
    }

    @Test
    public void TC11_BlankPasswordAndPID() {
        RegisterPage register = new RegisterPage(driver);
        register.goToRegisterPage();
        register.fillRegisterForm("testuser@mailinator.com", "", "", "");
        register.submit();
        register.verifyErrorMessage("Invalid password length or PID");
    }
}
