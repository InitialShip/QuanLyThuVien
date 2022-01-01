package main.service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import main.entity.Book;
import main.entity.Order;
import main.manager.OrderManager;
import main.mySqlConnector.Connector;

public class OrderService {
    public static void getAllOrder() throws SQLException{
        Connector.open();
        OrderManager.getInstance();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs =  statement.executeQuery("SELECT * FROM library_db.order");
        while(rs.next()){
            Order order = new Order();
            order.setId(rs.getInt("order_id"));
            order.setUserId(rs.getString("user_id"));
            order.setOrderDate(rs.getDate("order_date"));
            order.setExpireDate(rs.getDate("expire_date"));
            order.setStatusId(rs.getInt("status_id"));
            order.setFine(rs.getInt("fine"));

            OrderManager.addOrder(order);
        }
        statement.close();
        Connector.close();
    }
    public static Boolean createOrder(String userId) throws SQLException{
        Boolean result = false;
        Connector.open();
        PreparedStatement statement = Connector.getCnt().prepareStatement("INSERT INTO library_db.order(user_id,order_date,expire_date,order_status_id)VALUES(?,?,?,?)");
        statement.setString(1, userId);
        statement.setDate(2, Date.valueOf(LocalDate.now()));
        statement.setDate(3, Date.valueOf(LocalDate.now().plusDays(30)));
        statement.setInt(4, 1);

        if(statement.executeUpdate() > 0)
            result = true;
        statement.close();
        Connector.close();
        return result;
    }
    public static int getLatestOrderId(String userId) throws SQLException{
        int result = -1;
        Connector.open();
        PreparedStatement statement = Connector.getCnt().prepareStatement("SELECT id FROM library_db.order WHERE user_id = ? AND order_status_id = 1");
        statement.setString(1, userId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            result = rs.getInt("id");
        }
        statement.close();
        Connector.close();
        return result;
    }
    public static Boolean submitOrder(int orderId,List<Book> books) throws SQLException{
        Boolean result = true;
        Connector.open();
        Connector.getCnt().setAutoCommit(false);
        PreparedStatement statement = Connector.getCnt().prepareStatement("INSERT INTO library_db.order_detail (order_id,book_id,status_id)VALUES(?,?,?);");
        for (Book book : books) {
            statement.setInt(1, orderId);
            statement.setString(2, book.getId());
            statement.setInt(3, 1);
            statement.addBatch();
        }
        int[] i = statement.executeBatch();
        for (int j : i) {
            if(j == 0)
                result = false;
        }
        if(result)
           Connector.getCnt().commit();
        statement.close(); 
        Connector.close();
        return result;
    }
    public static void getLastestUserOrder() throws SQLException{
        Connector.open();
        String sql = "SELECT * FROM library.order WHERE status_id IN (1,2)";
        Connector.close();
    }
   
}
