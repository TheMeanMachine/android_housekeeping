package com.example.a300cemandroid.ui.tasks;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.a300cemandroid.taskObj;
import com.example.a300cemandroid.ui.houses.housesViewModel;

import java.util.ArrayList;

public class tasksViewModel extends ViewModel {


    private MutableLiveData<ArrayList<taskObj>> tasks = new MutableLiveData<>();

    public tasksViewModel() {
        tasks.setValue(new ArrayList<taskObj>());
    }

    private static tasksViewModel instance = null;


    //Singleton pattern applied
    public static tasksViewModel getInstance(){
        if(instance == null){
            instance = new tasksViewModel();
        }
        return instance;
    }

    /**
     * Resets instance of class for singleton
     */
    public void reset(){
        this.instance = new tasksViewModel();
    }


    /**
     * Returns Tasklist
     * @return MutableLiveData of taskObj obj
     */
    public MutableLiveData<ArrayList<taskObj>> getTasks() {

        return tasks;
    }


    /**
     * Sets task list
     * @param tasks ArrayList of taskObj
     */
    public void setTasks(ArrayList<taskObj> tasks) {
        this.tasks.setValue(tasks);

    }

}