package main.manager;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.entity.Order;
import main.entity.OrderDetail;
import main.service.OrderService;
import main.utility.Utils;

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
    // get all
    public static List<Order> getOrderList() {
        return orderList;
    }
    public static void setOrderList(List<Order> orderList) {
        OrderManager.orderList = orderList;
    }
    public static Order getOrderById(int id){
        return orderList.stream().filter(o -> o.getOrderId() == id).findFirst().orElse(null);
    }
    //Methods
    public static List<Order> getRecentOrder(){
        return orderList.stream().filter(o -> o.getOrderStatusId() == 1 || o.getOrderStatusId() == 2).toList();
    }
    public static List<Order> getOrderHistory(){
        return orderList.stream().filter(o -> o.getOrderStatusId() != 1 || o.getOrderStatusId() != 2).toList();
    }
    public static void loadData() throws SQLException{
        OrderService.getAllOrder();
        OrderService.getAllOrderDetail();
    }
    public static void addOrder(Order newOrder){
        orderList.add(newOrder);
    }
    public static void addOrderDetail(OrderDetail newOrderDetail){
        getOrderById(newOrderDetail.getOrderId()).getListOrder().add(newOrderDetail);
    }
    public static Boolean cancelAnOrder(int id) throws SQLException{
        return OrderService.cancelOrder(id);
    }
    public static Boolean checkInAnOrder(int id) throws SQLException{
        return OrderService.checkInOrder(id);
    }
    public static Boolean checkOutOrder(int id, List<OrderDetail> list, Date expireDate) throws SQLException{
        return OrderService.checkOutOrder(id, list,expireDate);
    }
    public static void reloadData() throws SQLException{
        orderList.clear();
        loadData();
    }
    //Statistic
    public static int getQTotalOrder(){
        int sum = 0;
        for (Order order : orderList) {
            if(order.getOrderStatusId() == 4 || order.getOrderStatusId() == 1)
                continue;
            if(Utils.getMonthDiff(order.getOrderDate().toLocalDate(), LocalDate.now()) > 4)
                continue;
            sum+= order.getListOrder().size();
        }
        return sum;
    }
    public static int getQReturnedOrder(){
        int sum = 0;
        for (Order order : orderList) {
            if(order.getOrderStatusId() == 4 || order.getOrderStatusId() == 1)
                continue;
            if(Utils.getMonthDiff(order.getOrderDate().toLocalDate(), LocalDate.now()) > 4)
                continue;
            sum+= order.getListOrder().stream().filter(od -> od.getStatusId() == 3).toList().size();
        }
        return sum;
    }
    public static int getQNotReturnedOrder(){
        int sum = 0;
        for (Order order : orderList) {
            if(order.getOrderStatusId() == 4 || order.getOrderStatusId() == 1)
                continue;
            if(Utils.getMonthDiff(order.getOrderDate().toLocalDate(), LocalDate.now()) > 4)
                continue;
            sum+= order.getListOrder().stream().filter(od -> od.getStatusId() == 2).toList().size();
        }
        return sum;
    }
    public static int getQTotalFine(){
        int sum = 0;
        for (Order order : orderList) {
            if(order.getOrderStatusId() == 4 || order.getOrderStatusId() == 1)
                continue;
            if(Utils.getMonthDiff(order.getOrderDate().toLocalDate(), LocalDate.now()) > 4)
                continue;
            for (OrderDetail orderDetail : order.getListOrder()) {
                if(orderDetail.getStatusId() == 3)
                    sum+= orderDetail.getFine();
            }
        }
        return sum;
    }
    public static int getYTotalOrder(){
        int sum = 0;
        for (Order order : orderList) {
            if(order.getOrderStatusId() == 4 || order.getOrderStatusId() == 1)
                continue;
            if(Utils.getYearDiff(order.getOrderDate().toLocalDate(), LocalDate.now()) > 1)
                continue;
            sum+= order.getListOrder().size();
        }
        return sum;
    }
    public static int getYReturnedOrder(){
        int sum = 0;
        for (Order order : orderList) {
            if(order.getOrderStatusId() == 4 || order.getOrderStatusId() == 1)
                continue;
            if(Utils.getYearDiff(order.getOrderDate().toLocalDate(), LocalDate.now()) > 1)
                continue;
            sum+= order.getListOrder().stream().filter(od -> od.getStatusId() == 3).toList().size();
        }
        return sum;
    }
    public static int getYNotReturnedOrder(){
        int sum = 0;
        for (Order order : orderList) {
            if(order.getOrderStatusId() == 4 || order.getOrderStatusId() == 1)
                continue;
            if(Utils.getYearDiff(order.getOrderDate().toLocalDate(), LocalDate.now()) > 1)
                continue;
            sum+= order.getListOrder().stream().filter(od -> od.getStatusId() == 2).toList().size();
        }
        return sum;
    }
    public static int getYTotalFine(){
        int sum = 0;
        for (Order order : orderList) {
            if(order.getOrderStatusId() == 4 || order.getOrderStatusId() == 1)
                continue;
            if(Utils.getYearDiff(order.getOrderDate().toLocalDate(), LocalDate.now()) > 1)
                continue;
            for (OrderDetail orderDetail : order.getListOrder()) {
                if(orderDetail.getStatusId() == 3)
                    sum+= orderDetail.getFine();
            }
        }
        return sum;
    }
}
