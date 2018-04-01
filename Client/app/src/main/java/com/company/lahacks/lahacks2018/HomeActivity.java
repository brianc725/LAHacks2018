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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private String[] mUrls = new String[6];
    private String lobbyName;
    private boolean isHost = false;
    private EditText lobby;

    public void updateImageUI() {
        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra("mUrl", mUrls);
        intent.putExtra("isHost", isHost);
        intent.putExtra("lobbyName", lobbyName);
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
        lobbyName = lobby.getText().toString();
        if(lobbyName.equals(""))
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
        isHost = true;
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.child("parties").getValue();
                if(map.containsKey(partyName)) {
                    Toast.makeText(HomeActivity.this, "Sorry that lobby already exists!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    myRef.child("parties").child(partyName).setValue(partyName);
                    myRef.child("parties").child(partyName).child("Final Restaurant").setValue("Pending");
                    generateImages(partyName);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void joinParty(View view){
        final String partyName = lobby.getText().toString();
        if (!getLobbyName()) {
            Toast.makeText(HomeActivity.this, "Lobby name cannot be empty!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.child("parties").getValue();
                if(map.containsKey(partyName)) {
                    ArrayList<String> urls = new ArrayList<String> ();
                    for(int i = 0; i < 6; i++) {
                        String l = Integer.toString(i);
                        String photo = (String) dataSnapshot.child("parties").child(partyName).child(l).getValue();
                        mUrls[i] = photo;
                        Log.d("abc", mUrls[i]);
                    }
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

    public void generateImages(final String s){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.child("parties").getValue();
                if(map.containsValue(s)) {
                    foodHelper((Map<String, Object>) dataSnapshot.getValue());
                    for(int i = 0; i < 6; i ++) {
                        String j = Integer.toString(i);
                        String l = mUrls[i];
                        String score = "Photo " + i + " score";
                        myRef.child("parties").child(s).child(j).setValue(l);
                        myRef.child("parties").child(s).child(score).setValue(0);
                    }
                    updateImageUI();
                }else {
                    return;
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void foodHelper(Map<String, Object> map){
        Map<String, String> foodUrls = (Map<String, String>) map.get("food");
        int length = foodUrls.size();

        Object[] keys = foodUrls.keySet().toArray();
        int numPics = 6;
        int size = 0;
        int[] chosenPics = new int[length];
        while (numPics > 0) {
            int randInt = (int) (Math.random() * length);
            boolean contains = false;
            for (int i = 0; i < size; i++) {
                if (chosenPics[i] == randInt)
                    contains = true;
            }
            if(contains)
                continue;
            chosenPics[6 - numPics] = randInt;
            mUrls[6 - numPics] = foodUrls.get((String) keys[randInt]);
            size++;
            numPics--;
        }
    }

    public void uploadImage(View view) {
        updateUploadUI();
    }

}
