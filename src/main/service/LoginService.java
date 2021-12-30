package main.service;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.mySqlConnector.Connector;
import main.utility.Utils;

public class LoginService {
    public static Boolean isAccountExisted(String id) throws SQLException{
        Boolean result = false;
        Connector.open();
        PreparedStatement statement = Connector.getCnt().prepareStatement("SELECT user_id FROM user_account WHERE user_id = ?");
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();

        if(rs.isBeforeFirst()){
            result = true;
        }
        statement.close();
        Connector.close();
        return result;
    }
    public static Boolean isPasswordCorrect(String id, String pass) throws SQLException, NoSuchAlgorithmException{
        Boolean result = false;
        Connector.open();
        PreparedStatement statement = Connector.getCnt().prepareStatement("SELECT user_password FROM user_account WHERE user_id = ?");
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            String retrievedPass = rs.getString("user_password");
            if(retrievedPass.equals(Utils.getEncryted(id+pass))){
                result = true;
                break;
            }
        }
        statement.close();
        Connector.close();
        return result;
    }
    public static Boolean isAdminAccountExisted(String id) throws SQLException{
        Boolean result = false;
        Connector.open();
        PreparedStatement statement = Connector.getCnt().prepareStatement("SELECT id FROM admin_account WHERE id = ?");
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();

        if(rs.isBeforeFirst()){
            result = true;
        }
        statement.close();
        Connector.close();
        return result;
    }
    public static Boolean isAdminPasswordCorrect(String id, String pass) throws SQLException, NoSuchAlgorithmException{
        Boolean result = false;
        Connector.open();
        PreparedStatement statement = Connector.getCnt().prepareStatement("SELECT password FROM admin_account WHERE id = ?");
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            String retrievedPass = rs.getString("password");
            if(retrievedPass.equals(Utils.getEncryted(id+pass))){
                result = true;
            }
        }
        statement.close();
        Connector.close();
        return result;
    }
}
