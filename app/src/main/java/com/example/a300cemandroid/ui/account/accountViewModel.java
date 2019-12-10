package com.example.a300cemandroid.ui.account;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.example.a300cemandroid.User;
import com.example.a300cemandroid.appViewModel;

public class accountViewModel extends ViewModel {
    private static accountViewModel instance = null;

    private User currentUser;


    private MutableLiveData<String> firstName = new MutableLiveData<>();
    private MutableLiveData<String> lastName = new MutableLiveData<>();

    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<Bitmap> usrImg = new MutableLiveData<>();

    private appViewModel appVM = appViewModel.getInstance();



    //Singleton pattern applied
    public static accountViewModel getInstance(){
        if(instance == null){
            instance = new accountViewModel();
        }
        return instance;
    }

    /**
     * Resets the instance (Singleton)
     */
    public void reset(){
        this.instance = new accountViewModel();
    }

    /**
     * Sets the current user and updates all fields accordingly
     * @param u - user to update by
     */
    public void setCurrentUser(User u) {
        if(u != null){//User is not null
            currentUser = u;

            setFirstName(u.getFirstName());
            setLastName(u.getLastName());
            setEmail(u.getEmail());
            setUsrImg(u.getImg());
        }

    }

    /**
     * Gets the currently logged in user
     * @return User Obj
     */
    public User getCurrentUser() {
        return currentUser;
    }


    /**
     * Returns first name of user
     * @return MutableLiveData<String> containing first name
     */
    public MutableLiveData<String> getFirstName() {
        return firstName;
    }

    /**
     * Sets first name
     * @param firstName - the name to set the first name
     */
    public void setFirstName(String firstName) {
        this.firstName.setValue(firstName);
    }

    /**
     * Returns last name of user
     * @return MutableLiveData<String> containing last name
     */
    public MutableLiveData<String> getLastName() {
        return lastName;
    }

    /**
     * Sets last name
     * @param lastName - the name to set the last name
     */
    public void setLastName(String lastName) {
        this.lastName.setValue(lastName);
    }


    /**
     * Returns the email
     * @return MutableLiveData<String> containing email
     */
    public MutableLiveData<String> getEmail() {
        return email;
    }

    /**
     * Sets the email
     * @param email - email to change to
     */
    public void setEmail(String email) {
        this.email.setValue(email);
    }

    /**
     * Returns the user's image
     * @return MutableLiveData<Bitmap> containing user image
     */
    public MutableLiveData<Bitmap> getUsrImg() {
        return usrImg;
    }

    /**
     * Set user image
     * @param usrImg - Bitmap to change to
     */
    public void setUsrImg(Bitmap usrImg) {
        this.usrImg.setValue(usrImg);
        currentUser.setImg(usrImg);

        appVM.updateUser(currentUser);
    }

}