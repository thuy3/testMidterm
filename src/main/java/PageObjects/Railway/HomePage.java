package PageObjects.Railway;

import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends GeneralPage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.navigate().to(Constant.RAILWAY_URL);
        return this;
    }

    private final By myTicketTab = By.xpath("//a[@href='/Page/ManageTicket.cshtml']");
    private final By changePasswordTab = By.xpath("//a[@href='/Account/ChangePassword.cshtml']");
    private final By logoutTab = By.xpath("//a[@href='/Account/Logout']");
    private final By _tabTimetable = By.linkText("Timetable");

    public boolean isMyTicketTabDisplayed() {
        return isElementDisplayed(myTicketTab);
    }

    public boolean isChangePasswordTabDisplayed() {
        return isElementDisplayed(changePasswordTab);
    }

    public boolean isLogoutTabDisplayed() {
        return isElementDisplayed(logoutTab);
    }

    public MyTicketPage clickMyTicketTab() {
        driver.findElement(myTicketTab).click();
        return new MyTicketPage(driver);
    }

    public ChangePasswordPage clickChangePasswordTab() {
        driver.findElement(changePasswordTab).click();
        return new ChangePasswordPage(driver);
    }

    public LoginPage clickLogoutTab() {
        driver.findElement(logoutTab).click();
        return new LoginPage(driver);
    }

    private boolean isElementDisplayed(By elementLocator) {
        try {
            return driver.findElement(elementLocator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public RegisterPage gotoRegisterPage() {
        By registerTab = By.xpath("//a[@href='/Account/Register.cshtml']");
        driver.findElement(registerTab).click();
        return new RegisterPage(driver);
    }

    public BookTicketPage clickBookTicketTab() {
        WebElement bookTicketTab = driver.findElement(By.xpath("//a[@href='/Page/BookTicketPage.cshtml']"));
        bookTicketTab.click();
        return new BookTicketPage(driver);
    }

    public TimeTablePage gotoTimeTable() {
        driver.findElement(_tabTimetable).click();
        return new TimeTablePage(driver);
    }

    public WebElement getTabMyTicket() {
        return driver.findElement(myTicketTab);
    }

    public MyTicketPage gotoMyTicketPage() {
        getTabMyTicket().click();
        return new MyTicketPage(driver);
    }
}
