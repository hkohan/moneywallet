package com.oriondev.moneywallet.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.oriondev.moneywallet.model.IFile;

public abstract class AbstractBackendServiceDelegate {

    private final BackendServiceStatusListener mListener;

    public AbstractBackendServiceDelegate(BackendServiceStatusListener listener) {
        mListener = listener;
    }

    public abstract String getId();

    @StringRes
    public abstract int getName();

    @StringRes
    public abstract int getBackupCoverMessage();

    @StringRes
    public abstract int getBackupCoverAction();

    public abstract boolean isServiceEnabled(Context context);

    public abstract void setup(Activity activity) throws BackendException;

    public abstract void teardown(Activity activity) throws BackendException;

    public boolean handlePermissionsResult(Context context, int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        return false;
    }

    public boolean handleActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        return false;
    }

    protected void setBackendServiceEnabled(boolean enabled) {
        if (mListener != null) {
            mListener.onBackendStatusChange(enabled);
        }
    }

    public interface BackendServiceStatusListener {

        void onBackendStatusChange(boolean enabled);
    }
}