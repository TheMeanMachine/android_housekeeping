package com.example.a300cemandroid.ui.houses;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.a300cemandroid.House;
import com.example.a300cemandroid.MapsActivity;
import com.example.a300cemandroid.R;
import com.example.a300cemandroid.User;
import com.example.a300cemandroid.appViewModel;
import com.example.a300cemandroid.inviteMember;
import com.example.a300cemandroid.mainScreenController;
import com.example.a300cemandroid.newHouse;
import com.example.a300cemandroid.ui.tasks.tasksViewModel;

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

    private LinearLayout content;

    private ImageView headOfHouseImg;

    private ProgressBar taskProgress;

    private List<House> houses;
    private int previousHouseSize = 0;
    private List<User> members;

    private Boolean restarted;

    private static appViewModel appVM = appViewModel.getInstance();
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
        selectBtn.setVisibility(View.GONE);

        headOfHouseVal = (TextView) view.findViewById(R.id.headOfHouseValue);
        tasksCompletedVal = (TextView) view.findViewById(R.id.tasksCompletedValue);
        totalTasksVal = (TextView) view.findViewById(R.id.totalTasksValue);

        headOfHouseImg = (ImageView) view.findViewById(R.id.headOfHouseImg);

        taskProgress = (ProgressBar) view.findViewById(R.id.taskProgress);

        content = (LinearLayout) view.findViewById(R.id.content);
        content.setVisibility(View.GONE);

        restarted = true;



        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();




        setListeners();
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


                if(h.size() > 0){
                    content.setVisibility(View.VISIBLE);
                }else{
                    content.setVisibility(View.GONE);
                }

                if(h.size() == 1){
                    viewModel.setSelectedHouseRaw(h.get(0));
                    viewModel.setSelectedPosition(housesDrop.getSelectedItemPosition());
                }

                //Houses
                List<String> houseNames = new ArrayList<String>();
                for(Integer i =0; i < houses.size(); i++){
                    houseNames.add(houses.get(i).getHouseName());
                }
                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, houseNames);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                housesDrop.setAdapter(adapter);

                if(previousHouseSize != h.size()){
                    previousHouseSize = h.size();
                    housesDrop.setSelection(-1, false);
                }

                viewModel.updateFields();
            }
        });

        viewModel.getUsers().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(@Nullable ArrayList<User> m) {
                ArrayList<User> members = m;

                //User
                List<String> userNames = new ArrayList<String>();
                Log.v("houseFrag", ((Integer)members.size()).toString());
                for(int i = 0; i < members.size(); i++){

                    userNames.add(members.get(i).getFullName());
                }
                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, userNames);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                membersDrop.setAdapter(adapter);
            }
        });


        viewModel.getSelectedHouse().observe(this, new Observer<House>() {
            @Override
            public void onChanged(@Nullable House house) {

                if(house != null){
                    tasksViewModel tasksVM = tasksViewModel.getInstance();
                    tasksVM.setTasks(house.getTasks());

                }

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


                if(housesDrop.getCount() > 0){
                    Intent myIntent = new Intent(v.getContext(), MapsActivity.class);
                    Bundle extras = new Bundle();

                    if(viewModel.getLongitude() == 0.0 && viewModel.getLatitude() == 0.0){
                        Toast.makeText(getContext(), "No location saved", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        extras.putDouble("longitude", viewModel.getLongitude());
                        extras.putDouble("latitude", viewModel.getLatitude());
                    }

                    myIntent.putExtras(extras);
                    startActivity(myIntent);
                }else{
                    Toast.makeText(getContext(), "No house selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(housesDrop.getCount()  > 0){
                    deleteAlert();
                }else{
                    Toast.makeText(getContext(), "No house selected", Toast.LENGTH_SHORT).show();
                }

            }
        });

        addMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(housesDrop.getCount()  > 0){
                    Intent myIntent = new Intent(v.getContext(), inviteMember.class);
                    Bundle extras = new Bundle();
                    //extras.putInt("houseID", houses.get(housesDrop.getSelectedItemPosition()).getID());
                    myIntent.putExtras(extras);
                    startActivity(myIntent);
                }else{
                    Toast.makeText(getContext(), "No house selected", Toast.LENGTH_SHORT).show();
                }



            }
        });

        housesDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if(restarted){
                    housesDrop.setSelection(viewModel.getSelectedPosition(), false);

                    restarted = false;
                    return;
                }
                if(position > -1){
                    House selHouse = houses.get(position);
                    if(selHouse != null){




                        viewModel.setSelectedHouseRaw(selHouse);
                        viewModel.setSelectedPosition(position);

                        content.setVisibility(View.VISIBLE);

                    }else{
                        content.setVisibility(View.GONE);
                    }

                }else{

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                viewModel.clearData();
                content.setVisibility(View.GONE);
            }
        });

        /*selectBtn.setOnClickListener(new View.OnClickListener() {
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
        });*/

    }

    public void deleteAlert(){
        new AlertDialog.Builder(getContext())
                .setTitle("Delete house")
                .setMessage("Are you sure you want to delete this house?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Integer pos = housesDrop.getSelectedItemPosition();
                        House selHouse = houses.get(pos);
                        if(selHouse != null){
                            viewModel.deleteHouse(selHouse);
                        }
                        viewModel.setSelectedPosition(housesDrop.getSelectedItemPosition());

                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}