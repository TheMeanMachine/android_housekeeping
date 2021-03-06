package com.example.a300cemandroid.ui.tasks;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a300cemandroid.DatabaseHandler;
import com.example.a300cemandroid.House;
import com.example.a300cemandroid.R;
import com.example.a300cemandroid.taskObj;
import com.example.a300cemandroid.User;
import com.example.a300cemandroid.taskAdapter;
import com.example.a300cemandroid.ui.account.accountViewModel;
import com.example.a300cemandroid.ui.houses.housesViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class tasksFragment extends Fragment  {
    private View view;
    private tasksViewModel tasksVM = tasksViewModel.getInstance();
    private housesViewModel houseVM = housesViewModel.getInstance();
    private accountViewModel accountVM = accountViewModel.getInstance();

    private ArrayList<taskObj> tasks = new ArrayList<>();

    private ListView list;

    private FloatingActionButton fab;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //tasksVM =
         //       ViewModelProviders.of(this).get(tasksViewModel.class);
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

    /**
     * Sets the listeners of the elements
     */
    private void setListeners(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(houseVM.getSelectedHouseRaw() == null){
                    Toast.makeText(getContext(), "No house selected", Toast.LENGTH_SHORT).show();
                }else{
                    taskObj tNew = new taskObj();
                    User uNew = accountVM.getCurrentUser();
                    tNew.setTitle("Title");

                    tNew.setMadeBy(uNew);

                    DatabaseHandler db = new DatabaseHandler(getContext());
                    Long id = db.addTask(tNew, houseVM.getSelectedHouse().getValue().getID());
                    if(id > 0){
                        tNew.setID(id.intValue());

                        houseVM.addTaskToHouse(tNew);
                        setList();
                    }
                    db.closeDB();

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

    /**
     * Sets observers of the viewmodel
     */
    private void setObservers(){
        tasksVM.getTasks().observe(this, new Observer<ArrayList<taskObj>>() {
            @Override
            public void onChanged(@Nullable ArrayList<taskObj> task) {
                if(houseVM.getSelectedHouseRaw() != null){
                    if(tasks.size() > 0) Log.v("tasksYo", task.get(0).getTitle());
                    //tasksVM.setTasks(houseVM.getSelectedHouseRaw().getTasks());
                    tasks = tasksVM.getTasks().getValue();
                }
                setList();

            }
        });

    }

    /**
     * Creates the list based on the task list
     */
    private void setList(){


        list.setAdapter(new taskAdapter(view.getContext(), R.layout.task_item, tasks));


    }
}