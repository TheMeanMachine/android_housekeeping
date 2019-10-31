package com.example.a300cemandroid;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class AppController extends ViewModel {
    private static AppController instance = null;
    private MutableLiveData<Integer> one = new MutableLiveData<>();

    private AppController(){

    }
    //Singleton pattern applied
    public static AppController getInstance(){
        if(instance == null){
            instance = new AppController();
        }
        return instance;
    }

    public User getUser(Integer id){
        User u = new User();
        u.setFirstName("Pizza");
        u.setLastName("Man");
        //u.setImageURL("https://i.stack.imgur.com/GsDIl.jpg");
        //do get user logic later
        return u;
    }


    public MutableLiveData<Integer> getOne() {
        return one;
    }

    public void setOne(MutableLiveData<Integer> one) {
        this.one = one;
    }
}
