package com.example.a300cemandroid;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class forgotPassword extends AppCompatActivity {
    private EditText email;
    private TextView emailValidation;
    private Button submitButton;
    private Button backBtn;

    private emailValidation emailValidator = new emailValidation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_forgot_password);

        email = (EditText) findViewById(R.id.emailText);
        emailValidation = (TextView) findViewById(R.id.emailValidationText);
        submitButton = (Button) findViewById(R.id.submit);
        backBtn = (Button) findViewById(R.id.backBtn);

        setListeners();
    }

    private void setListeners(){

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String email_string = s.toString();
                emailLogic(email_string);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email_string = s.toString();
                emailLogic(email_string);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void emailLogic(String emailString){
        if(emailValidator.isEmail(emailString)){
            emailValidation.setVisibility(View.GONE);
        }else if(emailString.length() > 1){
            emailValidation.setVisibility(View.VISIBLE);
        }
    }
}
