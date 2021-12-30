package main.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppUser {
    private String id;
    private String firstName;
    private String lastName;
    private String sex;
    private Date dateOfBirth;
    private String occupation;
    private List<Book> order;

    //TODO I should set every thing at default
    public AppUser(){
        order = new ArrayList<>();
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
}
