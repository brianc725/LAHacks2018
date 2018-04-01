package com.company.lahacks.lahacks2018;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class HostActivity extends AppCompatActivity {

    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationClient;

    private double lat;
    private double lon;

    SeekBar distanceSeekBar;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        //Find the longitude and lattitude (not sure if it works)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                });

        //Set the maximum distance seekbar
        distanceSeekBar = (SeekBar)findViewById(R.id.sb_distance);
        distanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                //TODO
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(HostActivity.this, "Max distance is:" + progressChangedValue + "km.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void closeParty(View view) {
        //close the party and push the lon, lat, max distance, and other options
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}

