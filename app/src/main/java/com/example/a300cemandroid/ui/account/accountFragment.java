package com.example.a300cemandroid.ui.account;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a300cemandroid.R;

public class accountFragment extends Fragment {

    private accountViewModel accountViewModel;

    private  View view;

    private TextView firstName;
    private TextView lastName;
    private TextView email;

    private ImageView userImg;

    private Button newPhoto;
    private Button logout;
    private Button deleteAccount;

    private static final int pic_id = 123;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(accountViewModel.class);
        view = inflater.inflate(R.layout.fragment_account, container, false);

        firstName = (TextView) view.findViewById(R.id.firstNameVal);
        lastName = (TextView) view.findViewById(R.id.lastNameVal);
        email = (TextView) view.findViewById(R.id.emailVal);
        userImg = (ImageView) view.findViewById(R.id.userImg);
        newPhoto = (Button) view.findViewById(R.id.newPhotoBtn);
        logout = (Button) view.findViewById(R.id.logoutBtn);
        deleteAccount = (Button) view.findViewById(R.id.deleteAccount);

        setListeners();

        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    private void setObservers(){

    }

    private void setListeners(){
        newPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });
    }

    private void startCamera(){
        Context context = getActivity();
        PackageManager packageManager = context.getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false) {
            Toast.makeText(getActivity(), "This device does not have a camera.", Toast.LENGTH_SHORT)
                    .show();
            return;
        } else {
            Intent camera_intent
                    = new Intent(MediaStore
                    .ACTION_IMAGE_CAPTURE);

            // Start the activity with camera_intent,
            // and request pic id
            startActivityForResult(camera_intent, pic_id);
        }
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data)
    {

        // Match the request 'pic id with requestCode
        if (requestCode == pic_id) {

            // BitMap is data structure of image file
            // which stor the image in memory
            userImg.setImageBitmap( (Bitmap)data.getExtras()
                    .get("data"));

            // Set the image in imageview for display

        }
    }

}