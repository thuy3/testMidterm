package Railway;

import org.testng.annotations.Test;
import PageObjects.Railway.*;
import org.openqa.selenium.*;
import org.testng.Assert;

import static Common.Constant.Constant.*;
public class RegisterTest extends BaseTest {

        //TC07_BUG
    @Test
    public void TC07_Register_Successfully() {
        RegisterPage register = new RegisterPage(driver);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        register.goToRegisterPage();

//        String email = "testuser" + System.currentTimeMillis() + "@mailinator.com";
//        register.fillRegisterForm(email, "httt12345", "httt12345", "123456789");
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 8);
        String email = "test" + timestamp + "@mailinator.com";
        String password = "httt12345";
        String pid = "123456789";

        System.out.println("Email: " + email + " (length: " + email.length() + ")");
        System.out.println("Password: " + password + " (length: " + password.length() + ")");
        System.out.println("PID: " + pid + " (length: " + pid.length() + ")");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        register.submit();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (register.isErrorMessageDisplayed()) {
            String errorMsg = register.getRegisterErrorMessage();
            String passwordError = register.getPasswordErrorMessage();
            String pidError = register.getPIDErrorMessage();
            System.out.println("Main error: " + errorMsg);
            System.out.println("Password error: " + passwordError);
            System.out.println("PID error: " + pidError);
            throw new AssertionError("Registration failed with error: " + errorMsg +
                    "\nPassword error: " + passwordError +
                    "\nPID error: " + pidError);
        }

        register.verifyRegistrationSuccess();
    }


    //TC_10: BUG
    @Test
    public void TC10_ConfirmPassword_NotMatch() {
        RegisterPage register = new RegisterPage(driver);
        register.goToRegisterPage();

        String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 8);
        String email = "test" + timestamp + "@mailinator.com";

        String password = "12345678";
        String confirmPassword = "87654321";
        String pid = "123456789";

        register.fillRegisterForm(email, password, confirmPassword, pid);
        register.submit();
        register.verifyErrorMessage("There're errors in the form. Please correct the errors and try again.");
    }

    @Test
    public void TC11_BlankPasswordAndPID() {
        RegisterPage register = new RegisterPage(driver);
        register.goToRegisterPage();

        String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 8);
        String email = "test" + timestamp + "@mailinator.com";

        String password = "";
        String confirmpassword = "";
        String pid = "";
        register.fillRegisterForm(email, password, confirmpassword, pid);
        register.submit();

        String formErrorMsg = register.getRegisterErrorMessage();
        Assert.assertEquals(formErrorMsg, "There're errors in the form. Please correct the errors and try again.");

        String passwordError = register.getPasswordErrorMessage();
        Assert.assertEquals(passwordError,"Invalid password length.");

        String pidError = register.getPIDErrorMessage();
        Assert.assertEquals(pidError, "Invalid ID length.");

    }
}
