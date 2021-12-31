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
            AppUser user = new AppUser();
            user.setId(rs.getString("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setSex(rs.getString("sex"));
            user.setDateOfBirth(rs.getDate("date_of_birth"));
            user.setOccupation(rs.getString("ocupation"));
            AppUserManager.setUser(user);
        }
        statement.close();
        Connector.close();
    }
}
