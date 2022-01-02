package main.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private String userId;
    private Date orderDate;
    private Date expireDate;
    private int orderStatusId;
    private List<OrderDetail> listOrder;

    public Order(){
        listOrder = new ArrayList<>();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public List<OrderDetail> getListOrder() {
        return listOrder;
    }

    public void setListOrder(List<OrderDetail> listOrder) {
        this.listOrder = listOrder;
    }
    //Methods
    public void addOrderDetail(OrderDetail newOrderDetail){
        listOrder.add(newOrderDetail);
    }
}
