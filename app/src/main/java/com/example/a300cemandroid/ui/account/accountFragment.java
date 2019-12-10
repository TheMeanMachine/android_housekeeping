package com.example.a300cemandroid.ui.account;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a300cemandroid.DatabaseHandler;
import com.example.a300cemandroid.MainActivity;
import com.example.a300cemandroid.R;
import com.example.a300cemandroid.appViewModel;
import com.example.a300cemandroid.mainScreenController;

public class accountFragment extends Fragment {

    private accountViewModel accountVM;

    private  View view;

    private TextView firstName;
    private TextView lastName;
    private TextView email;

    private ImageView userImg;

    private Button newPhoto;
    private Button logout;
    private Button deleteAccount;

    private appViewModel appVM = appViewModel.getInstance();

    private static final int pic_id = 123;

    private accountViewModel viewModel = accountViewModel.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountVM =
                ViewModelProviders.of(this).get(accountViewModel.class);
        view = inflater.inflate(R.layout.fragment_account, container, false);

        firstName = (TextView) view.findViewById(R.id.firstNameVal);
        lastName = (TextView) view.findViewById(R.id.lastNameVal);
        email = (TextView) view.findViewById(R.id.emailVal);
        userImg = (ImageView) view.findViewById(R.id.userImg);
        newPhoto = (Button) view.findViewById(R.id.newPhotoBtn);
        logout = (Button) view.findViewById(R.id.logoutBtn);
        deleteAccount = (Button) view.findViewById(R.id.deleteAccount);

        deleteAccount.setVisibility(View.GONE);

        setListeners();
        setObservers();
        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    /**
     * Sets observers of the view model
     */
    private void setObservers(){
        viewModel.getUsrImg().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap bitmap) {
                userImg.setImageBitmap(bitmap);
            }
        });

        viewModel.getFirstName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                firstName.setText(s);
            }
        });

        viewModel.getLastName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                lastName.setText(s);
            }
        });

        viewModel.getEmail().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                email.setText(s);
            }
        });
    }

    /**
     * Sets listeners of the elements
     */
    private void setListeners(){
        newPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * Logs the user out of the application, calls resets and sends back to login screen
     */
    private void logout(){
        Intent login = new Intent(getActivity(), MainActivity.class);
        mainScreenController.getInstance().resetInstances();
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login);


    }

    /**
     * Starts the camera for avatar picture
     */
    private void startCamera(){
        Context context = getActivity();
        PackageManager packageManager = context.getPackageManager();
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(getActivity(), "This device does not have a camera.", Toast.LENGTH_SHORT)
                    .show();

        } else {
            Intent camera_intent
                    = new Intent(MediaStore
                    .ACTION_IMAGE_CAPTURE);

            // Start the activity with camera_intent,
            // and request pic id
            startActivityForResult(camera_intent, pic_id);
        }
    }

    /**
     * On camera return
     * @param requestCode Code of the inital request
     * @param resultCode Result code (Okay, bad)
     * @param data Data of the request return
     */
    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data)
    {

        // Match the request 'pic id with requestCode
        if (requestCode == pic_id) {
            Bitmap usrImg = (Bitmap) data.getExtras().get("data");
            viewModel.setUsrImg( usrImg );
            DatabaseHandler db = new DatabaseHandler(getContext());
            db.updateUser(viewModel.getCurrentUser());



        }
    }

}