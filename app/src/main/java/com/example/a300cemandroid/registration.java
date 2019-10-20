package com.example.a300cemandroid;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class registration extends AppCompatActivity {
    private EditText password;
    private TextView passwordValidation;
    private ProgressBar passwordStrengthBar;
    private TextView passwordStrengthText;

    private EditText rePassword;
    private TextView rePasswordValidationText;

    private EditText email;
    private TextView emailValidation;

    private EditText firstName;
    private EditText lastName;
    private TextView nameValidationText;

    private passwordValidation passValidator = new passwordValidation();
    private emailValidation emailValidator = new emailValidation();
    private nameValidation nameValidator = new nameValidation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_registration);


        email = (EditText) findViewById(R.id.emailText);
        emailValidation = (TextView) findViewById(R.id.emailValidationText);

        password = (EditText) findViewById(R.id.password);
        passwordValidation = (TextView) findViewById(R.id.passwordValidationText);
        passwordStrengthBar = (ProgressBar) findViewById(R.id.passwordStrengthBar);
        passwordStrengthText = (TextView) findViewById(R.id.passwordStrengthText);

        rePassword = (EditText) findViewById(R.id.passwordRetype);
        rePasswordValidationText = (TextView) findViewById(R.id.reTypePasswordValidationText);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        nameValidationText = (TextView) findViewById(R.id.nameValidationText);

        setListeners();
    }


    private void setListeners() {
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    emailValidationManager();
                }
            }

        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(start > 2){
                    emailValidationManager();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String passwordString = password.getText().toString();
                    if (passValidator.passwordStrength(passwordString) > 1 || passwordString.length() == 0) {
                        passwordValidation.setVisibility(View.GONE);
                    } else {
                        passwordValidation.setVisibility(View.VISIBLE);
                    }
                }
            }

        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String passwordString = password.getText().toString();

                if (passValidator.passwordStrength(passwordString) > 1 || start <= passValidator.getPasswordMinimumLength()) {
                    passwordValidation.setVisibility(View.GONE);
                } else {
                    passwordValidation.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passText = s.toString();
                passwordStrengthManager(passText);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        rePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass = password.getText().toString();
                String rePass = s.toString();
                int passLen = pass.length();
                boolean isPasswordSame = pass.equals(rePass);
                if(isPasswordSame || passLen/2 > start){
                    rePasswordValidationText.setVisibility(View.GONE);
                }else{
                    rePasswordValidationText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                nameValidationManager();


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameValidationManager();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void nameValidationManager(){
        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();

        boolean nameValid = nameValidator.isFirstName(fName) && nameValidator.isLastName(lName);

        if(nameValid) {
            nameValidationText.setVisibility(View.GONE);
        } else {
            nameValidationText.setVisibility(View.VISIBLE);
        }
    }

    private void emailValidationManager(){
        String email_string = email.getText().toString();
        if (emailValidator.isEmail(email_string)) {
            emailValidation.setVisibility(View.GONE);
        } else {
            emailValidation.setVisibility(View.VISIBLE);
        }
    }

    private void passwordStrengthManager(String passText){
        int strength = passValidator.passwordStrength(passText);

        passwordStrengthBar.setProgress(strength);

        switch(strength){
            case 0:
                passwordStrengthText.setText("");
                break;
            case 1:
                passwordStrengthText.setText(getResources().getString(R.string.passWeak));
                break;
            case 2:
                passwordStrengthText.setText(getResources().getString(R.string.passOkay));
                break;
            case 3:
                passwordStrengthText.setText(getResources().getString(R.string.passStrong));
                break;
        }

    }
}
