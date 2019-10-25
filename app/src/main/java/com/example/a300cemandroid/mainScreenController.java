package com.example.a300cemandroid;

import android.content.Intent;
import android.view.View;

import com.example.a300cemandroid.ui.houses.housesViewModel;

import java.util.ArrayList;

public class mainScreenController {
    private static mainScreenController instance;
    private static AppController app = AppController.getInstance();
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
        housesVM.setHeadOfHouseImg(headOfHouse.getImageURL());
        housesVM.setHeadOfHouseName(headOfHouse.getFullName());
        housesVM.setTotalTasks(newHouse.countTasks());
        housesVM.setTasksCompleted(newHouse.countCompletedTasks());
        housesVM.setUsers(newHouse.getMembers());
        housesVM.setLongitude(newHouse.getLongitude());
        housesVM.setLatitude(newHouse.getLatitude());

    }

    public void addNewHouse(){

        //housesVM.addHouse();

    }

    public void deleteHouse(House house){
        //Delete code
    }

    public void selectHouse(House house){
        housesVM.setSelectedHouse(house);

    }


}
