package com.example.a300cemandroid;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a300cemandroid.ui.account.accountViewModel;
import com.example.a300cemandroid.ui.houses.housesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class newHouse extends AppCompatActivity {
    private housesViewModel housesVM;
    private appViewModel appVM;
    private accountViewModel accountVM;
    private nameValidation nameValidator;

    private Spinner usersDrop;
    private EditText houseName;
    private TextView houseNameError;
    private Switch locationSwitch;

    private Button okayBtn;
    private Button cancelBtn;
    private ImageButton mic;

    private Double latitude = 0.0;
    private Double longitude = 0.0;

    private static final int REQ_CODE_SPEECH_INPUT = 100;


    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_new_house);

        housesVM = housesViewModel.getInstance();
        appVM = appViewModel.getInstance();
        accountVM = accountViewModel.getInstance();
        nameValidator = new nameValidation();

        usersDrop = (Spinner) findViewById(R.id.membersDrop);

        houseName = (EditText) findViewById(R.id.houseName);
        houseNameError = (TextView) findViewById(R.id.houseNameError);

        locationSwitch = (Switch) findViewById(R.id.locationSwitch);

        okayBtn = (Button) findViewById(R.id.okayBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        mic = (ImageButton) findViewById(R.id.mic);

        setListeners();
        setObservers();



    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say name");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }


    private void setListeners() {
        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHouse();
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //If user wants location saved
                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        //Location is not enabled
                        Toast.makeText(newHouse.this, "You need to enable location permissions", Toast.LENGTH_SHORT).show();
                        locationSwitch.setChecked(false);

                    }
                    //Get location
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    //Show user
                    Toast.makeText(newHouse.this, "Your longitude is: " + Double.toString(longitude) + " Your latitude is: " + Double.toString(latitude), Toast.LENGTH_SHORT).show();

                }else{
                    longitude = 0.0;
                    latitude = 0.0;
                }
            }
        });

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });

    }

    private void setObservers(){

        /*
        Sets the list of users for the user to select head of house from
         */
        appVM.getAllUsers().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(@Nullable ArrayList<User> u) {
                users = u;


                assert u != null;
                if(u.size() > 0){

                    List<String> userNames = new ArrayList<>();
                    for(Integer i = 0; i < u.size(); i++){
                        userNames.add(users.get(i).getFullName());
                    }
                    ArrayAdapter<String> adapter;
                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, userNames);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    usersDrop.setAdapter(adapter);
                }

            }
        });
    }

    /*
    Adds a new house to the system
     */
    private void addHouse(){
        String name = houseName.getText().toString();
        Integer headPos = usersDrop.getSelectedItemPosition();
        Integer headID = users.get(headPos).getID();

        House h = new House();
        h.setHouseName(name);
        //TODO: add db ID
        h.setID(housesVM.getHouses().getValue().size()+1);

        h.setHeadOfHouseID(headID);

        ArrayList<User> u= new ArrayList<>();

        if(!accountVM.getCurrentUser().getID().equals(headID)){//If current user is not the same as the head of house
            u.add(accountVM.getCurrentUser());
        }
        u.add(appVM.getUserByID(headID));
        h.setMembers(u);

        h.setLatitude(latitude);
        h.setLongitude(longitude);

        appVM.addHouse(h);
        housesVM.addHouse(h);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Returns microphone data and inputs it into houseName
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    houseName.setText(result.get(0));
                }
                break;
            }

        }
    }


}
