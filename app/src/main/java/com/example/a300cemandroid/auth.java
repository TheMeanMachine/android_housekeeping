package com.example.a300cemandroid;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.a300cemandroid.ui.account.accountViewModel;
import com.example.a300cemandroid.ui.houses.housesViewModel;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class auth {

    private Context context;

    public auth(Context context){
        this.context = context;
    }
    /**
     * Takes user based on third party data
     * @param user User obj containing needed data
     */
    public void loginUserWithThird(User user){
        DatabaseHandler db = new DatabaseHandler(context);

        db.addUser(user);

        User u = db.getUser(user.getEmail());
        Log.v("auth", "logged in");
        //login

        startUpApp(u);
    }

    /**
     * Initialises the application with the new data corrospoding to the new user
     * @param currentUser - user start the app with
     */
    private void startUpApp(User currentUser){
        DatabaseHandler db = new DatabaseHandler(context);

        MutableLiveData<ArrayList<User>> users = new MutableLiveData<>();
        ArrayList<House> houses_CU = db.getHouses(currentUser.getID());

        users.setValue(db.getUsers());


        appViewModel appVM = appViewModel.getInstance();
        accountViewModel accVM = accountViewModel.getInstance();
        housesViewModel houseVM = housesViewModel.getInstance();

        accVM.setCurrentUser(currentUser);
        appVM.setAllUsers(users);
        houseVM.setHouses(houses_CU);


        Intent i = new Intent(context.getApplicationContext(), main_screen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(i);
    }


}
