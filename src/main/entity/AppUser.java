package main.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AppUser {
    private String id;
    private String firstName;
    private String lastName;
    private String sex;
    private Date dateOfBirth;
    private String occupation;
    private List<Book> order;
    private Boolean isBorrowing;
    private String email;
    private String phoneNumber;
    private Date libCardVaildUpTo;
    private Boolean canOrder;
    private List<UserOrder> orderHistory;
    public AppUser(){
        order = new ArrayList<>();
        orderHistory = new ArrayList<>();
    }
    public List<UserOrder> getOrderHistory() {
        return orderHistory;
    }
    public void setOrderHistory(List<UserOrder> orderHistory) {
        this.orderHistory = orderHistory;
    }
    public Boolean getCanOrder() {
        return canOrder;
    }
    public void setCanOrder(Boolean canOrder) {
        this.canOrder = canOrder;
    }
    public Date getLibCardVaildUpTo() {
        return libCardVaildUpTo;
    }
    public void setLibCardVaildUpTo(Date libCardVaildUpTo) {
        this.libCardVaildUpTo = libCardVaildUpTo;
    }
    public Boolean getIsBorrowing() {
        return isBorrowing;
    }
    public void setIsBorrowing(Boolean isBorrowing) {
        this.isBorrowing = isBorrowing;
    }
    public String getEmail() {
        if(email == null)
            return "";
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        if(phoneNumber == null)
            return "";
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public List<Book> getOrder() {
        return order;
    }
    public void setOrder(List<Book> order) {
        this.order = order;
    }
    // getter setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getOccupation() {
        return occupation;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    //method
    public void addOrder(Book book){
        this.order.add(book);
    }
    public void removeOrder(Book book){
        this.order.remove(book);
    }
    public void addOrderHistory(UserOrder order){
        this.orderHistory.add(order);
    }
}
