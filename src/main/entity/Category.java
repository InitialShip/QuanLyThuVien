package main.entity;

public class Category {
    private int id;
    private String name;
    
    //contructors
    public Category() {
    }
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
    //getter setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    //methods
    @Override
    public String toString() {
        return this.name;
    }
}
