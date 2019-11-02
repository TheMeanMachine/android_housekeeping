package com.example.a300cemandroid.ui.houses;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.a300cemandroid.AppController;
import com.example.a300cemandroid.House;
import com.example.a300cemandroid.Task;
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

    private appViewModel appVM = appViewModel.getInstance();
    private AppController app = AppController.getInstance();

    public housesViewModel(){
        ArrayList<House> h = new ArrayList<House>();
        ArrayList<User> u = new ArrayList<User>();
        //House house = new House();
        //house.setID(1);
        //house.setHouseName("Faloula");
        //h.add(house);

        setHouses(h);
        setUsers(u);
    }
    //Singleton pattern applied
    public static housesViewModel getInstance(){
        if(instance == null){
            instance = new housesViewModel();
        }
        return instance;
    }

    public House getSelectedHouseRaw(){
        return selectedHouse.getValue();
    }

    public void setSelectedHouseRaw(House h){
        selectedHouse.setValue(h);
        updateFields();
    }

    public void updateFields(){
        House h = selectedHouse.getValue();
        totalTasks.setValue(h.countTasks());
        tasksCompleted.setValue(h.countCompletedTasks());

        User head = appVM.getUserByID(h.getHeadOfHouseID());

        headOfHouseName.setValue(head.getFullName());
        headOfHouseImg.setValue(head.getImg());

        users.setValue(h.getMembers());

        Longitude = h.getLongitude();
        Latitude = h.getLatitude();
    }

    public MutableLiveData<Bitmap> getHeadOfHouseImg() {
        return headOfHouseImg;
    }

    public void setHeadOfHouseImg(URL headOfHouseImg) {
        getImage imageRenderer = new getImage();
        imageRenderer.execute(headOfHouseImg);



    }

    private void setHeadOfHouseImgBitmap(Bitmap bitmap){
        this.headOfHouseImg.setValue(bitmap);
    }


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


    public void clearData(){
        ArrayList<User> u = new ArrayList<User>();
        users.setValue(u);

        totalTasks.setValue(0);
        tasksCompleted.setValue(0);
        headOfHouseName.setValue("");
    }

    public LiveData<String> getHeadOfHouseName(){
        return headOfHouseName;
    }

    public void setHeadOfHouseName(String name){
        headOfHouseName.setValue(name);
    }

    public MutableLiveData<ArrayList<House>> getHouses() {
        return houses;
    }

    public void addHouse(House house){
        ArrayList<House> h = houses.getValue();
        h.add(house);


        houses.setValue(h);

    }

    public void addTaskToHouse(Task task){
        selectedHouse.getValue().addTask(task);
        updateFields();
    }

    public void setHouses(ArrayList<House> h){
        houses.setValue(h);
    }

    public MutableLiveData<ArrayList<User>> getUsers(){
        return users;
    }

    public void addUser(User user){
        ArrayList<User> u = users.getValue();
        u.add(user);

        users.setValue(u);
    }

    public void setUsers(ArrayList<User> u){
        users.setValue(u);
    }

    public MutableLiveData<Integer> getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(Integer no_tasks){
        totalTasks.setValue(no_tasks);
    }

    public MutableLiveData<Integer> getTasksRemaining(){
        return tasksCompleted;
    }

    public void setTasksCompleted(Integer tasks){
        tasksCompleted.setValue(tasks);
    }

    public MutableLiveData<House> getSelectedHouse() {
        return selectedHouse;
    }

    public void setSelectedHouse(MutableLiveData<House> selectedHouse) {
        this.selectedHouse = selectedHouse;
    }

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