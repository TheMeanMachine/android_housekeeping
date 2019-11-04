package com.example.a300cemandroid.ui.tasks;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.a300cemandroid.Task;

import java.util.ArrayList;

public class tasksViewModel extends ViewModel {


    private MutableLiveData<ArrayList<Task>> tasks = new MutableLiveData<>();

    public tasksViewModel() {
        tasks.setValue(new ArrayList<Task>());
    }


    public MutableLiveData<ArrayList<Task>> getTasks() {

        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks.setValue(tasks);
    }

    public void addTask(Task t){
        ArrayList<Task> newTasks = this.tasks.getValue();
        newTasks.add(t);
        setTasks(newTasks);
    }


}