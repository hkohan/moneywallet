package com.oriondev.moneywallet.model;

import android.os.Parcelable;

public interface IFile extends Parcelable {

    String getName();

    String getExtension();

    boolean isDirectory();

    long getSize();

    String encodeToString();
}