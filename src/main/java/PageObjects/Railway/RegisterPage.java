package PageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends GeneralPage {

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    private final By _txtEmail = By.id("email");
    private final By _txtPassword = By.id("password");
    private final By _txtConfirmPassword = By.id("confirmPassword");
    private final By _txtPid = By.id("pid");
    private final By _btnRegister = By.xpath("//input[@value='Register']");
    private final By _lblErrorMessage = By.xpath("//p[@class='message error']");
    private final By _lblErrorPassword = By.xpath("//*[@id='RegisterForm']/fieldset/ol/li[2]/label[2]");
    private final By _lblErrorPid = By.xpath("//*[@id='RegisterForm']/fieldset/ol/li[4]/label[2]");

    public void goToRegisterPage() {
        driver.get("http://railwayb1.somee.com/Account/Register.cshtml"); // Cập nhật lại URL nếu khác
    }

    public void enterEmail(String email) {
        driver.findElement(_txtEmail).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(_txtPassword).sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        driver.findElement(_txtConfirmPassword).sendKeys(confirmPassword);
    }

    public void enterPID(String pid) {
        driver.findElement(_txtPid).sendKeys(pid);
    }

    public void clickRegisterButton() {
        driver.findElement(_btnRegister).click();
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(_lblErrorMessage);
    }

    private boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void registerNewAccount(String email, String password, String confirmPassword, String pid) {
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        enterPID(pid);
        clickRegisterButton();
    }

    public void fillRegisterForm(String email, String password, String confirmPassword, String pid) {
        registerNewAccount(email, password, confirmPassword, pid);
    }

    public void submit() {
        clickRegisterButton();
    }

    public String getPasswordErrorMessage() {
        return driver.findElement(_lblErrorPassword).getText();
    }

    public String getPIDErrorMessage() {
        return driver.findElement(_lblErrorPid).getText();
    }

    public String getRegisterErrorMessage() {
        return driver.findElement(_lblErrorMessage).getText();
    }

    public void verifyErrorMessage(String expectedMessage) {
        String actual = getRegisterErrorMessage();
        if (!actual.contains(expectedMessage)) {
            throw new AssertionError("Expected: " + expectedMessage + ", but got: " + actual);
        }
    }

    public void verifyRegistrationSuccess() {
        String successText = driver.findElement(By.id("content")).getText();
        if (!successText.contains("Thank you for registering your account")) {
            throw new AssertionError("Registration was not successful.");
        }
    }
}
