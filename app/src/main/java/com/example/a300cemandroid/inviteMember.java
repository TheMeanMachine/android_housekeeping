package com.example.a300cemandroid;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

public class inviteMember extends AppCompatActivity {

    private Spinner usersDrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_invite_member);



        usersDrop = (Spinner) findViewById(R.id.usersDropdown);
    }


}
