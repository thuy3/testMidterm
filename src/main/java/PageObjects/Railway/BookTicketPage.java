package PageObjects.Railway;

import Common.Common.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class BookTicketPage extends GeneralPage {
    public BookTicketPage(WebDriver driver) {
        super(driver);
    }

    private final By selectDepartFrom = By.xpath("//select[@name='DepartStation']");
    private final By selectArriveAt = By.xpath("//select[@name='ArriveStation']");
    private final By selectSeatType = By.xpath("//select[@name='SeatType']");
    private final By selectTicketAmount = By.xpath("//select[@name='TicketAmount']");
    private final By btnBookTicket = By.xpath("//input[@value='Book ticket']");
    private final By selectDepartDate = By.xpath("//select[@name='Date']");
    private final By departDateField = By.xpath("//div[@id='content']//td[4]");
    private final By departStationField = By.xpath("//div[@id='content']//td[1]");
    private final By arriveStationField = By.xpath("//div[@id='content']//td[2]");
    private final By seatTypeField = By.xpath("//div[@id='content']//td[3]");
    private final By amountField = By.xpath("//div[@id='content']//td[7]");
    private final By successMessage = By.xpath("//div[@id='content']//h1[contains(text(),'Ticket booked successfully!')]");

    public String getDepartDate() {
        return driver.findElement(departDateField).getText();
    }

    public String getDepartStation() {
        return driver.findElement(departStationField).getText();
    }

    public String getArriveStation() {
        return driver.findElement(arriveStationField).getText();
    }

    public String getSeatType() {
        return driver.findElement(seatTypeField).getText();
    }

    public String getAmount() {
        return driver.findElement(amountField).getText();
    }

    public boolean isTicketBookedSuccessfullyDisplayed() {
        try {
            return driver.findElement(successMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void selectRandomDepartDate() {
        WebElement dropdown = driver.findElement(selectDepartDate);
        Utilities.scrollToElement(driver, dropdown);
        Select select = new Select(dropdown);
        List<WebElement> options = select.getOptions();
        int randomIndex = new Random().nextInt(options.size());
        select.selectByIndex(randomIndex);
    }

    private void selectOptionInDropdown(By locator, String value) {
        WebElement dropdown = driver.findElement(locator);
        Utilities.scrollToElement(driver, dropdown);
        Select select = new Select(dropdown);
        select.selectByVisibleText(value);
    }

    public void selectDepartFrom(String station) {
        selectOptionInDropdown(selectDepartFrom, station);
    }

    public void selectArriveAt(String station) {
        selectOptionInDropdown(selectArriveAt, station);
    }

    public void selectSeatType(String seatType) {
        selectOptionInDropdown(selectSeatType, seatType);
    }

    public void selectTicketAmount(String amount) {
        selectOptionInDropdown(selectTicketAmount, amount);
    }

    public void clickBookTicketButton() {
        WebElement button = driver.findElement(btnBookTicket);
        Utilities.scrollToElement(driver, button);
        button.click();
    }

    public void selectDepartDate(int daysToAdd) {
        LocalDate today = LocalDate.now();
        LocalDate desiredDate = today.plusDays(daysToAdd);

        if (daysToAdd >= 3 && daysToAdd <= 30) {
            String formattedDate = desiredDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            selectOptionInDropdown(selectDepartDate, formattedDate);
        } else {
            System.out.println("Only allow booking from 3 to 30 days ahead.");
        }
    }

    public boolean verifyTicketDetails(String departStation, String arriveStation, String seatType, String departDate, String amount) {
        WebElement row = driver.findElement(By.xpath("//table[@class='MyTable WideTable']//tr[@class='OddRow']"));
        return row.findElement(By.xpath("td[1]")).getText().equals(departStation) &&
                row.findElement(By.xpath("td[2]")).getText().equals(arriveStation) &&
                row.findElement(By.xpath("td[3]")).getText().equals(seatType) &&
                row.findElement(By.xpath("td[4]")).getText().equals(departDate) &&
                row.findElement(By.xpath("td[7]")).getText().equals(amount);
    }

    public String getDepartFrom() {
        return new Select(driver.findElement(selectDepartFrom)).getFirstSelectedOption().getText();
    }

    public String getArriveAt() {
        return new Select(driver.findElement(selectArriveAt)).getFirstSelectedOption().getText();
    }

    public String getBookingId() {
        String currentUrl = driver.getCurrentUrl();
        String[] parts = currentUrl.split("=");
        return parts.length > 1 ? parts[1] : "";
    }
}
