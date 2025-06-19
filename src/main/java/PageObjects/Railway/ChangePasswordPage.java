package PageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ChangePasswordPage extends GeneralPage {

    public ChangePasswordPage(WebDriver driver) {
        super(driver);
    }

    // Tab “Change Password”
    //private final By changePasswordTab = By.xpath("//a[@href='/Account/ChangePassword.html']");
    private final By changePasswordTab = By.xpath("//a[contains(@href,'ChangePassword') and span[text()='Change password']]");

    private final By txtCurrentPassword = By.id("currentPassword");
    private final By txtNewPassword = By.id("newPassword");
    private final By txtConfirmPassword = By.id("confirmPassword");
    private final By btnChangePassword = By.xpath("//input[@value='Change Password']");
    private final By lblSuccessMessage = By.xpath("//p[@class='message success']");

    public ChangePasswordPage clickChangePasswordTab() {
        driver.findElement(changePasswordTab).click();
        return this;
    }

    public boolean isChangePasswordPageDisplayed() {
        return driver.getCurrentUrl().contains("ChangePassword");
    }

    public void changePassword(String current, String newPass, String confirm) {
        driver.findElement(txtCurrentPassword).sendKeys(current);
        driver.findElement(txtNewPassword).sendKeys(newPass);
        driver.findElement(txtConfirmPassword).sendKeys(confirm);
        driver.findElement(btnChangePassword).click();
    }

    public void resetPasswordToOriginal(String currentPassword, String originalPassword) {
        clickChangePasswordTab();
        changePassword(currentPassword, originalPassword, originalPassword);

        verifySuccessMessage("Your password has been updated");
    }


    public void verifySuccessMessage(String expectedMsg) {
        String actual = driver.findElement(lblSuccessMessage).getText();
        if (!actual.contains(expectedMsg)) {
            throw new AssertionError("Expected: " + expectedMsg + ", but got: " + actual);
        }
    }
}
