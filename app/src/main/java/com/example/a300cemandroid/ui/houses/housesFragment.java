package com.example.a300cemandroid.ui.houses;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a300cemandroid.AppController;
import com.example.a300cemandroid.House;
import com.example.a300cemandroid.R;
import com.example.a300cemandroid.User;

import com.example.a300cemandroid.inviteMember;
import com.example.a300cemandroid.mainScreenController;
import com.example.a300cemandroid.newHouse;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class housesFragment extends Fragment{

    private housesViewModel homeViewModel;

    public View view;

    private FloatingActionButton faBtn;
    private Button addMemberBtn;
    private Button locationBtn;
    private Button selectBtn;
    private Button deleteBtn;
    private Spinner housesDrop;
    private Spinner membersDrop;

    private TextView headOfHouseVal;
    private TextView tasksCompletedVal;
    private TextView totalTasksVal;

    private ImageView headOfHouseImg;

    private ProgressBar taskProgress;

    private List<House> houses;
    private List<User> members;

    private static AppController appController = AppController.getInstance();
    private static mainScreenController msController = mainScreenController.getInstance();
    private housesViewModel viewModel = housesViewModel.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(housesViewModel.class);

        view = inflater.inflate(R.layout.fragment_house,container,false);



        faBtn = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        addMemberBtn = (Button) view.findViewById(R.id.inviteMemberBtn);
        locationBtn = (Button) view.findViewById(R.id.locationBtn);
        deleteBtn = (Button) view.findViewById(R.id.deleteHouseBtn);
        housesDrop = (Spinner) view.findViewById(R.id.housesDropdown);
        membersDrop = (Spinner) view.findViewById(R.id.membersDrop);
        selectBtn = (Button) view.findViewById(R.id.selectBtn);

        headOfHouseVal = (TextView) view.findViewById(R.id.headOfHouseValue);
        tasksCompletedVal = (TextView) view.findViewById(R.id.tasksCompletedValue);
        totalTasksVal = (TextView) view.findViewById(R.id.totalTasksValue);

        headOfHouseImg = (ImageView) view.findViewById(R.id.headOfHouseImg);

        taskProgress = (ProgressBar) view.findViewById(R.id.taskProgress);

        setListeners();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        setObservers();

    }

    private void setObservers(){

        viewModel.getHeadOfHouseName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                headOfHouseVal.setText(s);
            }
        });

        viewModel.getHeadOfHouseImg().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap bitmap) {
                headOfHouseImg.setImageBitmap(bitmap);
            }
        });


        viewModel.getTasksRemaining().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                tasksCompletedVal.setText(i.toString());
                taskProgress.setProgress(i);
            }
        });

        viewModel.getTotalTasks().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                totalTasksVal.setText(i.toString());
                taskProgress.setMax(i);
            }
        });

        viewModel.getHouses().observe(this, new Observer<ArrayList<House>>() {
            @Override
            public void onChanged(@Nullable ArrayList<House> h) {
                houses = h;

                //Houses
                List<String> houseNames = new ArrayList<String>();
                for(Integer i =0; i < houses.size(); i++){
                    houseNames.add(houses.get(i).getHouseName());
                }
                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, houseNames);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                housesDrop.setAdapter(adapter);
            }
        });

        viewModel.getUsers().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(@Nullable ArrayList<User> m) {
                members = m;

                //User
                List<String> userNames = new ArrayList<String>();
                for(Integer i = 0; i < members.size(); i++){
                    userNames.add(members.get(i).getFullName());
                }
                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, userNames);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                membersDrop.setAdapter(adapter);
            }
        });

    }



    public void setListeners(){
        faBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), newHouse.class);

                startActivity(myIntent);
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer sel = (Integer) housesDrop.getSelectedItemPosition();
                if(housesDrop.getCount()  > 0){
                    Intent myIntent = new Intent(v.getContext(), inviteMember.class);
                    Bundle extras = myIntent.getExtras();
                    extras.putInt("longitude", viewModel.getLongitude());
                    extras.putInt("latitude", viewModel.getLatitude());
                    startActivity(myIntent);
                }else{
                    Toast.makeText(getContext(), "No house selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlert();
            }
        });

        addMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(housesDrop.getCount()  > 0){
                    Intent myIntent = new Intent(v.getContext(), inviteMember.class);
                    Bundle extras = myIntent.getExtras();
                    extras.putInt("houseID", housesDrop.getSelectedItemPosition());
                    startActivity(myIntent);
                }else{
                    Toast.makeText(getContext(), "No house selected", Toast.LENGTH_SHORT).show();
                }



            }
        });

        housesDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                if(position > 0){
                    House selHouse = houses.get(position);
                    if(selHouse != null){
                        msController.newHouseSelected(selHouse);
                    }

                }else{
                    Toast.makeText(getContext(), "No house selected", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                msController.clearFields_Houses();
            }
        });

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Integer pos = housesDrop.getSelectedItemPosition();
                if(housesDrop.getCount() > 0){
                    House selHouse = houses.get(pos);
                    if(selHouse != null){
                        msController.selectHouse(selHouse);
                    }

                }else{
                    Toast.makeText(getContext(), "No house selected", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public void deleteAlert(){
        new AlertDialog.Builder(getContext())
                .setTitle("Delete house")
                .setMessage("Are you sure you want to delete this house?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Integer pos = housesDrop.getSelectedItemPosition();
                        if(housesDrop.getCount()  > 0){
                            House selHouse = houses.get(pos);
                            if(selHouse != null){
                                msController.deleteHouse(selHouse);
                            }

                        }else{
                            Toast.makeText(getContext(), "No house selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}