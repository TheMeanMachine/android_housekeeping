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



    public accountViewModel(){


        User u = new User();
        u.setFirstName("Reepio");
        u.setLastName("Lopio");
        u.setID(2);


        currentUser =u;

        getInformation();
    }

    private void getInformation() {
        if(true){

            firstName.setValue(currentUser.getFirstName());
        }


    }

    //Singleton pattern applied
    public static accountViewModel getInstance(){
        if(instance == null){
            instance = new accountViewModel();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }



    public MutableLiveData<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(MutableLiveData<String> firstName) {
        this.firstName = firstName;
    }

    public MutableLiveData<String> getLastName() {
        return lastName;
    }

    public void setLastName(MutableLiveData<String> lastName) {
        this.lastName = lastName;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public void setEmail(MutableLiveData<String> email) {
        this.email = email;
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