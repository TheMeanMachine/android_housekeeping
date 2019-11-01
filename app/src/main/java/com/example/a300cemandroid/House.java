package com.example.a300cemandroid;

import java.util.ArrayList;


public class House {
    private String houseName;
    private Integer ID;
    private Integer headOfHouseID;
    private ArrayList<User> members;
    private ArrayList<Task> tasks;

    private Double Longitude;
    private Double Latitude;

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }




    public House() {
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Integer countCompletedTasks(){
        Integer counter = 0;
        for(Integer i = 0; i < this.tasks.size(); i++){
            if(this.tasks.get(i).isCompleted()){
                counter++;
            }
        }
        return counter;
    }

    public Integer countTasks(){
        return this.tasks.size();
    }


    public void setHouseName(String name){
        this.houseName = name;
    }

    public String getHouseName(){
        return this.houseName;
    }

    public void setID(Integer newID){
        this.ID = newID;
    }

    public Integer getID(){
        return this.ID;
    }

    public void setHeadOfHouseID(Integer headOfHouseID) {
        this.headOfHouseID = headOfHouseID;
    }

    public Integer getHeadOfHouseID() {
        return this.headOfHouseID;
    }

    public void addMember(User m){
        this.members.add(m);
    }

    public void setMembers(ArrayList<User> m) {
        this.members = m;
    }

    public ArrayList<User> getMembers(){
        return this.members;
    }
}
