package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import main.manager.CategoryManager;
import main.mySqlConnector.Connector;

public class CategoryTest {
    CategoryManager categoryManager = CategoryManager.getInstance();
    @Test
    public void testLoadData() throws SQLException{
        CategoryManager.loadData();
        Connector.open();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM library_db.categories");
        while(rs.next()){
            assertEquals(CategoryManager.getCategoryName(rs.getInt(1)), rs.getString(2));
        }
        statement.close();
        Connector.close();
    }
}
