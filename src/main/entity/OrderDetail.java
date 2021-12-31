package main.entity;

import java.sql.Date;

public class OrderDetail {
    private int id;
    private String bookId;
    private Date returnedDate;
    private int statusId;
    public OrderDetail(){
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    public Date getReturnedDate() {
        return returnedDate;
    }
    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }
    public int getStatusId() {
        return statusId;
    }
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
