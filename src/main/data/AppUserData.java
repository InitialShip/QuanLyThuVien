package main.data;

import java.sql.SQLException;

import main.entity.AppUser;
import main.service.AppUserDataService;

public class AppUserData {
    private static AppUser user = null;
    
    private static AppUserData instance = null;
    private AppUserData(){
        user = new AppUser();
    }
    public static AppUserData getInstance(){
        if (instance == null){
            instance = new AppUserData();
        }
        return instance;
    }
    //getter
    public AppUser getUser(){
        return user;
    }
    public static void addUser(AppUser user){
        AppUserData.user = user;
    }
    public void loadData(String id) throws SQLException{
        AppUserDataService.getData(id);
    }
    public void reset(){
        user = null;
        instance = null;
    }
}
