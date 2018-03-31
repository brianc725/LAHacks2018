package com.company.lahacks.lahacks2018;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GalleryActivity extends AppCompatActivity {

    private String[] mUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        mUrls = extras.getStringArray("mUrls");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_images);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        GalleryActivity.ImageGalleryAdapter adapter = new GalleryActivity.ImageGalleryAdapter(this);
        recyclerView.setAdapter(adapter);

    }

    private class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>  {

        @Override
        public ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the layout
            View photoView = inflater.inflate(R.layout.item_photo, parent, false);

            ImageGalleryAdapter.MyViewHolder viewHolder = new ImageGalleryAdapter.MyViewHolder(photoView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ImageGalleryAdapter.MyViewHolder holder, int position) {

            MyPhoto mPhoto = mPhotos[position];
            ImageView imageView = holder.mPhotoImageView;

            Glide.with(mContext)
                    .load(mPhoto.getUrl())
//                    .placeholder(R.drawable.ic_cloud_off_red)
                    .into(imageView);
        }

        @Override
        public int getItemCount() {
            return (mPhotos.length);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView mPhotoImageView;

            public MyViewHolder(View itemView) {

                super(itemView);
                mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    MyPhoto mPhoto = mPhotos[position];

                    Glide.with(mContext)
                            .load("https://upload.wikimedia.org/wikipedia/en/c/c7/Bonobos_Lana_%26_Kesi_2006_CALVIN_IMG_1301.JPG")
//                    .placeholder(R.drawable.ic_cloud_off_red)
                            .into(mPhotoImageView);
                    //later if time create PhotoActivity
//                    Intent intent = new Intent(mContext, PhotoActivity.class);
//                    intent.putExtra(PhotoActivity.EXTRA_SPACE_PHOTO, mPhoto);
//                    startActivity(intent);
                }
            }
        }

        private MyPhoto[] mPhotos = new MyPhoto[6];
        private Context mContext;

        public ImageGalleryAdapter(Context context) {
            mContext = context;

            for (int i = 0; i < 6; i++) {
                mPhotos[i] = new MyPhoto(mUrls[i]);
            }
        }
    }
}
