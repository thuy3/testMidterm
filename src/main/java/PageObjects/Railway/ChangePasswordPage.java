package PageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ChangePasswordPage extends GeneralPage {

    public ChangePasswordPage(WebDriver driver) {
        super(driver);
    }

    private final By changePasswordTab = By.xpath("//a[@href='/Account/ChangePassword']");

    public ChangePasswordPage clickChangePasswordTab() {
        driver.findElement(changePasswordTab).click();
        return this;
    }

    public boolean isChangePasswordPageDisplayed() {
        return driver.getCurrentUrl().contains("ChangePassword");
    }
}
