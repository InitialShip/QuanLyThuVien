package main.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.entity.Category;
import main.service.CategoryDataService;

public class CategoryData {
    private static List<Category> categories = null;

    private static CategoryData instance = null;
    private CategoryData(){
        categories = new ArrayList<>();
    }
    public static CategoryData getInstance(){
        if (instance == null){
            instance = new CategoryData();
        }
        return instance;
    }

    //getter
    public static List<Category> getCategories(){
        return categories;
    }
    public static String getCategory(int id){
        return categories.stream().filter(c -> c.getId() == id).findFirst().orElse(null).getName();
    }
    //methods
    public static void loadData() throws SQLException{
        CategoryDataService.getData();
    }
    public void addCategory(Category category){
        categories.add(category);
    }
}
