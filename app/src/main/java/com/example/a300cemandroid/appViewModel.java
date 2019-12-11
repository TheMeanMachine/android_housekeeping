package com.example.a300cemandroid;


import android.arch.lifecycle.MutableLiveData;
import java.util.ArrayList;

public class appViewModel{
    private static appViewModel instance = null;

    private MutableLiveData<ArrayList<User>> allUsers = new MutableLiveData<>();

    //Singleton pattern applied
    public static appViewModel getInstance(){
        if(instance == null){
            instance = new appViewModel();
        }
        return instance;
    }

    /**
     * Resets the instance
     */
    public void reset(){
        this.instance = new appViewModel();
    }


    /**
     * Returns the user list containing all users
     * @return MutableLiveData<ArrayList<User>>
     */
    public MutableLiveData<ArrayList<User>> getAllUsers() {
        return allUsers;
    }

    /**
     * Replaces the user list
     * @param allUsers - Mutable Live Data containing ArrayList of User
     */
    public void setAllUsers(MutableLiveData<ArrayList<User>> allUsers) {
        this.allUsers = allUsers;
    }

    /**
     * Returns the user with ID matching
     * @param ID - DB ID of the user to find and return
     * @return User Obj
     */
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

    /**
     * Searches through user list to find user with particular ID
     * @param ID - ID of user to find
     * @return Index of user in list
     */
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

    /**
     * Updates the user to syncronise data across the application
     * Finds the user by their DB ID and updates the user
     * @param user - user with data to update
     */
    public void updateUser(User user){
        Integer userIndex = getUserIndexByID(user.getID());
        if(userIndex == null){
            return;
        }
        allUsers.getValue().set(userIndex, user);
    }

}
