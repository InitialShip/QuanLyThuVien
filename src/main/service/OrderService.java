package main.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import main.mySqlConnector.Connector;

public class OrderService {
    public static Boolean submitOrder() throws SQLException{
        Boolean result = false;
        Connector.open();
        Connector.getCnt().setAutoCommit(false);
        
        PreparedStatement statement = Connector.getCnt().prepareStatement("INSERT INTO order (user_id,order_date,expire_date,status_id,fine) VALUES (?,?,?,?,?)");
        

        Connector.getCnt().commit();
        Connector.close();
        return result;
    }
}
