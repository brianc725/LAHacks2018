package com.company.lahacks.lahacks2018;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class UploadActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private Uri imageUri = null;
    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        imageView = findViewById(R.id.iv_userimage);

    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public void chooseImage(View view) {
        openGallery();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    public void uploadImage(View view) {
        if (imageUri == null) {
            Toast.makeText(UploadActivity.this, "Cannot upload without selecting image.",
                    Toast.LENGTH_SHORT).show();
        }


        //TODO: after uploading the image set imageURI back to null and clear imageview
    }


}
