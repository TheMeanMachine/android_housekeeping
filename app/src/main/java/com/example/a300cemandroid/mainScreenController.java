package com.example.a300cemandroid;

import com.example.a300cemandroid.ui.account.accountViewModel;
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

    public void reset(){
        this.instance = new mainScreenController();
    }


    public void clearFields_Houses(){
        housesVM.clearData();
    }

    public void newHouseSelected(House newHouse){

    }

    public void addNewHouse(){

        //housesVM.addHouse();

    }

    public void deleteHouse(House house){
        //Delete code
        ArrayList<House> result = housesVM.getHouses().getValue();

        result.remove(housesVM.getSelectedPosition());

        housesVM.setHouses(result);

        housesVM.updateFields();
        //Todo db
    }

    public void selectHouse(House house){
        housesVM.setSelectedHouseRaw(house);

    }


    public void resetInstances(){
        housesVM.reset();
        appViewModel.getInstance().reset();
        accountViewModel.getInstance().reset();

    }
}
