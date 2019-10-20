package com.example.a300cemandroid;

import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class login_main extends AppCompatActivity {
    private EditText password;
    private TextView passwordValidation;
    private ProgressBar passwordStrengthBar;
    private TextView passwordStrengthText;

    private EditText email;
    private TextView emailValidation;

    private AppCompatCheckBox showToggleCheck;

    private Button forgotDetails;

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
        passwordStrengthBar = (ProgressBar) findViewById(R.id.passwordStrengthBar);
        passwordStrengthText = (TextView) findViewById(R.id.passwordStrengthText);

        showToggleCheck = (AppCompatCheckBox) findViewById(R.id.showToggleCheck);

        forgotDetails = (Button) findViewById(R.id.forgetDetails);

        setListeners();


    }

    private void setListeners(){
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String email_string = email.getText().toString();
                    if(emailValidator.isEmail(email_string)){
                        emailValidation.setVisibility(View.GONE);
                    }else{
                        emailValidation.setVisibility(View.VISIBLE);
                    }
                }
            }

        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String passwordString = password.getText().toString();
                    if(passValidator.passwordStrength(passwordString) > 1 || passwordString.length() == 0){
                        passwordValidation.setVisibility(View.GONE);
                    }else{
                        passwordValidation.setVisibility(View.VISIBLE);
                    }
                }
            }

        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String passwordString = password.getText().toString();

                if(passValidator.passwordStrength(passwordString) > 1 || start <= passValidator.getPasswordMinimumLength()){
                    passwordValidation.setVisibility(View.GONE);
                }else{
                    passwordValidation.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passText = s.toString();
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

            @Override
            public void afterTextChanged(Editable s) {}
        });

        showToggleCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                togglePasswordVisibility(buttonView, isChecked);
            }
        });

        forgotDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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



}
