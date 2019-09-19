package com.oriondev.moneywallet.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class Event implements Identifiable, Parcelable {

    private final long mId;
    private final String mName;
    private final Icon mIcon;
    private final Date mStartDate;
    private final Date mEndDate;

    public Event(long id, String name, Icon icon, Date startDate, Date endDate) {
        mId = id;
        mName = name;
        mIcon = icon;
        mStartDate = startDate;
        mEndDate = endDate;
    }

    protected Event(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mIcon = in.readParcelable(Icon.class.getClassLoader());
        mStartDate = new Date(in.readLong());
        mEndDate = new Date(in.readLong());
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mName);
        dest.writeParcelable(mIcon, flags);
        dest.writeLong(mStartDate.getTime());
        dest.writeLong(mEndDate.getTime());
    }
}
