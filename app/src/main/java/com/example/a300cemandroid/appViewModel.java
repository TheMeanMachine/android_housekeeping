package com.example.a300cemandroid;

import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class appViewModel {
    private static appViewModel instance = null;

    private MutableLiveData<ArrayList<User>> allUsers = new MutableLiveData<>();
    private MutableLiveData<ArrayList<House>> allHouses = new MutableLiveData<>();





    //Singleton pattern applied
    public static appViewModel getInstance(){
        if(instance == null){
            instance = new appViewModel();
        }
        return instance;
    }

    private appViewModel(){
        allHouses.setValue(new ArrayList<House>());
        allUsers.setValue(new ArrayList<User>());

        User u = new User();
        u.setFirstName("Peepe");
        u.setLastName("Deepe");
        u.setID(1);
        allUsers.getValue().add(u);

        u = new User();
        u.setFirstName("Reepio");
        u.setLastName("Lopio");
        u.setID(2);
        allUsers.getValue().add(u);

        u = new User();
        u.setFirstName("Lopindo");
        u.setLastName("Pindiantio");
        u.setID(3);
        allUsers.getValue().add(u);
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

    public House getHouseByID(Integer ID){

        House h = null;
        if(allHouses.getValue() == null){
            return null;
        }
        for(int i = 0; i < allHouses.getValue().size(); i++){
            if(allHouses.getValue().get(i).getID() == ID){
                h = allHouses.getValue().get(i);
                break;
            }
        }
        return h;
    }

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

    public void addHouse(House h){
        allHouses.getValue().add(h);
    }

}
