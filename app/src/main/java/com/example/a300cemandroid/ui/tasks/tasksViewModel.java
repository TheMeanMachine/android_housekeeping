package com.example.a300cemandroid.ui.tasks;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.a300cemandroid.taskObj;

import java.util.ArrayList;

public class tasksViewModel extends ViewModel {


    private MutableLiveData<ArrayList<taskObj>> tasks = new MutableLiveData<>();

    public tasksViewModel() {
        tasks.setValue(new ArrayList<taskObj>());
    }


    public MutableLiveData<ArrayList<taskObj>> getTasks() {

        return tasks;
    }

    public void setTasks(ArrayList<taskObj> tasks) {
        this.tasks.setValue(tasks);
    }

    public void addTask(taskObj t){
        ArrayList<taskObj> newTasks = this.tasks.getValue();
        newTasks.add(t);
        setTasks(newTasks);
    }


}