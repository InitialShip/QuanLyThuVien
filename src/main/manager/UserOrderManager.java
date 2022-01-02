package main.manager;

import java.util.ArrayList;
import java.util.List;

import main.entity.UserOrder;

public class UserOrderManager {
    private static List<UserOrder> orderList = null;

    private static UserOrderManager instance = null;
    private UserOrderManager(){
        orderList = new ArrayList<>();
    }
    public static UserOrderManager getInstance(){
        if (instance == null){
            instance = new UserOrderManager();
        }
        return instance;
    }

    public static List<UserOrder> getOrders(){
        return orderList;
    }
    public static void addOrder(UserOrder newOrder){
        orderList.add(newOrder);
    }
}
