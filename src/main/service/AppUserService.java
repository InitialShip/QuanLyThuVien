package main.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.entity.AppUser;
import main.manager.AppUserManager;
import main.mySqlConnector.Connector;

public class AppUserService {
    public static void getData(String id) throws SQLException{
        AppUserManager.getInstance();

        Connector.open();
        PreparedStatement statement = Connector.getCnt().prepareStatement("SELECT * FROM school_data WHERE id = ?");
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            AppUser user = new AppUser(rs.getString("id"), 
                rs.getString("first_name"), 
                rs.getString("last_name"), 
                rs.getString("sex"), 
                rs.getDate("date_of_birth"), 
                rs.getString("ocupation"));
            AppUserManager.addUser(user);
        }
        statement.close();
        Connector.close();
    }
}
