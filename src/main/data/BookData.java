package main.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.entity.Book;
import main.service.BookDataService;

public class BookData {
    private static List<Book> books = null;

    private static BookData instance = null;
    private BookData(){
        books = new ArrayList<>();
    }
    public static BookData getInstance() throws SQLException{
        if (instance == null){
            instance = new BookData();
        }
        return instance;
    }
    //getter
    public List<Book> getBooks(){
        return books;
    }
    //methods
    public static void addBook(Book book){
        books.add(book);
    }
    public static void loadData() throws SQLException{
        BookDataService.getData();
    }
    public void sort(){
        books.sort((b1,b2) -> {
            String title1 = b1.getTitle();
            String title2 = b2.getTitle();
            return title1.compareTo(title2);
        });
    }
}
