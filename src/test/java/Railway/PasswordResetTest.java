package Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import PageObjects.Railway.*;
import org.testng.Assert;

public class PasswordResetTest extends BaseTest {

    @Test
    public void TC09_ChangePasswordSuccessfully() {
        LoginPage login = new LoginPage(driver);
        ChangePasswordPage changePassword = new ChangePasswordPage(driver);

        String oldPass = "httt12345";
        String newPass = "newpass123";

        login.goToLoginPage();
        login.login("hoangthithuthuy12a5@gmail.com", oldPass);

        changePassword.clickChangePasswordTab();
        changePassword.changePassword(oldPass, newPass, newPass);
        changePassword.verifySuccessMessage("Your password has been updated");

        changePassword.resetPasswordToOriginal(newPass, oldPass);
    }

    //TC12_BUG
    @Test
    public void TC12_ResetPassword_EmptyToken() {
        PasswordResetPage resetPage = new PasswordResetPage(driver);

        resetPage.goTo();
        resetPage.fillNewPasswords("newpass123", "newpass123");
        resetPage.clearResetToken();
        resetPage.clickResetPassword();

        Assert.assertTrue(
                resetPage.getTopErrorMessage().contains("The password reset token is incorrect or may be expired"),
                "Expected top error message not found. Actual: " + resetPage.getTopErrorMessage()
        );

        Assert.assertTrue(
                resetPage.getTokenErrorMessage().contains("The password reset token is invalid."),
                "Expected token field error message not found. Actual: " + resetPage.getTokenErrorMessage()
        );
    }




    @Test
    public void TC13_OpenResetLinkFromMailinator() {
        ForgotPasswordPage forgot = new ForgotPasswordPage(driver);
        forgot.openMailinator("testuser");
        forgot.openResetEmail();
        forgot.clickResetLink();
        forgot.verifyResetPasswordFormDisplayed();
    }
}
