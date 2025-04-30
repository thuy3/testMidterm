package PageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TimeTablePage extends GeneralPage {

    public TimeTablePage(WebDriver driver) {
        super(driver);
    }

    private final By _linkBookTicketHueToSaigon = By.xpath("//td[contains(text(),'Huế')]/following-sibling::td[contains(text(),'Sài Gòn')]/following-sibling::td/a");

    public void clickBookTicketHueToSaigon() {
        driver.findElement(_linkBookTicketHueToSaigon).click();
    }
}
