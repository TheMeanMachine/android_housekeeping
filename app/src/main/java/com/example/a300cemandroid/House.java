package com.example.a300cemandroid;

import java.util.ArrayList;


public class House {
    private String houseName = "";
    private Integer ID = 0;
    private Integer headOfHouseID = 0;
    private ArrayList<User> members = new ArrayList<>();
    private ArrayList<taskObj> tasks = new ArrayList<>();

    private Double Longitude = 0.0;
    private Double Latitude = 0.0;

    public House() {


    }

    /**
     * Returns longitude of the house
     * @return Double value of the longitude
     */
    public Double getLongitude() {
        return Longitude;
    }

    /**
     * Sets longitude of the houses
     * @param longitude - Double containing longitude
     */
    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    /**
     * Returns Latitude of the house
     * @return Double value of the Latitude
     */
    public Double getLatitude() {
        return Latitude;
    }

    /**
     * Sets Latitude of the houses
     * @param latitude - Double containing Latitude
     */
    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    /**
     * Adds a task to the task list
     * @param t - task to add
     */
    public void addTask(taskObj t){
        tasks.add(t);
    }

    /**
     * Gets the list of tasks associated with the house
     * @return ArrayList containing taskObj objs
     */
    public ArrayList<taskObj> getTasks() {
        return tasks;
    }

    /**
     * Overrides the task list with new task list
     * @param tasks - ArrayList containing task
     */
    public void setTasks(ArrayList<taskObj> tasks) {
        this.tasks = tasks;
    }

    /**
     * Counts the amount of tasks have been completed within the task list
     * @return Integer containing the number of completed tasks
     */
    public Integer countCompletedTasks(){
        Integer counter = 0;
        for(Integer i = 0; i < this.tasks.size(); i++){
            if(this.tasks.get(i).isCompleted()){
                counter++;
            }
        }
        return counter;
    }

    /**
     * Returns the total amount of tasks associated with the house
     * @return Integer containing the number of tasks
     */
    public Integer countTasks(){
        return this.tasks.size();
    }


    /**
     * Sets the house name
     * @param name - Name to set
     */
    public void setHouseName(String name){
        this.houseName = name;
    }

    /**
     * Returns house name
     * @return String containing house name
     */
    public String getHouseName(){
        return this.houseName;
    }

    /**
     * Sets ID of the house
     * @param newID - ID to set (refers to DB)
     */
    public void setID(Integer newID){
        this.ID = newID;
    }

    /**
     * Returns the DB-associated ID of house
     * @return - Integer ID associated to House from DB
     */
    public Integer getID(){
        return this.ID;
    }

    /**
     * Sets the headOfHouseID (refers to user ID)
     * @param headOfHouseID - userID of the headOfHouse
     */
    public void setHeadOfHouseID(Integer headOfHouseID) {
        this.headOfHouseID = headOfHouseID;
    }

    /**
     * Gets the headOfHouseID (refers to user ID)
     * @return Integer ID of the headOfHouse
     */
    public Integer getHeadOfHouseID() {
        return this.headOfHouseID;
    }

    /**
     * Adds a user to the house
     * @param m - User obj containing the user data
     */
    public void addMember(User m){
        this.members.add(m);
    }

    /**
     * Overrides the members list to a new list
     * @param m - ArrayList of user objs
     */
    public void setMembers(ArrayList<User> m) {
        this.members = m;
    }

    /**
     * Returns the members list
     * @return ArrayList of User objs
     */
    public ArrayList<User> getMembers(){
        return this.members;
    }
}
