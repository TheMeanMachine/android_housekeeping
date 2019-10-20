package com.example.a300cemandroid.ui.tasks;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class tasksViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public tasksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tasks fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}