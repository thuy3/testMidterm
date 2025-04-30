package Railway;

import org.testng.annotations.Test;
import PageObjects.Railway.*;
import org.openqa.selenium.*;
import org.testng.Assert;

import static Common.Constant.Constant.*;

public class BookingTest extends BaseTest {

    @Test
    public void TC14_BookTicketSuccessfully() {
        BookTicketPage book = new BookTicketPage(driver);
        book.login("testuser@mailinator.com", "12345678");
        book.goToBookTicketPage();
        book.selectTicket("Sài Gòn", "Nha Trang", "7/5/2025", "Soft bed", "1");
        book.submitBooking();
        book.verifyBookingSuccess();
    }

    @Test
    public void TC15_BookMultipleTickets() {
        BookTicketPage book = new BookTicketPage(driver);
        book.login("testuser@mailinator.com", "12345678");
        book.goToBookTicketPage();
        for (int i = 0; i < 5; i++) {
            book.selectTicket("Sài Gòn", "Nha Trang", "8/5/2025", "Soft bed", "1");
            book.submitBooking();
            book.verifyBookingSuccess();
        }
    }

    @Test
    public void TC16_ViewMyTickets() {
        BookTicketPage book = new BookTicketPage(driver);
        book.login("testuser@mailinator.com", "12345678");
        book.goToMyTicketPage();
        book.verifyTicketsDisplayed();
    }
}

