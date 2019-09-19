package com.oriondev.moneywallet.ui.activity.base;

import android.content.Intent;

import com.oriondev.moneywallet.model.LockMode;
import com.oriondev.moneywallet.storage.preference.PreferenceManager;
import com.oriondev.moneywallet.ui.activity.LockActivity;


public abstract class BaseActivity extends ThemedActivity {

    private static final int REQUEST_CODE_LOCK_ACTIVITY = 20;

    private static final long MAX_LOCK_TIME_INTERVAL = 1000;

    boolean mActivityLocked = true;
    boolean mActivityLockEnabled = true;
    boolean mActivityResultOk = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (PreferenceManager.getCurrentLockMode() != LockMode.NONE) {
            if ((System.currentTimeMillis() - PreferenceManager.getLastLockTime()) > MAX_LOCK_TIME_INTERVAL) {
                mActivityLocked = true;
                if (mActivityResultOk) {
                    Intent intent = new Intent(this, LockActivity.class);
                    startActivityForResult(new Intent(this, LockActivity.class), REQUEST_CODE_LOCK_ACTIVITY);
                } else {
                    mActivityResultOk = true;
                }
            } else {
                mActivityLocked = false;
            }
            mActivityLockEnabled = true;
        } else {
            mActivityLocked = false;
            mActivityLockEnabled = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_LOCK_ACTIVITY) {
            if (resultCode != RESULT_OK) {
                moveTaskToBack(true);
                mActivityResultOk = false;
            } else {
                mActivityResultOk = true;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mActivityLockEnabled && !mActivityLocked) {
            PreferenceManager.setLastLockTime(System.currentTimeMillis());
        }
    }
}