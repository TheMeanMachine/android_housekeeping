package com.example.a300cemandroid.ui.tasks;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.a300cemandroid.R;
import com.example.a300cemandroid.Task;
import com.example.a300cemandroid.User;
import com.example.a300cemandroid.taskAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class tasksFragment extends Fragment {
    private View view;
    private tasksViewModel tasksVM;

    private ArrayList<Task> tasks;

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

                Task tNew = new Task();
                User uNew = new User();
                tNew.setTitle("Somewhere");
                uNew.setFirstName("Aaron");
                uNew.setLastName("Mandol");
                SimpleDateFormat date = new SimpleDateFormat("dd-MM-yy");
                tNew.setDateMade(date);
                tNew.setTimeMade(date);
                tNew.setMadeBy(uNew);

                tasksVM.addTask(tNew);
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
                tasks = task;
                setList();

            }
        });
    }

    private void setList(){
        list.setAdapter(new taskAdapter(view.getContext(), R.layout.task_item, tasks));


    }
}