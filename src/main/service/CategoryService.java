package main.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.entity.Category;
import main.manager.CategoryManager;
import main.mySqlConnector.Connector;

public class CategoryService {
    public static void getData() throws SQLException{
        CategoryManager categoryData = CategoryManager.getInstance();

        Connector.open();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM categories");
        while(rs.next()){
            categoryData.addCategory(new Category(rs.getInt("id"), rs.getString("name"))); 
        }
        statement.close();
        Connector.close();
    }
}
