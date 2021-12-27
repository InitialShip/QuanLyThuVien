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
        ResultSet rs = statement.executeQuery("SELECT * FROM book");
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
    public static int updateData(Book book) throws SQLException{
        Connector.open();
        Connector.getCnt().setAutoCommit(false);
        String sql = "UPDATE book SET title=?, author=?, description=?, year=?, publisher=?, category_id=?, location=? WHERE id=?";
        int result = 0;
        PreparedStatement statement = Connector.getCnt().prepareStatement(sql);
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getDescription());
        statement.setInt(4, book.getYear());
        statement.setString(5, book.getPublisher());
        statement.setInt(6, book.getCategoryId());
        statement.setString(7, book.getPlace());
        statement.setString(8, book.getId());
        result = statement.executeUpdate();

        if(book.getImageBinary() != null){
            statement = Connector.getCnt().prepareStatement("UPDATE book SET  book_cover=? WHERE id=?");
            statement.setBlob(1, book.getImageBinary());
            statement.setString(2, book.getId());
            result = statement.executeUpdate();
        }

        if(result == 1){
            Connector.getCnt().commit();
        } else{
            Connector.getCnt().rollback();
        }
        statement.close();
        Connector.close();
        return result;
    }
    public static int insertData(Book book) throws SQLException{
        Connector.open();
        Connector.getCnt().setAutoCommit(false);
        int result = 0;
        String sql = "INSERT INTO book(id,title,author,description,year,publisher,category_id,location,disable,date_added)" + 
            "VALUES (?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement statement = Connector.getCnt().prepareStatement(sql);

        result = statement.executeUpdate();
        Connector.close();
        return result;
    }
    public static Boolean isIdExisted(String bookId) throws SQLException{
        Connector.open();
        PreparedStatement statement = Connector.getCnt().prepareStatement("SELECT id FROM book WHERE id = ?");
        ResultSet rs = statement.executeQuery();

        statement.close();
        Connector.close();
        if(rs.isBeforeFirst()){
            return true;
        }
        return false;
    }
}
