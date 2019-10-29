package com.example.a300cemandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


public class login_main extends AppCompatActivity {
    private EditText password;
    private TextView passwordValidation;
    private ProgressBar passwordStrengthBar;
    private TextView passwordStrengthText;

    private EditText email;
    private TextView emailValidation;

    private AppCompatCheckBox showToggleCheck;

    private ProgressBar progressSpin;

    private Button forgotDetails;
    private Button loginBtn;

    private Button backBtn;

    private passwordValidation passValidator = new passwordValidation();
    private emailValidation emailValidator = new emailValidation();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_login_main);

        email = (EditText) findViewById(R.id.emailText);
        emailValidation = (TextView) findViewById(R.id.emailValidationText);

        password = (EditText) findViewById(R.id.password);
        passwordValidation = (TextView) findViewById(R.id.passwordValidationText);

        showToggleCheck = (AppCompatCheckBox) findViewById(R.id.showToggleCheck);

        forgotDetails = (Button) findViewById(R.id.forgetDetails);
        loginBtn = (Button) findViewById(R.id.login);
        backBtn = (Button) findViewById(R.id.backBtn);

        progressSpin = (ProgressBar) findViewById(R.id.progressAnim);

        setListeners();


    }

    private void setListeners(){


        showToggleCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                togglePasswordVisibility(buttonView, isChecked);
            }
        });

        forgotDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), forgotPassword.class);
                startActivity(i);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean emailV = emailValidationManager();
                boolean passwordV = passwordStrengthManager(password.getText().toString());




                if(emailV && passwordV){
                    login();
                }


            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void togglePasswordVisibility(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            // show password
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showToggleCheck.setText(getResources().getString(R.string.hidePassword));
        } else {
            // hide password
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showToggleCheck.setText(getResources().getString(R.string.showPassword));
        }
    }

    private void login(){

        inProgress();



    }

    private void inProgress(){
        progressBarAnimation animation = new progressBarAnimation(progressSpin, 1000, 2000);
        animation.setDuration(1000);
        progressSpin.startAnimation(animation);
        progressSpin.setVisibility(View.VISIBLE);

        email.setEnabled(false);
        password.setEnabled(false);
        loginBtn.setClickable(false);
        forgotDetails.setClickable(false);
        backBtn.setClickable(false);
    }

    private void stopProgress(){
        progressSpin.setVisibility(View.GONE);

        email.setEnabled(true);
        password.setEnabled(true);
        loginBtn.setClickable(true);
        forgotDetails.setClickable(true);
        backBtn.setClickable(true);
    }

    private Boolean emailValidationManager(){
        String email_string = email.getText().toString();
        if (emailValidator.isEmail(email_string)) {
            emailValidation.setVisibility(View.GONE);
            return true;
        } else {
            emailValidation.setVisibility(View.VISIBLE);
            return false;
        }
    }

    private boolean passwordStrengthManager(String passText){
        int strength = passValidator.passwordStrength(passText);


        if(strength >= 2){
            passwordValidation.setVisibility(View.GONE);
            return true;
        }
        passwordValidation.setVisibility(View.VISIBLE);
        return false;
    }

}
