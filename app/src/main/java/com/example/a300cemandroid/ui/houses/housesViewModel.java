package com.example.a300cemandroid.ui.houses;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.a300cemandroid.DatabaseHandler;
import com.example.a300cemandroid.House;
import com.example.a300cemandroid.taskObj;
import com.example.a300cemandroid.User;
import com.example.a300cemandroid.appViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class housesViewModel extends ViewModel {
    private static housesViewModel instance = null;

    private MutableLiveData<ArrayList<House>> houses = new MutableLiveData<>();
    private MutableLiveData<ArrayList<User>> users = new MutableLiveData<>();

    private MutableLiveData<Integer> totalTasks = new MutableLiveData<>();
    private MutableLiveData<Integer> tasksCompleted = new MutableLiveData<>();
    private MutableLiveData<String> headOfHouseName = new MutableLiveData<>();

    private MutableLiveData<Bitmap> headOfHouseImg = new MutableLiveData<>();

    private Double Longitude = 0.0;
    private Double Latitude = 0.0;

    private MutableLiveData<House> selectedHouse = new MutableLiveData<>();
    private int selectedPosition = 0;

    private appViewModel appVM = appViewModel.getInstance();

    //Singleton pattern applied
    public static housesViewModel getInstance(){
        if(instance == null){
            instance = new housesViewModel();
        }
        return instance;
    }

    /**
     * Resets the instance of this class
     */
    public void reset(){
        this.instance = new housesViewModel();
    }


    /**
     * Returns the value of the selected house
     * @return House obj
     */
    public House getSelectedHouseRaw(){
        return selectedHouse.getValue();
    }

    /**
     * Sets the selected house and updates data
     * @param h - house to update by
     */
    public void setSelectedHouseRaw(House h){
        selectedHouse.setValue(h);
        updateFields();
    }

    /**
     * Updates the variables based on the selected house
     */
    public void updateFields(){
        House h = selectedHouse.getValue();
        if(h != null) {
            totalTasks.setValue(h.countTasks());
            tasksCompleted.setValue(h.countCompletedTasks());

            User head = appVM.getUserByID(h.getHeadOfHouseID());
            if(head != null){
                headOfHouseName.setValue(head.getFullName());
                headOfHouseImg.setValue(head.getImg());
            }


            users.setValue(h.getMembers());

            Longitude = h.getLongitude();
            Latitude = h.getLatitude();
        }else{
            if(houses.getValue().size() != 0){
                setSelectedHouseRaw(houses.getValue().get(0));
                updateFields();//go again
            }

        }

    }

    /**
     * Gets the head of house image as MutableLiveData
     * @return MutableLiveData of headofHouseImg bitmap
     */
    public MutableLiveData<Bitmap> getHeadOfHouseImg() {
        return headOfHouseImg;
    }


    /**
     * Sets the bitmap form of the head of house img
     * @param bitmap - new bitmap
     */
    private void setHeadOfHouseImgBitmap(Bitmap bitmap){
        this.headOfHouseImg.setValue(bitmap);
    }


    /**
     * Gets the longitude of the selected house
     * @return Double containing the longitude
     */
    public Double getLongitude() {
        return Longitude;
    }

    /**
     * Sets the longitude of the selected house
     * @param longitude Double containing new longitude
     */
    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }
    /**
     * Gets the latitude of the selected house
     * @return Double containing the latitude
     */
    public Double getLatitude() {
        return Latitude;
    }
    /**
     * Sets the latitude of the selected house
     * @param latitude Double containing new latitude
     */
    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }


    /**
     * Clears  all data for the selected house
     */
    public void clearData(){
        ArrayList<User> u = new ArrayList<User>();
        users.setValue(u);

        totalTasks.setValue(0);
        tasksCompleted.setValue(0);
        headOfHouseName.setValue("");
    }

    /**
     * Gets head of HeadOfHouseName
     * @return LiveData String
     */
    public LiveData<String> getHeadOfHouseName(){
        return headOfHouseName;
    }

    /**
     * Set the headOfHouseName to new string value
     * @param name string value to set by
     */
    public void setHeadOfHouseName(String name){
        headOfHouseName.setValue(name);
    }

    /**
     * gets the arraylist of houses
     * @return MutableLiveData arraylist of House obj
     */
    public MutableLiveData<ArrayList<House>> getHouses() {
        return houses;
    }

    /**
     * Adds a new house to the house list
     * @param house - house to add to the list
     */
    public void addHouse(House house){

        ArrayList<House> h = houses.getValue();
        h.add(house);

        houses.setValue(h);
    }

    /**
     * Adds a new task to the currently selected house
     * @param task - task to add
     */
    public void addTaskToHouse(taskObj task){
        selectedHouse.getValue().addTask(task);
        updateFields();
    }

    /**
     * Overrides the current house list with new one
     * @param h - ArrayList of house
     */
    public void setHouses(ArrayList<House> h){
        houses.setValue(h);
        if(h.size() > 0){
            setSelectedHouseRaw(h.get(0));
            setSelectedPosition(0);
            updateFields();//Update the fields based on new data
        }
    }

    /**
     * Returns users list of current house
     * @return MutableLiveData of ArrayList of user obj
     */
    public MutableLiveData<ArrayList<User>> getUsers(){
        return users;
    }

    /**
     * Add a new user to the current house
     * @param user - user to add
     */
    public void addUser(User user){

        //houses.getValue().get(selectedPosition).addMember(user);
        selectedHouse.getValue().addMember(user);


        updateFields();

    }

    /**
     * Deletes a house
     * @param house - house to delete
     */
    public void deleteHouse(House house){
        //Delete code
        ArrayList<House> result = getHouses().getValue();

        result.remove(getSelectedPosition());

        setHouses(result);

        updateFields();

    }

    /**
     * Sets the users list
     * @param u - ArrayList of user obj
     */
    public void setUsers(ArrayList<User> u){
        users.setValue(u);
    }

    /**
     * Gets the total amount of tasks
     * @return MutableLiveData of integer containing total tasks
     */
    public MutableLiveData<Integer> getTotalTasks() {
        return totalTasks;
    }

    /**
     * Sets the total number of tasks
     * @param no_tasks - Integer containing number of tasks
     */
    public void setTotalTasks(Integer no_tasks){
        totalTasks.setValue(no_tasks);
    }

    /**
     * Gets the tasks which remain uncompleted
     * @return - MutableLiveData of Integer containing tasks remaining
     */
    public MutableLiveData<Integer> getTasksRemaining(){
        return tasksCompleted;
    }

    /**
     * Sets the tasks completed
     * @param tasks - integer of tasks completed
     */
    public void setTasksCompleted(Integer tasks){
        tasksCompleted.setValue(tasks);
    }

    /**
     * Gets the currently selected house
     * @return MutableLiveData of House obj
     */
    public MutableLiveData<House> getSelectedHouse() {
        return selectedHouse;
    }

    /**
     * Sets the selected house to a new value
     * @param selectedHouse MutableLiveData of House obj
     */
    public void setSelectedHouse(MutableLiveData<House> selectedHouse) {
        this.selectedHouse = selectedHouse;
    }

    /**
     * Gets the selected position in the dropdown
     * @return Int of selected position
     */
    public int getSelectedPosition() {
        return selectedPosition;
    }

    /**
     * Sets the selected position referring to the dropdown
     * @param selectedPosition - Int of dropdown location
     */
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    /**
     * Pulls image from website and converts to bitmap
     */
    private class getImage extends AsyncTask<URL, Void, Bitmap> {
        public getImage(){
            super();
        }

        @Override
        protected Bitmap doInBackground(URL[] params) {
            URL url = params[0];
            Bitmap bitmap = null;
            try {

                assert url != null;
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                if(bitmap != null){
                    return bitmap;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            if(bitmap != null){
                setHeadOfHouseImgBitmap(bitmap);
            }
        }
    }



}