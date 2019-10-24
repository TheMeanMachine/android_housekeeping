package com.example.a300cemandroid;

import com.example.a300cemandroid.ui.houses.housesViewModel;

public class mainScreenController {
    private static mainScreenController instance;
    private static AppController app;
    private static housesViewModel housesVM = housesViewModel.getInstance();
    //Singleton pattern applied
    public static mainScreenController getInstance(){
        if(instance == null){
            instance = new mainScreenController();
        }
        return instance;
    }

    public void clearFields_Houses(){
        housesVM.clearData();
    }

    public void newHouseSelected(House newHouse){
        User headOfHouse = app.getUser(newHouse.getHeadOfHouseID());

        housesVM.setHeadOfHouseName(headOfHouse.);
    }


}
