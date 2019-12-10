package com.example.a300cemandroid;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<User> membersExist = new ArrayList<>();
    private Spinner usersDrop;

    private Button inviteBtn;
    private Button cancelBtn;
    private Integer houseID;

    private House house;

    private ArrayList<User> removedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_invite_member);

        Bundle b = getIntent().getExtras();

        housesVM = housesViewModel.getInstance();
        house = housesVM.getSelectedHouseRaw();
        appVM = appViewModel.getInstance();

        membersExist = new ArrayList<User>(housesVM.getUsers().getValue());
        users = new ArrayList<User>(appVM.getAllUsers().getValue());

        houseID = house.getID();




        if(house == null){
            Toast.makeText(this, "No found", Toast.LENGTH_SHORT).show();
            finish();
        }

        inviteBtn = (Button) findViewById(R.id.inviteBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        usersDrop = (Spinner) findViewById(R.id.usersDropdown);

        setListeners();
        setSpinner();
    }

    private void setListeners(){
        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = removedList.get(usersDrop.getSelectedItemPosition());
                housesVM.addUser(user);

                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.addUserToHouse(user.getID(), houseID);

                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    private void setSpinner(){
        membersExist = new ArrayList<User>(housesVM.getUsers().getValue());
        users = new ArrayList<User>(appVM.getAllUsers().getValue());

        if (users.size() == 0 || membersExist.size() == users.size()) {
            Toast.makeText(getApplicationContext(), "No users to invite", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        removedList = removeExistingMembers(users, membersExist);

        if(removedList.size() == 0){
            Toast.makeText(getApplicationContext(), "No users to invite", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        List<String> userNames = new ArrayList<>();
        for(int i = 0; i < removedList.size(); i++){
            userNames.add(removedList.get(i).getFullName());
        }
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, userNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        usersDrop.setAdapter(adapter);
    }

    private ArrayList<User> removeExistingMembers(ArrayList<User> u, ArrayList<User> m){
        /*for(int i = 0; i < u.size(); i++){
            Integer thisID = u.get(i).getID();
            for(int j = 0; j < m.size(); j++){
                if(thisID.intValue() == m.get(j).getID().intValue()) {
                    result = removeUserByID(thisID, u);

                    break;
                }
            }
        }*/
        ArrayList<User> result = new ArrayList<>(u);

        ArrayList<Integer> uID = new ArrayList<>();
        for(int i = 0; i < u.size(); i++){
            uID.add(u.get(i).getID());
            Log.d("IM_ember - uID", String.valueOf(u.get(i).getID()));
        }

        //ArrayList<Integer> mID = new ArrayList<>();
        for(int i = 0; i < m.size(); i++){
            //mID.add(m.get(i).getID());
            Log.d("IM_ember - mID", String.valueOf(m.get(i).getID()));
            int index = uID.indexOf(m.get(i).getID());
            if(index >= 0){
                result.remove(index);
                uID.remove(index);
                Log.d("IM_ember-Deleting", String.valueOf(index));
            }else{
                Log.d("IM_ember-Notfound", String.valueOf(index));
            }


        }

        return result;

    }



}
