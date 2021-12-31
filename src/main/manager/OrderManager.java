package main.manager;

import java.util.ArrayList;
import java.util.List;

import main.entity.Order;

public class OrderManager {
    private static List<Order> orderList = null;

    private static OrderManager instance = null;
    private OrderManager(){
        orderList = new ArrayList<>();
    }
    public static OrderManager getInstance(){
        if (instance == null){
            instance = new OrderManager();
        }
        return instance;
    }

    public static List<Order> getOrders(){
        return orderList;
    }
    public static void addOrder(Order newOrder){
        orderList.add(newOrder);
    }
}
