package com.oriondev.moneywallet.api;

public class BackendException extends Exception {

    private boolean mIsRecoverable;

    public BackendException(String message) {
        super(message);
        mIsRecoverable = false;
    }

    public BackendException(String message, boolean isRecoverable) {
        super(message);
        mIsRecoverable = isRecoverable;
    }

    public boolean isRecoverable() {
        return mIsRecoverable;
    }
}