package PageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GeneralPage {
    protected WebDriver driver;

    public GeneralPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By tabLogin = By.xpath("//div[@id='menu']//a[@href='/Account/Login.cshtml']");
    private final By tabLogout = By.xpath("//div[@id='menu']//a[@href='/Account/Logout']");
    private final By lblwelcomeMessage = By.xpath("//div[@class='account']/strong");

    protected WebElement getTabLogin() {
        return driver.findElement(tabLogin);
    }

    protected WebElement getTabLogout() {
        return driver.findElement(tabLogout);
    }

    protected WebElement getLblWelcomeMessage() {
        return driver.findElement(lblwelcomeMessage);
    }

    public String getWelcomeMessage() {
        return this.getLblWelcomeMessage().getText();
    }

    public LoginPage gotoLoginPage() {
        scrollToElement(getTabLogin());
        this.getTabLogin().click();
        return new LoginPage(driver);
    }

    private void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
}