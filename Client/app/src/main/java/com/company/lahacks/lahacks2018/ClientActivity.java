package com.company.lahacks.lahacks2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ClientActivity extends AppCompatActivity {

    private String lobbyName;
    private TextView finalname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        Bundle extras = getIntent().getExtras();
        lobbyName = extras.getString("lobbyName");

        lobbyName = "banana"; //TODO: REMOVE THIS, THIS IS TEMPORARY FOR TESTING

        finalname = (TextView) findViewById(R.id.tv_final_rest);
        
    }
}
