package main.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.entity.Category;
import main.service.CategoryService;

public class CategoryManager {
    private static List<Category> categories = null;

    private static CategoryManager instance = null;
    private CategoryManager(){
        categories = new ArrayList<>();
    }
    public static CategoryManager getInstance(){
        if (instance == null){
            instance = new CategoryManager();
        }
        return instance;
    }

    //getter
    public static List<Category> getCategories(){
        return categories;
    }
    public static String getCategoryName(int id){
        return categories.stream().filter(c -> c.getId() == id).findFirst().orElse(null).getName();
    }
    public static Category getCategory(int id){
        return categories.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }
    //methods
    public static void loadData() throws SQLException{
        CategoryService.getData();
    }
    public void addCategory(Category category){
        categories.add(category);
    }
}
