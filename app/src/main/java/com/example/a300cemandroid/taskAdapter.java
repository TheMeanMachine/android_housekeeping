package com.example.a300cemandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class taskAdapter extends ArrayAdapter<Task> {
    private ArrayList<Task> tasks;
    private Context context;
    private int resource;

    public taskAdapter(@NonNull Context context, int resource, ArrayList<Task> tasks) {
        super(context, resource, tasks);
        this.resource = resource;
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        try{
            if(v==null){
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = layoutInflater.inflate(resource, parent, false);
            }

            TextView time = (TextView) v.findViewById(R.id.timeVal);
            TextView date = (TextView) v.findViewById(R.id.dateVal);
            TextView madeBy = (TextView) v.findViewById(R.id.madeByVal);
            TextView title = (TextView) v.findViewById(R.id.taskTitle);
            CheckBox completed = (CheckBox) v.findViewById(R.id.completedCheck);

            Task task = tasks.get(position);

            time.setText(task.getStringTimeMade());
            date.setText(task.getStringDateMade());
            madeBy.setText(task.getMadeBy().getFullName());
            title.setText(task.getTitle());
            completed.setChecked(task.isCompleted());


        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return v;
    }
}
