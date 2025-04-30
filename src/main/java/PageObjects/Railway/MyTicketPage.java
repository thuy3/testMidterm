package PageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MyTicketPage extends GeneralPage {

    public MyTicketPage(WebDriver driver) {
        super(driver);
    }

    private final By myTicketTab = By.xpath("//a[@href='/Page/ManageTicket.cshtml']");
    private final By cancelTicketButton = By.xpath("//input[@value='Cancel']");
    private final By confirmationDialog = By.id("confirmButton");

    public MyTicketPage clickMyTicketTab() {
        driver.findElement(myTicketTab).click();
        return this;
    }

    public boolean isMyTicketPageDisplayed() {
        return driver.getCurrentUrl().contains("ManageTicket");
    }

    public void cancelTicketWithId(String ticketId) {
        WebElement ticket = driver.findElement(By.id(ticketId));
        ticket.findElement(cancelTicketButton).click();
    }

    public void confirmCancellation() {
        driver.findElement(confirmationDialog).click();
    }
}
