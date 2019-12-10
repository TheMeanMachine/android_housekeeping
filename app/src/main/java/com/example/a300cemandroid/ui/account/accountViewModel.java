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

    public void reset(){
        this.instance = new accountViewModel();
    }

    //Singleton pattern applied
    public static accountViewModel getInstance(){
        if(instance == null){
            instance = new accountViewModel();
        }
        return instance;
    }

    public void setCurrentUser(User u) {


        if(u != null){
            currentUser = u;

            setFirstName(u.getFirstName());
            setLastName(u.getLastName());
            setEmail(u.getEmail());
            setUsrImg(u.getImg());
        }





    }



    public User getCurrentUser() {
        return currentUser;
    }



    public MutableLiveData<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.setValue(firstName);
    }

    public MutableLiveData<String> getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.setValue(lastName);
    }



    public MutableLiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public MutableLiveData<Bitmap> getUsrImg() {
        return usrImg;
    }

    public void setUsrImg(Bitmap usrImg) {
        this.usrImg.setValue(usrImg);
        currentUser.setImg(usrImg);

        appVM.updateUser(currentUser);
    }

}