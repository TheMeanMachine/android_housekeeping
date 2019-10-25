package com.example.a300cemandroid;

import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class appViewModel {
    private static appViewModel instance = null;

    private MutableLiveData<ArrayList<User>> allUsers = new MutableLiveData<>();


    //Singleton pattern applied
    public static appViewModel getInstance(){
        if(instance == null){
            instance = new appViewModel();
        }
        return instance;
    }

    public MutableLiveData<ArrayList<User>> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(MutableLiveData<ArrayList<User>> allUsers) {
        this.allUsers = allUsers;
    }

    public void getUser(Integer id){

    }
}
