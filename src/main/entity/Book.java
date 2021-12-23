package main.entity;

public class Book {
    private String id;
    private String title;
    private String authors;
    private String description;
    private int year;
    private String publisher;
    private int categoryId;
    private String place;
    //constructor
    public Book() {
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
    public Book(String id, String title, String authors, String description, int year, String publisher, int categoryId) {
        this.id = id;
        this.title = title;
        this.authors = authors;
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
    public String getAuthors() {
        if (this.authors == null)
            return "";
        return authors;
    }
    public void setAuthors(String authors) {
        this.authors = authors;
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

    //method
    @Override
    public String toString() {
        String result; 
        result = String.format("%s \n%s \n%s \n%s \n%d \n%s \n\n", this.id, this.title, this.authors, this.description, this.year, this.publisher);
        return result;
    }
}
