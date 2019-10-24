package com.example.a300cemandroid.ui.houses;

import android.arch.lifecycle.Observer;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
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
import com.example.a300cemandroid.mainScreenController;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class housesFragment extends Fragment{

    private housesViewModel homeViewModel;

    public View view;

    private FloatingActionButton faBtn;
    private Button addMemberBtn;
    private Button locationBtn;
    private Button deleteBtn;
    private Spinner housesDrop;
    private Spinner membersDrop;

    private TextView headOfHouseVal;
    private TextView tasksRemainingVal;
    private TextView totalTasksVal;

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


        headOfHouseVal = (TextView) view.findViewById(R.id.headOfHouseValue);
        tasksRemainingVal = (TextView) view.findViewById(R.id.tasksRemainingValue);
        totalTasksVal = (TextView) view.findViewById(R.id.totalTasksValue);

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


        viewModel.getTasksRemaining().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                tasksRemainingVal.setText(i.toString());
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
                setSpinners();
            }
        });

        viewModel.getUsers().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(@Nullable ArrayList<User> m) {
                members = m;
                setSpinners();
            }
        });

    }

    public void setSpinners(){

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

    public void setListeners(){
        faBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        housesDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                msController.newHouseSelected(houses.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                msController.clearFields_Houses();
            }
        });

    }


    public void setFields(){

    }

}