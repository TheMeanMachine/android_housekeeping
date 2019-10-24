package com.example.a300cemandroid;

import java.util.List;

public class AppController {
    private static AppController instance = null;
    private List<House> houses;
    private House selectedHouse;


    private AppController(){

    }
    //Singleton pattern applied
    public static AppController getInstance(){
        if(instance == null){
            instance = new AppController();
        }
        return instance;
    }
}
