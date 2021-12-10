package main.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.entity.Book;
import main.mySqlConnector.Connector;

public class BookService {
    public List<Book> getBooks() throws SQLException{
        List<Book> books = new ArrayList<>();

        Connector.open();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT id,name, authors, description, year, publisher FROM book_detail");
        while (rs.next()) {
            Book b = new Book(rs.getString("id"), rs.getString("name"), rs.getString("authors"), rs.getString("description"), rs.getInt("year"), rs.getString("publisher"));
            books.add(b);
        }
        Connector.close();
        return books;
    }
}
