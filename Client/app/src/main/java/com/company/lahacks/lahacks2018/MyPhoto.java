package com.company.lahacks.lahacks2018;

import android.os.Parcel;
import android.os.Parcelable;

public class MyPhoto implements Parcelable {

    private String mUrl;
    private String mTitle;

    public MyPhoto(String url, String title) {
        mUrl = url;
        mTitle = title;
    }

    protected MyPhoto(Parcel in) {
        mUrl = in.readString();
        mTitle = in.readString();
    }

    public static final Creator<MyPhoto> CREATOR = new Creator<MyPhoto>() {
        @Override
        public MyPhoto createFromParcel(Parcel in) {
            return new MyPhoto(in);
        }

        @Override
        public MyPhoto[] newArray(int size) {
            return new MyPhoto[size];
        }
    };

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public static  MyPhoto[] getPhotos() {

        return new MyPhoto[]{
                new MyPhoto("http://i.imgur.com/zuG2bGQ.jpg", "Galaxy"),
                new MyPhoto("http://i.imgur.com/ovr0NAF.jpg", "Space Shuttle"),
                new MyPhoto("http://i.imgur.com/n6RfJX2.jpg", "Galaxy Orion"),
                new MyPhoto("http://i.imgur.com/qpr5LR2.jpg", "Earth"),
                new MyPhoto("http://i.imgur.com/pSHXfu5.jpg", "Astronaut"),
                new MyPhoto("http://i.imgur.com/3wQcZeY.jpg", "Satellite"),
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUrl);
        parcel.writeString(mTitle);
    }
}
