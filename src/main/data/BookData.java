package main.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.entity.Book;
import main.service.BookDataService;

public class BookData {
    private static List<Book> books = null;

    private static BookData instance = null;
    private BookData() throws SQLException{
        books = new ArrayList<>();
    }
    public static BookData getInstance() throws SQLException{
        if (instance == null)
            instance = new BookData();
        return instance;
    }
    //getter
    public List<Book> getBooks(){
        return books;
    }
    //methods
    public void addBook(Book book){
        books.add(book);
    }
    public void loadData() throws SQLException{
        BookDataService.getBooks();
    }
    public void sort(){
        books.sort((b1,b2) -> {
            String title1 = b1.getName();
            String title2 = b2.getName();
            return title1.compareTo(title2);
        });
    }
}
