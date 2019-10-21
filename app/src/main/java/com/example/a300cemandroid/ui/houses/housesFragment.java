package com.example.a300cemandroid.ui.houses;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.example.a300cemandroid.R;

public class housesFragment extends Fragment{

    private housesViewModel homeViewModel;

    public View view;

    private FloatingActionButton faBtn;
    private Button addMemberBtn;
    private Button locationBtn;
    private Button deleteBtn;
    private Spinner housesDrop;
    private Spinner membersDrop;


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

        Toast.makeText(view.getContext(), "Setting up stuff bby!", Toast.LENGTH_SHORT).show();
        setListeners();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();


    }

    public void setListeners(){
        faBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "floating here boi!", Toast.LENGTH_SHORT).show();
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "yo!", Toast.LENGTH_SHORT).show();
            }
        });
    }



}