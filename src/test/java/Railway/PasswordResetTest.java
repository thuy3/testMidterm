package Railway;

import org.testng.annotations.Test;
import PageObjects.Railway.*;
import org.testng.Assert;

public class PasswordResetTest extends BaseTest {

    @Test
    public void TC09_ResetPasswordLinkDisplayed() {
        ForgotPasswordPage forgot = new ForgotPasswordPage(driver);
        forgot.goToLoginPage();
        forgot.clickForgotPassword();
        forgot.verifyResetPasswordPageDisplayed();
    }

    @Test
    public void TC12_ResetPasswordEmailSent() {
        ForgotPasswordPage forgot = new ForgotPasswordPage(driver);
        forgot.goToLoginPage();
        forgot.clickForgotPassword();
        forgot.enterEmail("testuser@mailinator.com");
        forgot.submitRequest();
        forgot.verifyEmailSentMessage();
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
