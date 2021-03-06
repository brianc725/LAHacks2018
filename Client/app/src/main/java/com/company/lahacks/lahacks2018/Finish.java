package com.company.lahacks.lahacks2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Finish extends AppCompatActivity {

    private String lobbyName;
    private TextView finalname;
    private String restaurant;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        Bundle extras = getIntent().getExtras(); //TODO: FIX THIS ONCE LOBBY  NAME IS PASSED IN
        lobbyName = extras.getString("lobbyName");

        finalname = (TextView) findViewById(R.id.tv_final_rest);

    }

    @Override
    protected void onResume() {
        super.onResume();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object tempVal = dataSnapshot.child("parties").child(lobbyName).child("Final Restaurant").getValue();
                if(tempVal != null) {
                    restaurant = tempVal.toString();
                } else {
                    restaurant = "";
                }
                finalname.setText(restaurant);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void finish(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        myRef.child("parties").child(lobbyName).removeValue();
        startActivity(intent);
    }
}
