package main.entity;

import java.util.Date;

public class AppUser {
    private String id;
    private String firstName;
    private String lastName;
    private String sex;
    private Date dateOfBirth;
    private String occupation;

    private static AppUser user_instance = null;
    private AppUser(){
        //Do nothing for now
    }
    public static AppUser getInstance()
    {
        if (user_instance == null)
            user_instance = new AppUser();
        return user_instance;
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
}
