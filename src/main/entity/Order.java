package main.entity;

import java.sql.Date;
import java.util.List;

public class Order {
    private int id;
    private String userId;
    private Date orderDate;
    private Date expireDate;
    private int statusId;
    private double fine;
    private List<OrderDetail> list;
    public Order(){
    }
    public List<OrderDetail> getList() {
        return list;
    }
    public void setList(List<OrderDetail> list) {
        this.list = list;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public int getStatusId() {
        return statusId;
    }
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
    public double getFine() {
        return fine;
    }
    public void setFine(double fine) {
        this.fine = fine;
    }
}
