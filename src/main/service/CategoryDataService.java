package main.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.data.CategoryData;
import main.entity.Category;
import main.mySqlConnector.Connector;

public class CategoryDataService {
    public static void getData() throws SQLException{
        CategoryData categoryData = CategoryData.getInstance();

        Connector.open();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM categories");
        while(rs.next()){
            categoryData.addCategory(new Category(rs.getInt("id"), rs.getString("name"))); 
        }
        Connector.close();
    }
}
