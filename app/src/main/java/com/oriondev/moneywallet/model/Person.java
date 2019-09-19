package com.oriondev.moneywallet.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Identifiable, Parcelable {

    private final long mId;
    private final String mName;
    private final Icon mIcon;

    public Person(long id, String name, Icon icon) {
        mId = id;
        mName = name;
        mIcon = icon;
    }

    protected Person(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mIcon = in.readParcelable(Icon.class.getClassLoader());
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public Icon getIcon() {
        return mIcon;
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
    }
}
