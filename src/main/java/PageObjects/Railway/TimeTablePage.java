package PageObjects.Railway;

import Common.Common.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class TimeTablePage extends GeneralPage {

    public TimeTablePage(WebDriver driver) {
        super(driver);
    }

    private final By _linkBookTicketHueToSaigon = By.xpath(
            "//td[contains(text(),'Huế')]/following-sibling::td[contains(text(),'Sài Gòn')]/following-sibling::td[4]/a[contains(text(),'book ticket')]");
    private final By selectDepartFrom = By.xpath("//select[@name='DepartStation']");
    private final By selectArriveAt = By.xpath("//select[@name='ArriveStation']");

    public void goToTimetablePage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement timetableLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Timetable']")));
        timetableLink.click();
    }

    public void clickBookTicketHueToSaigon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement bookLink = wait.until(ExpectedConditions.elementToBeClickable(_linkBookTicketHueToSaigon));
        Utilities.scrollToElement(driver, bookLink);
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bookLink);
            wait.until(ExpectedConditions.urlContains("BookTicketPage.cshtml"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(selectDepartFrom));
            wait.until(ExpectedConditions.visibilityOfElementLocated(selectArriveAt));
            System.out.println("URL sau khi click: " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println("HTML của trang sau khi click link book ticket:\n" + driver.getPageSource());
            throw new AssertionError("Không thể click link book ticket hoặc không chuyển đến trang Book Ticket: " + e.getMessage());
        }
    }

    public void verifyDepartAndArriveStations(String expectedDepart, String expectedArrive) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement departDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(selectDepartFrom));
            WebElement arriveDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(selectArriveAt));
            String actualDepart = new Select(departDropdown).getFirstSelectedOption().getText();
            String actualArrive = new Select(arriveDropdown).getFirstSelectedOption().getText();
            if (!actualDepart.equals(expectedDepart) || !actualArrive.equals(expectedArrive)) {
                throw new AssertionError("Depart from: " + actualDepart + " (expected: " + expectedDepart + "), Arrive at: " + actualArrive + " (expected: " + expectedArrive + ")");
            }
            System.out.println("Verified: Depart from " + actualDepart + ", Arrive at " + actualArrive);
        } catch (Exception e) {
            System.out.println("URL hiện tại: " + driver.getCurrentUrl());
            System.out.println("HTML của trang Book Ticket:\n" + driver.getPageSource());
            throw new AssertionError("Không tìm thấy dropdown DepartStation hoặc ArriveStation: " + e.getMessage());
        }
    }
}