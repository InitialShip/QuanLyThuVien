package main.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.entity.Book;
import main.manager.BookManager;
import main.mySqlConnector.Connector;

public class BookService {
    /**
     * Get book data from database
     * @throws SQLException
     */
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
            book.setDisabled(rs.getBoolean("disable"));
            book.setDateAdded(rs.getDate("date_added"));

            BookManager.addBook(book);
        }
        statement.close();
        Connector.close();
    }
    /**
     * update book data
     * @param book
     * @return true if success
     * @throws SQLException
     */
    public static Boolean updateData(Book book) throws SQLException{
        Connector.open();
        Connector.getCnt().setAutoCommit(false);
        String sql = "UPDATE book SET title=?,author=?, description=?, year=?, publisher=?, category_id=?, location=?, disable=?, date_added=? WHERE id=?;";
        int result = 0;
        PreparedStatement statement = Connector.getCnt().prepareStatement(sql);
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getDescription());
        statement.setInt(4, book.getYear());
        statement.setString(5, book.getPublisher());
        statement.setInt(6, book.getCategoryId());
        statement.setString(7, book.getPlace());
        statement.setBoolean(8, book.isDisabled());
        statement.setDate(9, book.getDateAdded());
        statement.setString(10, book.getId());
        result = statement.executeUpdate();
        statement.execute();

        if(book.getImageBinary() != null){
            statement = Connector.getCnt().prepareStatement("UPDATE book SET  book_cover=? WHERE id=?");
            statement.setBlob(1, book.getImageBinary());
            statement.setString(2, book.getId());
            result = statement.executeUpdate();
        }

        if(result != 0){
            Connector.getCnt().commit();
        } else{
            Connector.getCnt().rollback();
        }
        statement.close();
        Connector.close();
        return result>0?true:false;
    }
    /**
     * Insert book data to database
     * @param book
     * @return true if success
     * @throws SQLException
     */
    public static Boolean insertData(Book book) throws SQLException{
        Connector.open();
        Connector.getCnt().setAutoCommit(false);
        int result = 0;
        String sql = "INSERT INTO book(id,title,author,description,year,publisher,category_id,location,book_cover,disable,date_added) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement statement = Connector.getCnt().prepareStatement(sql);
        statement.setString(1, book.getId());
        statement.setString(2, book.getTitle());
        statement.setString(3, book.getAuthor());
        statement.setString(4, book.getDescription());
        statement.setInt(5, book.getYear());
        statement.setString(6, book.getPublisher());
        statement.setInt(7, book.getCategoryId());
        statement.setString(8, book.getPlace());
        statement.setBlob(9, book.getImageBinary());
        statement.setBoolean(10, book.isDisabled());
        statement.setDate(11, book.getDateAdded());

        result = statement.executeUpdate();
        if(result>0){
            Connector.getCnt().commit();
        }else{
            Connector.getCnt().rollback();
        }
        statement.close();
        Connector.close();
        return result>0?true:false;
    }
    /**
     * Check if ID exists
     * @param bookId
     * @return  true if exists 
     * @throws SQLException
     */
    public static Boolean isIdExisted(String bookId) throws SQLException{
        Boolean result = false;
        Connector.open();
        PreparedStatement statement = Connector.getCnt().prepareStatement("SELECT id FROM book WHERE id = ?");
        statement.setString(1, bookId);
        ResultSet rs = statement.executeQuery();
        if(rs.isBeforeFirst()){
            result = true;
        }
        statement.close();
        Connector.close();
        return result;
    }

    public static int getCount() throws SQLException{
        int count = 0;
        Connector.open();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM book");
        while(rs.next()){
            count = rs.getInt(1);
        }
        statement.close();
        Connector.close();
        return count;
    }
}
