package PageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordResetPage extends GeneralPage {

    public PasswordResetPage(WebDriver driver) {
        super(driver);
    }

    private final By txtNewPassword = By.id("newPassword");
    private final By txtConfirmPassword = By.id("confirmPassword");
    private final By txtResetToken = By.id("resetToken");
    private final By btnResetPassword = By.xpath("//input[@value='Reset Password']");

    private final By lblTopErrorMessage = By.xpath("//p[@class='message error']");
    private final By lblTokenError = By.xpath("//label[@for='resetToken' and contains(@class, 'validation-error')]");

    public void goTo() {
        driver.get("http://railwayb1.somee.com/Account/PasswordReset");
    }

    public void fillNewPasswords(String pass, String confirmPass) {
        driver.findElement(txtNewPassword).sendKeys(pass);
        driver.findElement(txtConfirmPassword).sendKeys(confirmPass);
    }

    public void clearResetToken() {
        driver.findElement(txtResetToken).clear();
    }

    public void clickResetPassword() {
        driver.findElement(btnResetPassword).click();
    }

    public String getTopErrorMessage() {
        return driver.findElement(lblTopErrorMessage).getText();
    }

    public String getTokenErrorMessage() {
        return driver.findElement(lblTokenError).getText();
    }
}

