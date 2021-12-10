package main.entity;

public class Book {
    private String id;
    private String name;
    private String authors;
    private String description;
    private int year;
    private String publisher;
    //constructor
    public Book() {
    }
    public Book(String id, String name, String authors, String description, int year, String publisher) {
        this.id = id;
        this.name = name;
        this.authors = authors;
        this.description = description;
        this.year = year;
        this.publisher = publisher;
    }
    //getter & setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthors() {
        return authors;
    }
    public void setAuthors(String authors) {
        this.authors = authors;
    }
    public String getDescription() {
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
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    //method
    @Override
    public String toString() {
        String result; 
        result = String.format("%s \n%s \n%s \n%s \n%d \n%s \n\n", this.id, this.name, this.authors, this.description, this.year, this.publisher);
        return result;
    }
}
