package com.example.a300cemandroid;

import java.net.MalformedURLException;
import java.net.URL;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private URL imageURL;
    private Integer ID;

    public User(){
        try {
            imageURL = new URL("");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getFullName(){
        return firstName + " " + lastName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public URL getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        URL u = null;
        try {
            u = new URL(imageURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.imageURL = u;
    }


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}
