package PageObjects.Railway;

import Common.Common.Utilities;
import PageObjects.Railway.GeneralPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
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
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//tr[@class='OddRow']")));
            WebElement cancelButton = row.findElement(By.xpath(".//input[@value='Cancel']"));
            String onclick = cancelButton.getAttribute("onclick");
            if (onclick == null || !onclick.contains("DeleteTicket")) {
                System.out.println("HTML của trang My Ticket:\n" + driver.getPageSource());
                throw new IllegalStateException("Không tìm thấy thuộc tính onclick hợp lệ: " + onclick);
            }
            return onclick.replaceAll(".*?DeleteTicket\\((\\d+)\\).*", "$1");
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy bookingId, HTML của trang:\n" + driver.getPageSource());
            throw new AssertionError("Không thể lấy bookingId: " + e.getMessage());
        }
    }

    public void goToBookTicketPage() {
        driver.findElement(By.xpath("//span[text()='Book ticket']")).click();
    }

    public void selectTicket(String departStation, String arriveStation, String date, String seatType, String ticketAmount) {
        selectDepartFrom(departStation);
        selectArriveAt(arriveStation);
        selectSeatType(seatType);
        selectTicketAmount(ticketAmount);
        selectOptionInDropdown(selectDepartDate, date);
    }

    public void submitBooking() {
        clickBookTicketButton();
    }

    public void verifyBookingSuccess() {
        boolean isSuccess = isTicketBookedSuccessfullyDisplayed();
        if (!isSuccess) {
            System.out.println("HTML của trang sau khi đặt vé:\n" + driver.getPageSource());
            throw new AssertionError("Booking was not successful.");
        }
    }

    public void goToMyTicketPage() {
        driver.findElement(By.xpath("//span[text()='My ticket']")).click();
    }

    public void verifyTicketsDisplayed() {
        List<WebElement> rows = driver.findElements(By.xpath("//table[@class='MyTable']//tr"));
        if (rows.size() <= 1) {
            throw new AssertionError("No tickets found in My Ticket.");
        }
    }

    public void cancelTicket(String bookingId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//input[@type='button' and @value='Cancel' and contains(@onclick, 'DeleteTicket(" + bookingId + ")')]")));
        Utilities.scrollToElement(driver, cancelButton);
        cancelButton.click();
    }

    public void confirmCancel() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            System.out.println("Không có alert xác nhận: " + e.getMessage());
        }
    }

    public void verifyTicketDisappeared(String bookingId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> rows = driver.findElements(By.xpath(
                "//input[@type='button' and @value='Cancel' and contains(@onclick, 'DeleteTicket(" + bookingId + ")')]"));
        if (!rows.isEmpty()) {
            System.out.println("HTML của trang My Ticket:\n" + driver.getPageSource());
            throw new AssertionError("Vé với ID " + bookingId + " vẫn hiển thị sau khi hủy.");
        }
    }
}