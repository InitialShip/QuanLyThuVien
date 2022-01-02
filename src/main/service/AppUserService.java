package main.service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import main.entity.AppUser;
import main.entity.UserOrder;
import main.manager.AppUserManager;
import main.manager.BookManager;
import main.mySqlConnector.Connector;

public class AppUserService {
    public static void getData(String id) throws SQLException{
        AppUserManager.getInstance();

        Connector.open();
        PreparedStatement statement = Connector.getCnt().prepareStatement("SELECT * FROM library_db.school_data, library_db.id_card WHERE id = user_id AND id = ?");
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            AppUser user = new AppUser();
            user.setId(rs.getString("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setSex(rs.getString("sex"));
            user.setDateOfBirth(rs.getDate("date_of_birth"));
            user.setOccupation(rs.getString("ocupation"));
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setLibCardVaildUpTo(rs.getDate("expire_date"));
            AppUserManager.setUser(user);
        }
        statement.close();
        Connector.close();
    }
    public static Boolean canOrder(String id) throws SQLException{
        Boolean result = false;
        if(OrderService.getLatestOrderId(id) < 0)
            result = true;
        return result;
    }
    //yeet
    public static Boolean isIdCardValid(String id) throws SQLException{
        Boolean result = false;
        Connector.open();
        PreparedStatement statement = Connector.getCnt().prepareStatement("SELECT expire_date FROM library_db.id_card WHERE user_id = ?");
        ResultSet rs = statement.executeQuery();
        if(rs.isBeforeFirst()){
            Date retrivedDate = rs.getDate("expire_date");
            if(LocalDate.now().compareTo(retrivedDate.toLocalDate())<=0)
                result = true;
        }
        Connector.close();
        return result;
    }
    public static void getRecentOrder() throws SQLException{
        Connector.open();
        PreparedStatement statement = Connector.getCnt().prepareStatement("SELECT * FROM library_db.order_detail WHERE order_id = ?");
        statement.setInt(1, OrderService.getLatestOrderId(AppUserManager.getUser().getId()));
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            AppUserManager.getUser().getOrder().add(BookManager.getBook(rs.getString("book_id")));
        }
        statement.close();
        Connector.close();
    }
    public static void getUserOrderDetail(String id) throws SQLException{
        Connector.open();
        String sql = "SELECT * FROM library_db.order o, library_db.order_detail d WHERE o.id = d.order_id AND o.order_status_id NOT IN (1,2) AND user_id = ?";
        PreparedStatement statement = Connector.getCnt().prepareStatement(sql);
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            UserOrder order = new UserOrder();
            order.setId(rs.getInt("id"));
            order.setBookId(rs.getString("book_id"));
            order.setOrderDate(rs.getDate("order_date"));
            order.setExpireDate(rs.getDate("expire_date"));
            order.setReturnedDate(rs.getDate("returned_date"));
            order.setStatusId(rs.getInt("status_id"));
            order.setFine(rs.getInt("fine"));
            AppUserManager.getUser().addOrderHistory(order);
        }     
        Connector.close();
    }
    public static Boolean UpdateIdCard(String email, String phoneN,String id) throws SQLException{
        Boolean result = false;
        Connector.open();
        PreparedStatement statement = Connector.getCnt().prepareStatement("UPDATE library_db.id_card SET email = ?,phone_number = ? WHERE user_id = ?;");
        statement.setString(1, email);
        statement.setString(2, phoneN);
        statement.setString(3, id);
        if(statement.executeUpdate() > 0)
            result = true;
        statement.close();
        Connector.close();
        return result;
    }
    
}
