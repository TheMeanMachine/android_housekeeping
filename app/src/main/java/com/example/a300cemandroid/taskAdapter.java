package com.example.a300cemandroid;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Task element (adapter) to insert into task list
 */
public class taskAdapter extends ArrayAdapter<taskObj> {
    private ArrayList<taskObj> tasks;
    private Context context;
    private int resource;

    /**
     * Constructor
     * @param context Sets the context of the app
     * @param resource - Resource
     * @param tasks - List of tasks
     */
    public taskAdapter(@NonNull Context context, int resource, ArrayList<taskObj> tasks) {
        super(context, resource, tasks);
        this.resource = resource;
        this.context = context;
        this.tasks = tasks;
    }


    /**
     * Sets the task view
     * @param position position of this particular adapter
     * @param convertView - View of the placement
     * @param parent - parent element
     * @return view of the adapter
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        try{
            if(v==null){//If view is not present
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = layoutInflater.inflate(resource, parent, false);
            }

            final taskObj task = tasks.get(position);

            TextView time = (TextView) v.findViewById(R.id.timeVal);
            TextView date = (TextView) v.findViewById(R.id.dateVal);
            TextView madeBy = (TextView) v.findViewById(R.id.madeByVal);
            final TextView title = (TextView) v.findViewById(R.id.taskTitle);
            final EditText titleEdit = (EditText) v.findViewById(R.id.taskTitleEdit);
            CheckBox completed = (CheckBox) v.findViewById(R.id.completedCheck);

            final Button editBtn = (Button) v.findViewById(R.id.editBtn);
            final Button doneBtn = (Button) v.findViewById(R.id.doneBtn);


            //User clicks edit button
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(task.getEditing()){//Already editing, undo changes
                        task.setEditing(false);
                        editBtn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.ic_edit_black_24dp));
                        doneBtn.setVisibility(View.GONE);
                        title.setVisibility(View.VISIBLE);
                        titleEdit.setVisibility(View.GONE);

                        titleEdit.setText(task.getTitle());
                    }else{//Not editing, set to true
                        task.setEditing(true);
                        editBtn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.ic_undo_black_24dp));
                        doneBtn.setVisibility(View.VISIBLE);
                        title.setVisibility(View.GONE);
                        titleEdit.setVisibility(View.VISIBLE);
                    }
                }
            });

            //User is done with editing and wants to save
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    task.setTitle(titleEdit.getText().toString());
                    title.setText(task.getTitle());

                    task.setEditing(false);
                    editBtn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.ic_edit_black_24dp));
                    doneBtn.setVisibility(View.GONE);
                    title.setVisibility(View.VISIBLE);
                    titleEdit.setVisibility(View.GONE);

                    DatabaseHandler db = new DatabaseHandler(context);
                    db.updateTask(task);
                    db.closeDB();
                }
            });

            //User completed task
            completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    task.setCompleted(isChecked);
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.updateTask(task);
                    db.closeDB();
                }
            });



            //Sets the values of the elements
            time.setText(task.getTimeMade());
            date.setText(task.getDateMade());
            madeBy.setText(task.getMadeBy().getFullName());
            title.setText(task.getTitle());
            titleEdit.setText(task.getTitle());
            completed.setChecked(task.isCompleted());


        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return v;
    }


}
