package com.example.a300cemandroid;

import android.support.annotation.StringRes;
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

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login_main extends AppCompatActivity {
    private EditText password;
    private TextView passwordValidation;
    private ProgressBar passwordStrengthBar;
    private TextView passwordStrengthText;

    private EditText email;
    private TextView emailValidation;

    private AppCompatCheckBox showToggleCheck;

    private Button forgotDetails;


    private static int passwordMinimumLength = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    if(isEmail(email_string)){
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
                    if(passwordStrength(passwordString) > 1 || passwordString.length() == 0){
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

                if(passwordStrength(passwordString) > 1 || start <= passwordMinimumLength){
                    passwordValidation.setVisibility(View.GONE);
                }else{
                    passwordValidation.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passText = s.toString();
                int strength = passwordStrength(passText);

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

    public static int passwordStrength(String password){
        int len = password.length();
        Boolean strong = (len >= 12);
        Boolean okay = (len >= passwordMinimumLength && len < 12);
        Boolean weak = (len > 0 && len < passwordMinimumLength);
        Boolean hasSymbol = containsSymbol(password);

        if((weak || okay || strong) && !hasSymbol) {
            return 1;
        }else if(okay && hasSymbol){
            return 2;
        }else if(strong && hasSymbol){
            return 3;
        }

        return 0;
    }

    public static boolean containsSymbol(String target){
        String expression = "[$&+,:;=?@#|'<>.^*()%!-]";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static boolean isEmail(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
