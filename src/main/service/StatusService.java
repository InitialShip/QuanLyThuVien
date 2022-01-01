package main.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.entity.Status;
import main.manager.StatusManager;
import main.mySqlConnector.Connector;

public class StatusService {
    public static void getData() throws SQLException{
        StatusManager.getInstance();

        Connector.open();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM library_db.status");
        while(rs.next()){
            Status status = new Status();
            status.setId(rs.getInt("id"));
            status.setStatusName(rs.getString("name"));
            StatusManager.addStatus(status);
        }
        statement.close();
        Connector.close();
    }
}
