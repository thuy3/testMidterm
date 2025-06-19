package PageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends GeneralPage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private final By _txtUsername = By.xpath("//input[@id='username']");
    private final By _txtPassword = By.xpath("//input[@id='password']");
    private final By _btnLogin = By.xpath("//input[@value='login']");
    private final By _lblLoginErrorMsg = By.xpath("//p[@class='message error LoginForm']");

    public void goToLoginPage() {
        driver.get("http://railwayb1.somee.com/Account/Login.cshtml");
    }

    public WebElement getTxtUsername() {
        return driver.findElement(_txtUsername);
    }

    public WebElement getTxtPassword() {
        return driver.findElement(_txtPassword);
    }

    public WebElement getBtnLogin() {
        return driver.findElement(_btnLogin);
    }

    public WebElement getLblLoginErrorMsg() {
        return driver.findElement(_lblLoginErrorMsg);
    }

    public HomePage login(String username, String password) {
        this.getTxtUsername().sendKeys(username);
        this.getTxtPassword().sendKeys(password);
        scrollToElement(getBtnLogin());
        this.getBtnLogin().click();
        return new HomePage(driver);
    }

    private void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
}