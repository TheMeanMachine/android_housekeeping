package com.example.a300cemandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

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

    private Button backBtn;
    private Button regBtn;

    private ProgressBar progressSpin;

    private TextView title;

    private ImageView icon;

    private passwordValidation passValidator = new passwordValidation();
    private emailValidation emailValidator = new emailValidation();
    private nameValidation nameValidator = new nameValidation();


    private FirebaseAuth mAuth;
    private String TAG = "REG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_registration);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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

        backBtn = (Button) findViewById(R.id.backBtn);
        regBtn = (Button) findViewById(R.id.register);

        progressSpin = (ProgressBar) findViewById(R.id.progressAnim);

        icon = (ImageView) findViewById(R.id.houseIcon);

        title = (TextView) findViewById(R.id.title);
        setListeners();



    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Updates the UI based on the login procedure
     * @param currentUser
     */
    private void updateUI(final FirebaseUser currentUser, final User u) {
        if(currentUser != null && u != null){

            currentUser.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                @Override
                public void onSuccess(GetTokenResult result) {
                    String idToken = result.getToken();
                    //Do whatever
                    Log.d(TAG, "GetTokenResult result = " + idToken);
                    if(idToken != null){
                        auth authenticator = new auth(getApplicationContext());

                        u.setEmail(currentUser.getEmail());

                        authenticator.loginUserWithThird(u);
                    }

                }
            });

        }else if(u != null){

            auth authenticator = new auth(getApplicationContext());

            authenticator.loginUserWithThird(u);

        }else{
            stopProgress();
        }
    }

    /**
     * Registers an account (U&P) on firebase
     * @param cU - user Obj
     * @param password - Password of new user
     */
    private void createAccount(final User cU, String password){
        if(cU != null){
            mAuth.createUserWithEmailAndPassword(cU.getEmail(), password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
//                                User u = new User();
//                                u.setFirstName(cU.getFirstName());
//                                u.setLastName(cU.getLastName());
//                                u.setEmail(cU.getEmail());
//                                u.setImg(cU.getImg());
//                                u.setID(cU.getID());
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user, cU);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null, cU);
                            }

                            // ...
                        }
                    });

        }

    }

    /**
     * Sets listeners for elements
     */
    private void setListeners() {
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.RubberBand)
                        .duration(700)
                        .repeat(1)
                        .playOn(title);
            }
        });

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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean nameV = nameValidationManager();
                boolean emailV = emailValidationManager();
                String emailS = email.getText().toString();
                boolean passwordV = passwordStrengthManager(password.getText().toString());

                String passwordS =  password.getText().toString();
                String rePasswordS = rePassword.getText().toString();
                boolean isPasswordSame =passwordS.equals(rePasswordS);
                if(nameV && emailV && passwordV && isPasswordSame){
                    inProgress();
                    User u = new User();
                    u.setEmail(email.getText().toString());
                    u.setLastName(lastName.getText().toString());
                    u.setFirstName(firstName.getText().toString());
                    createAccount(u, passwordS);
                }else{
                    progressSpin.setVisibility(View.GONE);
                    Toast.makeText(registration.this, "You must enter valid information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Sets spinners in action, inputs disabled
     */
    private void inProgress(){
        progressBarAnimation animation = new progressBarAnimation(progressSpin, 1000, 2000);
        animation.setDuration(1000);
        progressSpin.startAnimation(animation);

        progressSpin.setVisibility(View.VISIBLE);

        email.setEnabled(false);
        firstName.setEnabled(false);
        lastName.setEnabled(false);
        password.setEnabled(false);
        rePassword.setEnabled(false);
        regBtn.setClickable(false);
    }

    /**
     * Hides spinner, allows input
     */
    private void stopProgress(){
        progressSpin.setVisibility(View.GONE);

        email.setEnabled(true);
        firstName.setEnabled(true);
        lastName.setEnabled(true);
        password.setEnabled(true);
        rePassword.setEnabled(true);
        regBtn.setClickable(true);
    }

    /**
     * Validates the first and last name fields on the form
     * @return True if valid names
     */
    private boolean nameValidationManager(){
        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();

        boolean nameValid = nameValidator.isFirstName(fName) && nameValidator.isLastName(lName);

        if(nameValid) {
            nameValidationText.setVisibility(View.GONE);
        } else {
            nameValidationText.setVisibility(View.VISIBLE);
        }
        return nameValid;
    }

    /**
     * Validates the email entry on the form
     * @return True if valid email
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
     * Validates the strength of the password on the form
     * @param passText - password to check
     * @return true if strong enough
     */
    private boolean passwordStrengthManager(String passText){
        int strength = passValidator.passwordStrength(passText);

        passwordStrengthBar.setProgress(strength);

        switch(strength){
            case 0:
                passwordStrengthText.setText("");
                return false;

            case 1:
                passwordStrengthText.setText(getResources().getString(R.string.passWeak));
                return false;
            case 2:
                passwordStrengthText.setText(getResources().getString(R.string.passOkay));
                return true;
            case 3:
                passwordStrengthText.setText(getResources().getString(R.string.passStrong));
                return true;
        }
        return false;
    }
}
