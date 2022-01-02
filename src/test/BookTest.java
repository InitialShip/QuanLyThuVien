package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.junit.Test;

import main.entity.Book;
import main.manager.BookManager;
import main.mySqlConnector.Connector;
import main.service.BookService;

public class BookTest {
    BookManager bookManager = BookManager.getInstance();
    @Test
    public void testGetCount() throws SQLException{
        Connector.open();
        int count = 0;
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT COUNT(id) FROM library_db.book");
        if(rs.next())
            count = rs.getInt(1);
        assertEquals(BookService.getCount(), count);
        statement.close();
        Connector.close();
    }
    @Test
    public void testLoadBookData() throws SQLException{
        BookManager.loadData();
        assertEquals(BookManager.getAllBooks().size(), BookService.getCount());
    }
    @Test
    public void testData() throws SQLException{
        BookManager.loadData();
        Connector.open();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM library_db.book");
        while(rs.next()){
            Book book = BookManager.getBook(rs.getString("id"));
            assertEquals(book.getTitle(), rs.getString("title"));
        }
        Connector.close();
    }
    @Test
    public void testGetBooks() throws SQLException{
        BookManager.loadData();
        assertEquals(BookManager.getBook("B001").getId(), "B001");
        assertEquals(BookManager.getBook("B002").getDateAdded(), Date.valueOf(LocalDate.of(2021, 12, 8)));
        assertEquals(BookManager.getBook("B003").getTitle(), "Drawing Portraits: Faces and Figures");
        assertEquals(BookManager.getBook("B004").getAuthor(), "Soizic Mouton");
        assertEquals(BookManager.getBook("B005").getPublisher(), "Arcturus Publishing");
        assertEquals(BookManager.getBook("B006").getYear(), 2004);
    }
    @Test
    public void testIsExisted() throws SQLException{
        assertEquals(BookService.isIdExisted("B001"), true);
        assertEquals(BookService.isIdExisted("B002"), true);
        assertEquals(BookService.isIdExisted("B099"), false);
        assertEquals(BookService.isIdExisted("B004"), true);
        assertEquals(BookService.isIdExisted("B011"), true);
        assertEquals(BookService.isIdExisted("B105"), false);
        assertEquals(BookService.isIdExisted("B211"), false);
        assertEquals(BookService.isIdExisted("B027"), true);
        assertEquals(BookService.isIdExisted("qf001"), false);
    }
}
