package main.manager;

import java.sql.SQLException;

import main.entity.AppUser;
import main.entity.Book;
import main.service.AppUserService;

public class AppUserManager {
    private static AppUser user = null;
    
    private static AppUserManager instance = null;
    private AppUserManager(){
        user = new AppUser();
    }
    public static AppUserManager getInstance(){
        if (instance == null){
            instance = new AppUserManager();
        }
        return instance;
    }
    //Getter setter
    public static AppUser getUser(){
        return user;
    }
    public static void setUser(AppUser user){
        AppUserManager.user = user;
    }
    //Methods
    public static void order(Book book){
        if(user.getOrder().size() < 5)
            if(!user.getOrder().contains(book))
                user.addOrder(book);
            else
                user.removeOrder(book);
    }
    public static void loadData(String id) throws SQLException{
        AppUserService.getData(id);
    }
    public static void reset(){
        user = new AppUser();
    }
}
