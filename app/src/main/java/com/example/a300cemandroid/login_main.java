package com.example.a300cemandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


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

    private FirebaseAuth mAuth;
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



        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        setListeners();


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /**
     * Updates the UI based on the result of attempted login
     * If unsuccessful, will stop progress bars and return to normal,
     * If successful, will send details to auth class to init the app
     * @param currentUser - FirebaseUser credientials
     */
    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null){
            stopProgress();
            auth authenticator = new auth(getApplicationContext());
            User u = new User();
            u.setEmail(currentUser.getEmail());
            u.setFirstName(u.getFirstName());
            u.setLastName(u.getLastName());
            authenticator.loginUserWithThird(u);
        }else{
            stopProgress();
        }
    }


    /**
     * Sets the listeners for the elements
     */
    private void setListeners(){
        showToggleCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                togglePasswordVisibility(isChecked);
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

    /**
     * Toggles the 'show password' option
     * @param isChecked - if true, will set password visble
     */
    public void togglePasswordVisibility(boolean isChecked) {
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

    /**
     * Handles the login procedures
     */
    private void login(){

        inProgress();
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("login", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("login", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });


    }

    /**
     * Sets spinners and stops input
     */
    private void inProgress(){
        progressBarAnimation animation = new progressBarAnimation(progressSpin, 1000, 2000);
        animation.setDuration(1000);
        progressSpin.startAnimation(animation);
        progressSpin.setVisibility(View.VISIBLE);

        email.setEnabled(false);
        password.setEnabled(false);
        loginBtn.setClickable(false);
        forgotDetails.setClickable(false);

    }

    /**
     * Stops spinners and allows input
     */
    private void stopProgress(){
        progressSpin.setVisibility(View.GONE);

        email.setEnabled(true);
        password.setEnabled(true);
        loginBtn.setClickable(true);
        forgotDetails.setClickable(true);

    }

    /**
     * Handles email validation text
     * @return true if email okay
     */
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

    /**
     * Handles password strength management text
     * @param passText - password to check
     * @return true if strength is okay
     */
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
