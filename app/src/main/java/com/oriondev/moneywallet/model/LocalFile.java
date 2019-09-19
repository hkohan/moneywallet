package com.oriondev.moneywallet.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class LocalFile implements IFile {

    private final File mFile;

    public LocalFile(File file) {
        mFile = file;
    }

    public LocalFile(String encoded) {
        mFile = new File(encoded);
    }

    private LocalFile(Parcel in) {
        mFile = new File(in.readString());
    }

    public static final Creator<LocalFile> CREATOR = new Creator<LocalFile>() {
        @Override
        public LocalFile createFromParcel(Parcel in) {
            return new LocalFile(in);
        }

        @Override
        public LocalFile[] newArray(int size) {
            return new LocalFile[size];
        }
    };

    @Override
    public String getName() {
        return mFile.getName();
    }

    @Override
    public String getExtension() {
        int index = mFile.getName().lastIndexOf(".");
        return index >= 0 ? mFile.getName().substring(index) : null;
    }

    @Override
    public boolean isDirectory() {
        return mFile.isDirectory();
    }

    @Override
    public long getSize() {
        return mFile.length();
    }

    @Override
    public String encodeToString() {
        return mFile.getPath();
    }

    public File getFile() {
        return mFile;
    }

    public String getLocalPath() {
        return mFile.getPath();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFile.getPath());
    }
}