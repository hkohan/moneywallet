package com.oriondev.moneywallet.model;

import android.os.Parcel;
import android.os.Parcelable;



public class Place implements Parcelable {

    private final long mId;
    private final String mName;
    private final Icon mIcon;
    private final String mAddress;
    private final Double mLatitude;
    private final Double mLongitude;

    public Place(long id, String name, Icon icon, String address, Double latitude, Double longitude) {
        mId = id;
        mName = name;
        mIcon = icon;
        mAddress = address;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public Place(long id, String name, Icon icon, String address, Coordinates coordinates) {
        mId = id;
        mName = name;
        mIcon = icon;
        mAddress = address;
        mLatitude = coordinates != null ? coordinates.getLatitude() : null;
        mLongitude = coordinates != null ? coordinates.getLongitude() : null;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public boolean hasCoordinates() {
        return mLatitude != null && mLongitude != null;
    }

    public Coordinates getCoordinates() {
        return hasCoordinates() ? new Coordinates(mLatitude, mLongitude) : null;
    }

    protected Place(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mIcon = in.readParcelable(Icon.class.getClassLoader());
        mAddress = in.readString();
        if (in.readByte() == 0) {
            mLatitude = null;
        } else {
            mLatitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            mLongitude = null;
        } else {
            mLongitude = in.readDouble();
        }
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mName);
        dest.writeParcelable(mIcon, flags);
        dest.writeString(mAddress);
        if (mLatitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mLatitude);
        }
        if (mLongitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mLongitude);
        }
    }
}