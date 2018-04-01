package com.company.lahacks.lahacks2018;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class HostActivity extends AppCompatActivity {

    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationClient;

    private String lobbyName;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private static String keyWord;
    private double lat;
    private double lon;
    private int currDistance = 0;


    private String crash;

    SeekBar distanceSeekBar;
    Switch luckySwitch;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        lobbyName = extras.getString("lobbyName");

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
                        } else {
                            lat = 34.0635016;
                            lon = -118.44551639999997;
                        }
                    }
                });

        //Set the maximum distance seekbar
        distanceSeekBar = (SeekBar) findViewById(R.id.sb_distance);
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
                currDistance = progressChangedValue;
            }
        });

        //Set the switch
        luckySwitch = (Switch) findViewById(R.id.s_switch);
    }


    public void closeParty(final View view) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int curr = 0;
                int highest = 0;
                for (int i = 0; i < 6; i++) {
                    String j = "Photo " + i + " score";
                    int value = ((Long) dataSnapshot.child("parties").child(lobbyName).child(j).getValue()).intValue();
                    if (value > curr) {
                        curr = value;
                        highest = i;
                    }
                }
                String highestVal = Integer.toString(highest);
                String decision = (String) dataSnapshot.child("parties").child(lobbyName).child(highestVal).getValue();
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.child("food").getValue();

                Object[] keyValues = map.keySet().toArray();

                for (int i = 0; i < keyValues.length; i++) {
                    if (map.get(keyValues[i]).equals(decision)) {
                        keyWord = (String) keyValues[i];
                        break;
                    }
                }

                currDistance = currDistance * 1000;
                String distance = Integer.toString(currDistance);

                Intent intent = new Intent(view.getContext(), RestaurantsActivity.class);
                intent.putExtra("keyWord", keyWord);
                intent.putExtra("lat", 34.0635);
                intent.putExtra("lon", -118.4455);
                intent.putExtra("distance", distance);
                intent.putExtra("lucky", luckySwitch.isChecked());
                startActivity(intent);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

