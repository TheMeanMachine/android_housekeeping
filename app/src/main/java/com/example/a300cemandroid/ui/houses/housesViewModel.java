package com.example.a300cemandroid.ui.houses;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class housesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public housesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is houses fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}