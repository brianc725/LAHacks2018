package com.company.lahacks.lahacks2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;


public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Button showGalleryBtn = (Button) findViewById(R.id.btn_show_gallery);
        showGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(ImageActivity.this, GalleryActivity.class);
                galleryIntent.putExtra("mUrls",getIntent().getExtras().getStringArray("mUrl"));
                galleryIntent.putExtra("isHost",getIntent().getExtras().getBoolean("isHost"));
                galleryIntent.putExtra("lobbyName",getIntent().getExtras().getString("lobbyName"));
                startActivity(galleryIntent);
            }
        });
    }
}
