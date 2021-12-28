package main.manager;

import java.sql.SQLException;

import main.entity.AppUser;
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
    //getter
    public AppUser getUser(){
        return user;
    }
    public static void addUser(AppUser user){
        AppUserManager.user = user;
    }
    public void loadData(String id) throws SQLException{
        AppUserService.getData(id);
    }
    public void reset(){
        user = null;
        instance = null;
    }
}
