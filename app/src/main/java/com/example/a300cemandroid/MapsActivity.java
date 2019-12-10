package com.example.a300cemandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Double longitude;
    private Double latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        if (null != intent) { //Null Checking
            Bundle extras = intent.getExtras();
            longitude = extras.getDouble("longitude");
            latitude = extras.getDouble("latitude");

        }

        if(longitude == null || latitude == null){
            longitude = 0.0;
            latitude = 0.0;
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {//When map is ready
        mMap = googleMap;

        LatLng house = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(house).title("Current house"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(house,15f));
    }


}
