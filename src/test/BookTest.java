package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import main.manager.BookManager;
import main.service.BookService;

public class BookTest {
    BookManager bookManager = BookManager.getInstance();
    @BeforeAll
    public void testLoadBookData() throws SQLException{
        BookManager.loadData();
        assertEquals(BookManager.getAllBooks().size(), BookService.getCount());
    }

    @Test
    public void testGetBooks() throws SQLException{
        BookManager.loadData();
        assertEquals(BookManager.getBook("B001").getId(), "B001");
        assertEquals(BookManager.getBook("B002").getId(), "B002");
        assertEquals(BookManager.getBook("B003").getTitle(), "Drawing Portraits: Faces and Figures");
        assertEquals(BookManager.getBook("B004").getAuthor(), "Soizic Mouton");
        assertEquals(BookManager.getBook("B005").getPublisher(), "Arcturus Publishing");
    }
}
