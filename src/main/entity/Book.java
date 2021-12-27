package main.entity;

import java.io.InputStream;

import javafx.scene.image.Image;
import main.utility.MyImage;

public class Book {
    private String id;
    private String title;
    private String author;
    private String description;
    private int year;
    private String publisher;
    private int categoryId;
    private String place;
    private InputStream imageBinary;
    private Image image; //Only used at app
    //constructor
    public Book() {
    }
    public Book(String id, String title, String author, String description, int year, String publisher, int categoryId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.year = year;
        this.publisher = publisher;
        this.categoryId = categoryId;
    }
    //getter & setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        if (this.author == null)
            return "";
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getDescription() {
        if(this.description == null)
            return "";
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getPublisher() {
        if(this.publisher == null)
            return "";
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public InputStream getImageBinary() {
        return imageBinary;
    }
    public void setImageBinary(InputStream imageBinary) {
        this.imageBinary = imageBinary;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public Image getImage(){
        if(image == null){
            if(imageBinary == null)
                return MyImage.placeHolder;
            this.image = MyImage.toImage(this.imageBinary);
        }
        return image;
    }
    //method
    @Override
    public String toString() {
        String result; 
        result = String.format("%s \n%s \n%s \n%s \n%d \n%s \n\n", this.id, this.title, this.author, this.description, this.year, this.publisher);
        return result;
    }
}
