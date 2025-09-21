package Railway;

import org.testng.annotations.Test;
import PageObjects.Railway.*;
import org.openqa.selenium.*;
import org.testng.Assert;

import static Common.Constant.Constant.*;

public class BookingTest extends BaseTest {
    private void loginBeforeBooking(String username, String password) {
        HomePage homePage = new HomePage(driver);
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(username, password);
    }

    //BUG: dropdown don't have S
    @Test
    public void TC14_BookTicketSuccessfully() {
        loginBeforeBooking("test123@mailinator.com", "httt12345");
        BookTicketPage book = new BookTicketPage(driver);
        book.goToBookTicketPage();
        String departDate = java.time.LocalDate.now().plusDays(5).format(java.time.format.DateTimeFormatter.ofPattern("M/d/yyyy"));
        book.selectTicket("Sài Gòn", "Nha Trang", departDate, "Soft bed with air conditioner", "1");
        book.submitBooking();
        book.verifyBookingSuccess();
        // check ticket detail
        Assert.assertTrue(book.verifyTicketDetails("Sài Gòn", "Nha Trang", "Soft bed with air conditioner", departDate, "1"),
                "Thông tin vé không đúng!");
    }

    @Test
    public void TC15_OpenBookTicketFromTimetable() {
        loginBeforeBooking("test123@mailinator.com", "httt12345");
        TimeTablePage timetable = new TimeTablePage(driver);
        timetable.goToTimetablePage();
        timetable.clickBookTicketHueToSaigon();
        timetable.verifyDepartAndArriveStations("Huế", "Sài Gòn");
    }

    @Test
    public void TC16_CancelTicket() {
        loginBeforeBooking("test123@mailinator.com", "httt12345");
        BookTicketPage book = new BookTicketPage(driver);
        book.goToBookTicketPage();
        String departDate = java.time.LocalDate.now().plusDays(5).format(java.time.format.DateTimeFormatter.ofPattern("M/d/yyyy"));
        book.selectTicket("Huế", "Sài Gòn", departDate, "Soft bed with air conditioner", "1");
        book.submitBooking();
        book.goToMyTicketPage();
        String bookingId = book.getBookingId();
        book.cancelTicket(bookingId);
        book.confirmCancel();
        book.verifyTicketDisappeared(bookingId);
    }
}