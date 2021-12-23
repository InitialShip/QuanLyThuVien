package main.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.data.BookData;
import main.entity.Book;
import main.mySqlConnector.Connector;

public class BookDataService {
    public static void getData() throws SQLException{
        BookData.getInstance();

        Connector.open();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM book_detail");
        while (rs.next()) {
            Book book = new Book(
                rs.getString("id"), 
                rs.getString("name"), 
                rs.getString("authors"), 
                rs.getString("description"), 
                rs.getInt("year"), 
                rs.getString("publisher"),
                rs.getInt("category_id"));
                BookData.addBook(book);
        }
        Connector.close();
    }
}
