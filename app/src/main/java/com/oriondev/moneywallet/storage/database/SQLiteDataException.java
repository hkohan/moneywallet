package com.oriondev.moneywallet.storage.database;

import android.database.sqlite.SQLiteException;


public class SQLiteDataException extends SQLiteException {

    private final int mErrorCode;

    /*package-local*/ SQLiteDataException(int code) {
        mErrorCode = code;
    }

    /*package-local*/ SQLiteDataException(int code, String error) {
        super(error);
        mErrorCode = code;
    }

    public int getErrorCode() {
        return mErrorCode;
    }
}