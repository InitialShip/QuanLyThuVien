package test;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import main.manager.StatusManager;
import main.mySqlConnector.Connector;

public class StatusTest {
    StatusManager categoryManager = StatusManager.getInstance();
    @Test
    public void testLoadData() throws SQLException{
        StatusManager.loadData();
        Connector.open();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM library_db.status");
        while(rs.next()){
            assertEquals(StatusManager.getStatusName(rs.getInt(1)), rs.getString(2));
        }
        statement.close();
        Connector.close();
    }
}
