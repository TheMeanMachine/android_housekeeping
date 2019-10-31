package com.example.a300cemandroid;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a300cemandroid.ui.houses.housesViewModel;

import java.util.ArrayList;
import java.util.List;

public class inviteMember extends AppCompatActivity {
    private housesViewModel housesVM;
    private appViewModel appVM;

    private ArrayList<User> users;
    private ArrayList<User> membersExist;
    private Spinner usersDrop;

    private Button inviteBtn;
    private Button cancelBtn;
    private Integer houseID;

    private House house;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_invite_member);

        Bundle b = getIntent().getExtras();
        houseID = -1; // or other values
        if(b != null) {
            houseID = b.getInt("houseID");
        }
        housesVM = housesViewModel.getInstance();

        membersExist = house.getMembers();

        appVM = appViewModel.getInstance();



        house = appVM.getHouseByID(houseID);

        if(house == null){
            Toast.makeText(this, "No found", Toast.LENGTH_SHORT).show();
            finish();
        }

        inviteBtn = (Button) findViewById(R.id.inviteBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        usersDrop = (Spinner) findViewById(R.id.usersDropdown);

        setListeners();
        setObservers();
    }

    private void setListeners(){
        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = users.get(usersDrop.getSelectedItemPosition());
                housesVM.addUser(user);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setObservers(){
        appVM.getAllUsers().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(@Nullable ArrayList<User> u) {
                users = removeExistingMembers(u);

                //Houses
                List<String> userNames = new ArrayList<String>();
                for(Integer i = 0; i < u.size(); i++){
                    userNames.add(users.get(i).getFullName());
                }
                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, userNames);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                usersDrop.setAdapter(adapter);
            }
        });


    }

    private ArrayList<User> removeExistingMembers(ArrayList<User> u){
        ArrayList<User> newUserList = u;
        for(int i = 0; i < u.size(); i++){
            for(int j = 0; j < membersExist.size(); i++){
                if(u.get(i).getID() == membersExist.get(j).getID()) {
                    newUserList = removeUserByID(u.get(i).getID(), newUserList);
                    break;
                }
            }
        }
        return newUserList;
    }

    private ArrayList<User> removeUserByID(Integer ID, ArrayList<User> u){
        for(int i = 0; i < u.size(); i++){
            if(u.get(i).getID() == ID){
                u.remove(i);
                break;
            }
        }
        return u;
    }

}
