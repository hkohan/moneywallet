package com.oriondev.moneywallet.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;


public class BackupService {

    private final String mIdentifier;
    private final int mIconRes;
    private final int mNameRes;

    public BackupService(String identifier, int icon, int name) {
        mIdentifier = identifier;
        mIconRes = icon;
        mNameRes = name;
    }

    public String getIdentifier() {
        return mIdentifier;
    }

    @DrawableRes
    public int getIconRes() {
        return mIconRes;
    }

    @StringRes
    public int getNameRes() {
        return mNameRes;
    }
}