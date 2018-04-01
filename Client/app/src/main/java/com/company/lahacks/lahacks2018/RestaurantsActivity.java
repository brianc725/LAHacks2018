package com.company.lahacks.lahacks2018;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RestaurantsActivity extends AppCompatActivity {
    private FsqApp mFsqApp;
    private ArrayList<FsqVenue> mVenueList;

    public static final String CLIENT_ID = "YQMNRZM5OXBUQQPLZX5DN1P5ER1U3KVWDRDFLNDJKB2MSNAD";
    public static final String CLIENT_SECRET = "G3ZAUGE2CGIMNPT2BKSCZI4TA4VSX0YQP011WVIK00QJLFID";

    public String mQuery;
    public String mDistance;
    public double mLat;
    public double mLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        setParams();

        mFsqApp = new FsqApp(CLIENT_ID, CLIENT_SECRET, mQuery, mDistance);
        mVenueList = new ArrayList<>();

        loadNearby(mLat, mLong);
    }

    private void setParams() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int what = 0;
        try {
            mQuery = extras.getString("EXTRA_QUERY");
            mDistance = extras.getString("EXTRA_DISTANCE");
            mLat = extras.getDouble("EXTRA_LAT");
            mLong = extras.getDouble("EXTRA_LONG");
        } catch (Throwable e) {
            what = 1;
        }
        nHandler.sendMessage(nHandler.obtainMessage(what));
    }

    private Handler nHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                Toast.makeText(RestaurantsActivity.this, "Error when receiving variables to intent", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    });

    private void loadNearby(final double latitude, final double longitude) {

        new Thread() {
            @Override
            public void run() {
                int what = 0;
                try {
                    mVenueList = mFsqApp.getNearby(latitude, longitude);
                } catch (Exception e) {
                    what = 1;
                    e.printStackTrace();
                }
                mHandler.sendMessage(mHandler.obtainMessage(what));
            }
        }.start();
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                if (mVenueList.size() == 0) {
                    Toast.makeText(RestaurantsActivity.this, "No venues that match query nearby", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(RestaurantsActivity.this, "Failed to load nearby venues", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    });
}
