package com.example.a300cemandroid;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.a300cemandroid.ui.houses.housesViewModel;

import java.util.ArrayList;
import java.util.List;

public class newHouse extends AppCompatActivity {
    private housesViewModel housesVM;
    private appViewModel appVM;
    private nameValidation nameValidator;

    private Spinner usersDrop;
    private EditText houseName;
    private TextView houseNameError;
    private Switch locationSwitch;

    private Button okayBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_new_house);

        housesVM = housesViewModel.getInstance();
        appVM = appViewModel.getInstance();
        nameValidator = new nameValidation();

        usersDrop = (Spinner) findViewById(R.id.usersDropdown);

        houseName = (EditText) findViewById(R.id.houseName);
        houseNameError = (TextView) findViewById(R.id.houseNameError);

        locationSwitch = (Switch) findViewById(R.id.locationSwitch);

        okayBtn = (Button) findViewById(R.id.okayBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        setListeners();
        setObservers();
    }

    public void setListeners(){
        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                }else{

                }
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
