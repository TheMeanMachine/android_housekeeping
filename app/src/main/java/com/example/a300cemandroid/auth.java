package com.example.a300cemandroid;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.nio.charset.Charset;
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
        Log.v("auth", u.getFullName());
        //login


    }

}
