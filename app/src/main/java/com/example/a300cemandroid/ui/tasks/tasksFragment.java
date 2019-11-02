package com.example.a300cemandroid.ui.tasks;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a300cemandroid.House;
import com.example.a300cemandroid.R;
import com.example.a300cemandroid.Task;
import com.example.a300cemandroid.User;
import com.example.a300cemandroid.taskAdapter;
import com.example.a300cemandroid.ui.account.accountViewModel;
import com.example.a300cemandroid.ui.houses.housesViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class tasksFragment extends Fragment {
    private View view;
    private tasksViewModel tasksVM;
    private housesViewModel houseVM = housesViewModel.getInstance();
    private accountViewModel accountVM = accountViewModel.getInstance();

    private ArrayList<Task> tasks = new ArrayList<>();

    private ListView list;

    private FloatingActionButton fab;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tasksVM =
                ViewModelProviders.of(this).get(tasksViewModel.class);
        view = inflater.inflate(R.layout.fragment_tasks, container, false);

        list = view.findViewById(R.id.taskListView);

        fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        setListeners();
        setObservers();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    private void setListeners(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(houseVM.getSelectedHouseRaw() == null){
                    Toast.makeText(getContext(), "No house selected", Toast.LENGTH_SHORT).show();
                }else{
                    Task tNew = new Task();
                    User uNew = accountVM.getCurrentUser();
                    tNew.setTitle("Somewhere");
                    SimpleDateFormat date = new SimpleDateFormat("dd-MM-yy");//https://stackoverflow.com/questions/28542070/how-to-save-date-dd-mm-yyyy-in-java
                    tNew.setDateMade(date);
                    date = new SimpleDateFormat("HH:mm:ss");
                    tNew.setTimeMade(date);
                    tNew.setMadeBy(uNew);

                    houseVM.addTaskToHouse(tNew);
                    setList();
                    //tasksVM.addTask(tNew);
                }

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }

    private void setObservers(){
        tasksVM.getTasks().observe(this, new Observer<ArrayList<Task>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Task> task) {
                if(houseVM.getSelectedHouseRaw() != null){

                    //tasksVM.setTasks(houseVM.getSelectedHouseRaw().getTasks());
                    tasks = tasksVM.getTasks().getValue();
                }
                setList();

            }
        });

        houseVM.getSelectedHouse().observe(this, new Observer<House>() {
            @Override
            public void onChanged(@Nullable House house) {

                if(houseVM.getSelectedHouseRaw() != null){

                    tasksVM.setTasks(houseVM.getSelectedHouseRaw().getTasks());
                    tasks = tasksVM.getTasks().getValue();
                }
                setList();
            }
        });


    }

    private void setList(){


        list.setAdapter(new taskAdapter(view.getContext(), R.layout.task_item, tasks));


    }
}