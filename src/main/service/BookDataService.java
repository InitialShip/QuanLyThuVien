package main.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.data.BookData;
import main.entity.Book;
import main.mySqlConnector.Connector;

public class BookDataService {
    public static void getBooks() throws SQLException{
        BookData bookData = BookData.getInstance();

        Connector.open();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT id,name, authors, description, year, publisher FROM book_detail");
        while (rs.next()) {
            Book book = new Book(
                rs.getString("id"), 
                rs.getString("name"), 
                rs.getString("authors"), 
                rs.getString("description"), 
                rs.getInt("year"), 
                rs.getString("publisher"));
                bookData.addBook(book);
        }
        Connector.close();
    }
}
