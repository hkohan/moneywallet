package com.oriondev.moneywallet.model;

import android.os.Parcel;
import android.os.Parcelable;



public class Coordinates implements Parcelable {

    private final double mLatitude;
    private final double mLongitude;

    public Coordinates(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    @SuppressWarnings("WeakerAccess")
    protected Coordinates(Parcel in) {
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public static final Creator<Coordinates> CREATOR = new Creator<Coordinates>() {

        @Override
        public Coordinates createFromParcel(Parcel in) {
            return new Coordinates(in);
        }

        @Override
        public Coordinates[] newArray(int size) {
            return new Coordinates[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
    }
}