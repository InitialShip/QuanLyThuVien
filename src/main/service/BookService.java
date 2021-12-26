package main.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.entity.Book;
import main.manager.BookManager;
import main.mySqlConnector.Connector;

public class BookService {
    public static void getData() throws SQLException{
        BookManager.getInstance();

        Connector.open();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM book_detail");
        while (rs.next()) {
            Book book = new Book();
            
            book.setId(rs.getString("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setDescription(rs.getString("description"));
            book.setYear(rs.getInt("year"));
            book.setPublisher(rs.getString("publisher"));
            book.setCategoryId(rs.getInt("category_id"));
            book.setPlace(rs.getString("location"));
            book.setImageBinary(rs.getBinaryStream("book_cover"));

            BookManager.addBook(book);
        }
        statement.close();
        Connector.close();
    }
    public static void updateData(Book book) throws SQLException{
        Connector.open();
        Connector.getCnt().setAutoCommit(false);
        String sql = "UPDATE book_detail SET title=?, author=?, description=?, year=?, publisher=?, category_id=?, location=?, book_cover=? WHERE id=?";
        PreparedStatement statement = Connector.getCnt().prepareStatement(sql);
        //statement.addBatch("");
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getDescription());
        statement.setInt(4, book.getYear());
        statement.setString(5, book.getPublisher());
        statement.setInt(6, book.getCategoryId());
        statement.setString(7, book.getPlace());
        statement.setBlob(8, book.getImageBinary());
        statement.setString(9, book.getId());

        statement.executeUpdate();
        Connector.getCnt().commit();
        Connector.close();
    }
}
