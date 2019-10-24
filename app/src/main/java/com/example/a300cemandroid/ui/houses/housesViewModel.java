package com.example.a300cemandroid.ui.houses;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.a300cemandroid.House;
import com.example.a300cemandroid.User;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class housesViewModel extends ViewModel {
    private static housesViewModel instance = null;

    private MutableLiveData<ArrayList<House>> houses = new MutableLiveData<>();
    private MutableLiveData<ArrayList<User>> users = new MutableLiveData<>();

    private MutableLiveData<Integer> totalTasks = new MutableLiveData<>();
    private MutableLiveData<Integer> tasksRemaining = new MutableLiveData<>();
    private MutableLiveData<String> headOfHouseName = new MutableLiveData<>();

    public housesViewModel(){
        ArrayList<House> h = new ArrayList<House>();
        houses.setValue(h);

        ArrayList<User> u = new ArrayList<User>();
        users.setValue(u);
    }
    //Singleton pattern applied
    public static housesViewModel getInstance(){
        if(instance == null){
            instance = new housesViewModel();
        }
        return instance;
    }

    public LiveData<String> getHeadOfHouseName(){
        return headOfHouseName;
    }

    public void setHeadOfHouseName(String name){
        headOfHouseName.setValue(name);
    }

    public MutableLiveData<ArrayList<House>> getHouses() {
        return houses;
    }

    public void addHouse(House house){
        ArrayList<House> h = houses.getValue();
        h.add(house);

        houses.setValue(h);

    }

    public MutableLiveData<ArrayList<User>> getUsers(){
        return users;
    }

    public void addUser(User user){
        ArrayList<User> u = users.getValue();
        u.add(user);

        users.setValue(u);
    }

    public MutableLiveData<Integer> getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(Integer no_tasks){
        totalTasks.setValue(no_tasks);
    }

    public MutableLiveData<Integer> getTasksRemaining(){
        return tasksRemaining;
    }

    public void setTasksRemaining(Integer tasks){
        tasksRemaining.setValue(tasks);
    }





}