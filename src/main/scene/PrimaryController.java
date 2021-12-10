package main.scene;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import main.entity.Book;
import main.service.BookService;

public class PrimaryController {
    List<Book> books = new ArrayList<>();
    BookService bookService = new BookService();
    @FXML
    private void sayHello() throws SQLException{
        books = bookService.getBooks();
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }
}
