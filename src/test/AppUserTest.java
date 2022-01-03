package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.entity.Book;
import main.manager.AppUserManager;
import main.manager.BookManager;
import main.mySqlConnector.Connector;
import main.service.AppUserService;

public class AppUserTest {
    @Test
    public void testLoadData() throws SQLException{
        AppUserManager.getInstance();
        AppUserManager.loadData("CS1960008");
        assertEquals(AppUserManager.getUser().getFirstName(), "Destiney");
        assertEquals(AppUserManager.getUser().getLastName(), "Lynn");
        assertEquals(AppUserManager.getUser().getCanOrder(), true);
    }
    @Test
    public void testCanOrder() throws SQLException{
        assertEquals(AppUserService.canOrder("CS1960008"), true);
        assertEquals(AppUserService.canOrder("EP0650007"), true);
        assertEquals(AppUserService.canOrder("PS4160002"), false);
    }
    @Test
    public void testIsIdCardValid() throws SQLException{
        assertEquals(AppUserService.isIdCardValid("CS1960008"), true);
        assertEquals(AppUserService.isIdCardValid("EP0650007"), false);
        assertEquals(AppUserService.isIdCardValid("PS4160002"), true);
    }
    @Test
    public void testGetRecentOrder() throws SQLException{
        BookManager.getInstance();
        BookManager.loadData();
        AppUserManager.getInstance();
        AppUserManager.loadData("PS4160002");
        AppUserManager.getRecentOrder();
        List<Book> testList = new ArrayList<>();
        Connector.open();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT order_detail.* FROM library_db.order, library_db.order_detail WHERE id = order_id AND user_id = 'PS4160002' AND order_status_id = 1;");
        while(rs.next()){
            Book book = BookManager.getBook(rs.getString("book_id"));
            testList.add(book);
        }
        statement.close();
        Connector.close();
        assertEquals(AppUserManager.getUser().getOrder(), testList);
    }
}
