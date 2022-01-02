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
import main.entity.OrderDetail;
import main.manager.OrderManager;
import main.mySqlConnector.Connector;
import main.utility.Utils;

public class OrderService {
    
    public static void getAllOrder() throws SQLException{
        Connector.open();
        OrderManager.getInstance();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs =  statement.executeQuery("SELECT * FROM library_db.order");
        while(rs.next()){
            Order order = new Order();
            order.setOrderId(rs.getInt("id"));
            order.setUserId(rs.getString("user_id"));
            order.setOrderDate(rs.getDate("order_date"));
            order.setExpireDate(rs.getDate("expire_date"));
            order.setOrderStatusId(rs.getInt("order_status_id"));
            OrderManager.addOrder(order);
        }
        statement.close();
        Connector.close();
    }
    public static void getAllOrderDetail() throws SQLException{
        Connector.open();
        OrderManager.getInstance();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM library_db.order_detail");
        while(rs.next()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(rs.getInt("order_id"));
            orderDetail.setBookId(rs.getString("book_id"));
            orderDetail.setReturnedDate(rs.getDate("returned_date"));
            orderDetail.setStatusId(rs.getInt("status_id"));
            orderDetail.setFine(rs.getInt("fine"));

            OrderManager.addOrderDetail(orderDetail);
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
    /**
     * Get order id of user that has not yet returned all of the books
     * @param userId
     * @return
     * @throws SQLException
     */
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
    public static Boolean cancelOrder(int id) throws SQLException{
        Boolean result = false;
        Connector.open();
        Connector.getCnt().setAutoCommit(false);
        PreparedStatement statement = Connector.getCnt().prepareStatement("UPDATE library_db.order SET order_status_id = 4 WHERE id = ?");
        statement.setInt(1, id);
        if(statement.executeUpdate() > 0)
            result = true;
        if(result){
            statement = Connector.getCnt().prepareStatement("UPDATE library_db.order_detail SET status_id = 4 WHERE order_id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            Connector.getCnt().commit();
        }
        statement.close();
        Connector.close();
        return result;
    }
    public static Boolean checkInOrder(int id) throws SQLException{
        Boolean result = false;
        Connector.open();
        Connector.getCnt().setAutoCommit(false);
        PreparedStatement statement = Connector.getCnt().prepareStatement("UPDATE library_db.order SET order_status_id = 2 WHERE id = ?");
        statement.setInt(1, id);
        if(statement.executeUpdate() > 0)
            result = true;
        if(result){
            statement = Connector.getCnt().prepareStatement("UPDATE library_db.order_detail SET status_id = 2 WHERE order_id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            Connector.getCnt().commit();
        }
        statement.close();
        Connector.close();
        return result;
    }
    public static Boolean checkOutOrder(int id, List<OrderDetail> list , Date expireDate) throws SQLException{
        Boolean result = true;
        int fine = 0;
        int dateDiff = 0;
        LocalDate exDate = expireDate.toLocalDate();
        if(LocalDate.now().compareTo(exDate) > 0){
            dateDiff = (int) Utils.getDaysDiff(exDate, LocalDate.now());
        }
        fine = dateDiff*5000;
        Connector.open();
        Connector.getCnt().setAutoCommit(false);
        PreparedStatement statement = Connector.getCnt().prepareStatement("UPDATE library_db.order_detail SET status_id = 3, returned_date = ?, fine = ? WHERE order_id = ? AND book_id = ?");
        for (OrderDetail orderDetail : list) {
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setInt(2, fine);
            statement.setInt(3, id);
            statement.setString(4, orderDetail.getBookId());
            statement.addBatch();
        }
        int[] i = statement.executeBatch();
        for (int j : i) {
            if(j == 0)
                result = false;
        }
        if(result){
            if(isOrderOver(id)){
                statement = Connector.getCnt().prepareStatement("UPDATE library_db.order SET order_status_id = 3 WHERE id = ?");
                statement.setInt(1, id);
                if(statement.executeUpdate() == 0)
                    result = false;
            }
        }
        if(result)
            Connector.getCnt().commit();
        statement.close();
        Connector.close();
        return result;
    }
    private static Boolean isOrderOver(int id) throws SQLException{
        Boolean result = true;
        PreparedStatement statement = Connector.getCnt().prepareStatement("SELECT * FROM library_db.order_detail WHERE status_id = 2 AND order_id = ?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        while(rs.isBeforeFirst()){
            result = false;
            break;
        }
        statement.close();
        return result;
    }
}
