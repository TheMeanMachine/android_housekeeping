package com.example.a300cemandroid.ui.account;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class accountViewModel extends ViewModel {

    private MutableLiveData<String> firstName;
    private MutableLiveData<String> lastName;
    private MutableLiveData<String> email;
    
    public accountViewModel() {

    }


}