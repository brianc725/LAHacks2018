package com.company.lahacks.lahacks2018;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class RestaurantsActivity extends AppCompatActivity {
    private FsqApp mFsqApp;
    private RecyclerView mRecyclerView;
    private ArrayList<FsqVenue> mVenueList = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public static final String CLIENT_ID = "YQMNRZM5OXBUQQPLZX5DN1P5ER1U3KVWDRDFLNDJKB2MSNAD";
    public static final String CLIENT_SECRET = "G3ZAUGE2CGIMNPT2BKSCZI4TA4VSX0YQP011WVIK00QJLFID";

    private String lobbyName;

    public String mQuery;
    public String mDistance;
    public double mLat;
    public double mLong;
    private boolean isLucky;

    private void setParams() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int what = 0;
        try {
            mQuery = extras.getString("keyWord");
            mDistance = extras.getString("distance");
            mLat = extras.getDouble("lat");
            mLong = extras.getDouble("lon");
            isLucky = extras.getBoolean("lucky");
            lobbyName = extras.getString("lobbyName");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        setParams();

        mFsqApp = new FsqApp(CLIENT_ID, CLIENT_SECRET, mQuery, mDistance);
        //mVenueList = new ArrayList<>();
        mVenueList.clear();

        Thread thread = new Thread() {
            @Override
            public void run() {
                int what = 0;
                try {
                    mVenueList = mFsqApp.getNearby(mLat, mLong);
                    FsqVenue f = mVenueList.get(0);
                } catch (Exception e) {
                    what = 1;
                    e.printStackTrace();
                }
                mHandler.sendMessage(mHandler.obtainMessage(what));
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mRecyclerView = findViewById(R.id.rv_restaurant_list);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);

        RestaurantsActivity.RVAdapter adapter = new RestaurantsActivity.RVAdapter(mVenueList);
        mRecyclerView.setAdapter(adapter);
    }


    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RestaurantHolder> {

        ArrayList<FsqVenue> venues;

        RVAdapter(ArrayList<FsqVenue> venues) {
            if (!isLucky) {
                this.venues = venues;
            } else {
                this.venues = new ArrayList<>();
                this.venues.add(venues.get(0));
            }
        }

        @Override
        public int getItemCount() {
            return venues.size();
        }

        @Override
        public RestaurantHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurant_item, viewGroup, false);
            RestaurantHolder rh = new RestaurantHolder(v);
            return rh;
        }

        @Override
        public void onBindViewHolder(RVAdapter.RestaurantHolder holder, int i) {
            FsqVenue temp = new FsqVenue();
            temp = venues.get(i);
            holder.roomname.setText(temp.name);
            holder.roomaddr.setText(temp.address);
            holder.roomcat.setText(temp.type);
        }

        /*
        @Override
        public void onAttachedToReyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }*/

        public class RestaurantHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView roomname;
            TextView roomaddr;
            TextView roomcat;

            RestaurantHolder(View itemView) {
                super(itemView);
                cv = (CardView) itemView.findViewById(R.id.cv_restaurantcard);
                roomname = (TextView) itemView.findViewById(R.id.tv_room_name);
                roomaddr = (TextView) itemView.findViewById(R.id.tv_room_addr);
                roomcat = (TextView) itemView.findViewById(R.id.tv_room_cat);
            }
        }

    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                if (mVenueList.size() == 0) {
                    Toast.makeText(RestaurantsActivity.this, "No venues that match query nearby", Toast.LENGTH_SHORT).show();
                    return false;
                }
                RecyclerView.generateViewId();
            } else {
                Toast.makeText(RestaurantsActivity.this, "Failed to load nearby venues", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    });

    public void finish(){
        myRef.child("parties").child(lobbyName).removeValue();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
