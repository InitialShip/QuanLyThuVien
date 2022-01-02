package main.manager;

import java.sql.SQLException;

import main.entity.AppUser;
import main.entity.Book;
import main.service.AppUserService;
import main.service.OrderService;

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
    public static AppUser getUser(){
        return user;
    }
    public static void setId(String id){
        user.setId(id);
    }
    public static void setUser(AppUser nuser){
        user = nuser;
    }
    public static String getVaildUpToDate(){
        if(user.getLibCardVaildUpTo() == null)
            return "";
        return user.getLibCardVaildUpTo().toString();
    }
    public static String getDateOfBirth(){
        if(user.getDateOfBirth() == null)
            return "";
        return user.getDateOfBirth().toString();
    }
    //Methods
    public static void order(Book book){
        if(!user.getOrder().contains(book) && user.getOrder().size() < 5)
            user.addOrder(book);
        else
            user.removeOrder(book);
    }
    public static void clearOrder(){
        user.getOrder().clear();
    }
    public static void submitOrder() throws SQLException{
        if(AppUserManager.getUser().getCanOrder() && !AppUserManager.getUser().getOrder().isEmpty()){
            OrderService.createOrder(user.getId());
            OrderService.submitOrder(OrderService.getLatestOrderId(user.getId()), user.getOrder());
        }
    }
    public static void loadData(String id) throws SQLException{
        AppUserService.getData(id);
        AppUserManager.getUser().setCanOrder(AppUserService.canOrder(id)&&AppUserService.isIdCardValid(id));
    }
    public static void reset(){
        user = new AppUser();
    }
    public static void getRecentOrder() throws SQLException{
        AppUserService.getRecentOrder();
    }
    public static void getOrderHistory() throws SQLException{
        AppUserService.getUserOrderDetail(user.getId());
    }
    public static Boolean updateIdCard(String email, String phoneNumber) throws SQLException{
        return AppUserService.UpdateIdCard(email, phoneNumber, user.getId());
    }
}
