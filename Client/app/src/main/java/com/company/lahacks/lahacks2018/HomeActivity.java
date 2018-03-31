package com.company.lahacks.lahacks2018;

import android.view.View;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    public void updateImageUI() {
        Intent intent = new Intent(this, ImageActivity.class);
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
    }

    public void createParty(View view) {
        updateImageUI();
    }

    public void joinParty(View view) {
        updateImageUI();
    }

    public void uploadImage(View view) {
        updateUploadUI();
    }
}
