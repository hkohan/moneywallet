package com.oriondev.moneywallet.model;

import com.oriondev.moneywallet.storage.preference.PreferenceManager;


public enum LockMode {

    NONE(PreferenceManager.LOCK_MODE_NONE),
    PIN(PreferenceManager.LOCK_MODE_PIN),
    SEQUENCE(PreferenceManager.LOCK_MODE_SEQUENCE),
    FINGERPRINT(PreferenceManager.LOCK_MODE_FINGERPRINT);

    private final int mValue;

    LockMode(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }

    public String getValueAsString() {
        return String.valueOf(mValue);
    }

    public static LockMode get(int value) {
        switch (value) {
            case PreferenceManager.LOCK_MODE_PIN:
                return PIN;
            case PreferenceManager.LOCK_MODE_SEQUENCE:
                return SEQUENCE;
            case PreferenceManager.LOCK_MODE_FINGERPRINT:
                return FINGERPRINT;
            default:
                return NONE;
        }
    }
}