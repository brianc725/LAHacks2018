package com.company.lahacks.lahacks2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private EditText lobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        lobby = (EditText) findViewById(R.id.et_lobby);
    }

    public void addParty(View view) {
        if(!getLobbyName()){
            Toast.makeText(HomeActivity.this, "Lobby name cannot be empty!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        String partyName = lobby.getText().toString();
        myRef.child(partyName).setValue(partyName);
    }

    public boolean getLobbyName(){
        String partyName = lobby.getText().toString();
        if(partyName.equals(""))
            return false;
        return true;
    }

}
