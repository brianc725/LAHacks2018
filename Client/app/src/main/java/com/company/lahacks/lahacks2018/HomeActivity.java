package com.company.lahacks.lahacks2018;

import android.util.Log;
import android.view.View;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private EditText lobby;
    private String[] mUrls = {
            "http://i.imgur.com/zuG2bGQ.jpg",
            "http://i.imgur.com/zuG2bGQ.jpg",
            "http://i.imgur.com/zuG2bGQ.jpg",
            "http://i.imgur.com/zuG2bGQ.jpg",
            "http://i.imgur.com/zuG2bGQ.jpg",
            "http://i.imgur.com/zuG2bGQ.jpg"
    };

    public void updateImageUI() {
        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra("mUrl", mUrls);
        startActivity(intent);
    }

    public void updateUploadUI() {
        Intent intent = new Intent(this, UploadActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        lobby = (EditText) findViewById(R.id.et_lobby);
    }

    public boolean getLobbyName(){
        String partyName = lobby.getText().toString();
        if(partyName.equals(""))
            return false;
        return true;
    }

    public void createParty(View view) {

        if (!getLobbyName()) {
            Toast.makeText(HomeActivity.this, "Lobby name cannot be empty!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        
        final String partyName = lobby.getText().toString();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                if(map.containsValue(partyName)) {
                    Toast.makeText(HomeActivity.this, "Sorry, that lobby already exists!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    myRef.child(partyName).setValue(partyName);
                    updateImageUI();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void joinParty(View view){
        final String partyName = lobby.getText().toString();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                if(map.containsValue(partyName)) {
                    updateImageUI();
                }else {
                    Toast.makeText(HomeActivity.this, "That lobby doesn't exist!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void uploadImage(View view) {
        updateUploadUI();
    }

}
