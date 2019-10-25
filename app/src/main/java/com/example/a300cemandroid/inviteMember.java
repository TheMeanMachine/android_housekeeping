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

import com.example.a300cemandroid.ui.houses.housesViewModel;

import java.util.ArrayList;
import java.util.List;

public class inviteMember extends AppCompatActivity {
    private housesViewModel housesVM;
    private appViewModel appVM;

    private Spinner usersDrop;

    private Button inviteBtn;
    private Button cancelBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_invite_member);

        housesVM = housesViewModel.getInstance();
        appVM = appViewModel.getInstance();

        inviteBtn = (Button) findViewById(R.id.inviteMemberBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        usersDrop = (Spinner) findViewById(R.id.usersDropdown);

        setListeners();
        setObservers();
    }

    public void setListeners(){
        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setObservers(){
        appVM.getAllUsers().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(@Nullable ArrayList<User> u) {
                ArrayList<User> users = u;

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


}
