
package PageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ForgotPasswordPage extends GeneralPage {

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    // Locator
    private final By lnkForgotPassword = By.linkText("Forgot Password page");
    private final By txtEmail = By.id("email");
    private final By btnSendInstructions = By.xpath("//input[@value='Send Instructions']");
    private final By lblResetPasswordPage = By.xpath("//legend[contains(text(),'Password Reset Instructions Form')]");
    private final By lblEmailSent = By.xpath("//p[contains(text(),'We have sent instructions')]");
    private final By resetPasswordForm = By.id("ResetPasswordForm");
    private final By txtNewPassword = By.id("newPassword");
    private final By txtConfirmPassword = By.id("confirmPassword");
    private final By btnResetPassword = By.xpath("//input[@value='Reset Password']");
    private final By lblGeneralError = By.xpath("//p[contains(text(),'Could not reset password')]");
    private final By lblConfirmPasswordError = By.xpath("//span[contains(text(),'The password confirmation did not match')]");

    public void goToLoginPage() {
        driver.get("http://railwayb1.somee.com/Account/Login.cshtml");
    }

    public void clickForgotPassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(lnkForgotPassword));
        link.click();
    }

    public void verifyResetPasswordPageDisplayed() {
        if (!driver.findElement(lblResetPasswordPage).isDisplayed()) {
            throw new AssertionError("Trang Reset Password không hiển thị.");
        }
    }

    public void enterEmail(String email) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(txtEmail));
        emailField.sendKeys(email);
    }

    public void submitRequest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(btnSendInstructions));
        button.click();
    }

    public void verifyEmailSentMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(lblEmailSent));
        if (!message.isDisplayed()) {
            throw new AssertionError("Thông báo xác nhận gửi email không hiển thị.");
        }
    }

    public void openMailinator(String username) {
        driver.get("https://www.mailinator.com/v4/public/inboxes.jsp?to=" + username);
    }

    public void openResetEmail() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailRow = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id='inboxTable']//tr[1]"))); // Cập nhật XPath sau khi debug
        emailRow.click();
    }

    public void clickResetLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.switchTo().frame("publicshowmaildivcontent"); // update ID iframe after debug
        WebElement resetLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Reset Password')]"))); // Cập nhật XPath sau khi debug
        resetLink.click();
        driver.switchTo().defaultContent();
    }

    public void verifyResetPasswordFormDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement form = wait.until(ExpectedConditions.visibilityOfElementLocated(resetPasswordForm));
        if (!form.isDisplayed()) {
            throw new AssertionError("Form thay đổi mật khẩu không hiển thị.");
        }
        // In HTML để debug locator
        System.out.println("HTML của trang reset mật khẩu:\n" + driver.getPageSource());
    }

    public void enterPasswordDetails(String newPassword, String confirmPassword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement newPassField = wait.until(ExpectedConditions.visibilityOfElementLocated(txtNewPassword));
        WebElement confirmPassField = wait.until(ExpectedConditions.visibilityOfElementLocated(txtConfirmPassword));
        newPassField.sendKeys(newPassword);
        confirmPassField.sendKeys(confirmPassword);
    }

    public void clickResetPasswordButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(btnResetPassword));
        button.click();
    }

    public void verifyGeneralErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(lblGeneralError));
        if (!error.isDisplayed()) {
            throw new AssertionError("Thông báo lỗi chung 'Could not reset password...' không hiển thị.");
        }
    }

    public void verifyConfirmPasswordErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(lblConfirmPasswordError));
        if (!error.isDisplayed()) {
            throw new AssertionError("Thông báo lỗi xác nhận mật khẩu 'The password confirmation did not match...' không hiển thị.");
        }
    }
}
