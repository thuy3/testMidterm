package PageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage extends GeneralPage {

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By lnkForgotPassword = By.linkText("Forgot Password page");
    private final By txtEmail = By.id("email");
    private final By btnSendInstructions = By.xpath("//input[@value='Send Instructions']");
    private final By lblResetPasswordPage = By.xpath("//h1[contains(text(),'Forgot Password')]");
    private final By lblEmailSent = By.xpath("//p[contains(text(),'Instructions have been sent to your email')]");
    private final By resetPasswordForm = By.id("ResetPasswordForm");

    // Actions
    public void goToLoginPage() {
        driver.get("http://railwayb1.somee.com/Account/Login.cshtml");
    }

    public void clickForgotPassword() {
        driver.findElement(lnkForgotPassword).click();
    }

    public void verifyResetPasswordPageDisplayed() {
        if (!driver.findElement(lblResetPasswordPage).isDisplayed()) {
            throw new AssertionError("Reset Password Page is not displayed.");
        }
    }

    public void enterEmail(String email) {
        driver.findElement(txtEmail).sendKeys(email);
    }

    public void submitRequest() {
        driver.findElement(btnSendInstructions).click();
    }

    public void verifyEmailSentMessage() {
        if (!driver.findElement(lblEmailSent).isDisplayed()) {
            throw new AssertionError("Confirmation message not displayed.");
        }
    }

    // Mailinator interaction (mocked as placeholders, since we can't really interact with external sites here)
    public void openMailinator(String username) {
        driver.get("https://www.mailinator.com/v4/public/inboxes.jsp?to=" + username);
    }

    public void openResetEmail() {
        // Placeholder: you will need to locate the reset email row and click on it
    }

    public void clickResetLink() {
        // Placeholder: switch to email iframe and click on reset password link
    }

    public void verifyResetPasswordFormDisplayed() {
        if (!driver.findElement(resetPasswordForm).isDisplayed()) {
            throw new AssertionError("Reset Password form not displayed.");
        }
    }
}

