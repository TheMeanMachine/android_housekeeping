package com.example.a300cemandroid;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.example.a300cemandroid.ui.account.accountViewModel;

import java.util.ArrayList;

public class appViewModel{
    private static appViewModel instance = null;

    private MutableLiveData<ArrayList<User>> allUsers = new MutableLiveData<>();

    public void reset(){
        this.instance = new appViewModel();
    }


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

    public void retrieveAllUsers(){

    }

//    public House getHouseByID(Integer ID){
//
//        House h = null;
//        if(allHouses.getValue() == null){
//            return null;
//        }
//        for(int i = 0; i < allHouses.getValue().size(); i++){
//            if(allHouses.getValue().get(i).getID() == ID){
//                h = allHouses.getValue().get(i);
//                break;
//            }
//        }
//        return h;
//    }

    public User getUserByID(Integer ID){

        User u = null;
        if(allUsers.getValue() == null){
            return null;
        }
        for(int i = 0; i < allUsers.getValue().size(); i++){
            if(allUsers.getValue().get(i).getID() == ID){
                u = allUsers.getValue().get(i);
                break;
            }
        }
        return u;
    }
    private Integer getUserIndexByID(Integer ID){

        Integer index = null;
        if(allUsers.getValue() == null){
            return null;
        }
        for(int i = 0; i < allUsers.getValue().size(); i++){
            if(allUsers.getValue().get(i).getID() == ID){
                index = i;
                break;
            }
        }
        return index;
    }




    public void updateUser(User user){
        Integer userIndex = getUserIndexByID(user.getID());
        if(userIndex == null){
            return;
        }
        allUsers.getValue().set(userIndex, user);

        //do db stuff
    }

//    public void addHouse(House h){
//        DatabaseHandler db = new DatabaseHandler(getContext());
//        db.addHouse(h);
//        allHouses.getValue().add(h);
//
//    }

//    public MutableLiveData<User> getCurrentUser() {
//        return currentUser;
//    }

//    public void setCurrentUser(MutableLiveData<User> currentUser) {
//        this.currentUser = currentUser;
//    }
//    public void setCurrentUserValue(User currentUser) {
//        this.currentUser.setValue(currentUser);
//    }
//
//
//    public User getCurrentUserValue(){
//        return this.currentUser.getValue();
//    }
}
