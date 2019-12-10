package com.example.a300cemandroid;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private Bitmap img;
    private Integer ID;

    public User(){

    }


    /**
     * Returns both first name and last name as a single string
     * E.g. John Doe
     * @return firstname & lastname in a single string
     */
    public String getFullName(){
        return firstName + " " + lastName;
    }

    /**
     * Returns first name
     * e.g. John
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name
     * @param firstName - name to change first name to
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns last name
     * e.g. Doe
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Sets the last name
     * @param lastName - name to change last name to
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns email
     * @return email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email
     * @param email - Email to change to
     */
    public void setEmail(String email) {
        this.email = email;
    }


/*
    public void setImageURL(String imageURL) {
        URL u = null;
        try {
            u = new URL(imageURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.imageURL = u;
    }
*/

    /**
     * Returns ID of user
     * @return ID of user
     */
    public Integer getID() {
        return ID;
    }

    /**
     * Sets the ID of the user
     * @param ID - ID to change to
     */
    public void setID(Integer ID) {
        this.ID = ID;
    }

    /**
     * Gets the image as a bitmap
     * @return Bitmap image of user
     */
    public Bitmap getImg() {
        return img;
    }

    /**
     * Sets image bitmap
     * @param img - Bitmap of the image to set
     */
    public void setImg(Bitmap img) {
        this.img = img;
    }
}
