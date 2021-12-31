package main.service;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import main.mySqlConnector.Connector;
import main.utility.Utils;

public class RegisterService {
    public static Boolean isFromSchool(String id) throws SQLException{
        Boolean result = false;
        Connector.open();
        PreparedStatement statement = Connector.getCnt().prepareStatement("SELECT * FROM school_data WHERE id = ?");
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();
        if(rs.isBeforeFirst()){
            result = true;
        } 
        statement.close();
        Connector.close();
        return result;
    }
    public static Boolean isAccountExisted(String id) throws SQLException{
        return LoginService.isAccountExisted(id);
    }
    public static Boolean register(String id, String pass) throws SQLException, NoSuchAlgorithmException{
        Boolean result = false;
        Connector.open();
        Connector.getCnt().setAutoCommit(false);
        PreparedStatement statement = Connector.getCnt().prepareStatement("INSERT INTO user_account VALUES(?,?)");
        statement.setString(1, id);
        statement.setString(2, Utils.getEncryted(id+pass));
        if(statement.executeUpdate() > 0)
            result = true;
        Connector.getCnt().commit();
        statement.close();
        Connector.close();
        return result;
    }
    public static Boolean createIdCard(String id) throws SQLException{
        Boolean result = false;
        Connector.open();
        PreparedStatement statement = Connector.getCnt().prepareStatement("INSERT INTO library_db.id_card(user_id,expire_date)VALUES(?,?)");
        statement.setString(1, id);
        statement.setDate(2, Date.valueOf(LocalDate.now().plusMonths(5)));
        if(statement.executeUpdate() > 0)
            result = true;
        statement.close();
        Connector.close();
        return result;
    }
}
