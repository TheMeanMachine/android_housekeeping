package com.example.a300cemandroid;

import java.util.List;

public class House {
    private String houseName;
    private List<Task> tasks;


    public House() {
    }

    public void setHouseName(String name){
        houseName = name;
    }

    public String getHouseName(){
        return houseName;
    }

}
