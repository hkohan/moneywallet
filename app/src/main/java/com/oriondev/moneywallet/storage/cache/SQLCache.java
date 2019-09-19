package com.oriondev.moneywallet.storage.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*package-local*/ class SQLCache extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cache.db";
    private static final int DATABASE_VERSION = 1;

    /*package-local*/ static final class ExchangeRateT {
        private static final String TABLE = "exchange_rates";
        /*package-local*/ static final String CURRENCY_ISO = "exchange_currency_iso";
        /*package-local*/ static final String RATE = "exchange_rate";
        /*package-local*/ static final String TIMESTAMP = "exchange_timestamp";
    }

    private static final String CREATE_TABLE_EXCHANGE_RATE = "CREATE TABLE " + ExchangeRateT.TABLE + " (" +
            ExchangeRateT.CURRENCY_ISO + " TEXT PRIMARY KEY, " +
            ExchangeRateT.RATE + " REAL NOT NULL, " +
            ExchangeRateT.TIMESTAMP + " INTEGER NOT NULL" +
            ")";

    /*package-local*/ SQLCache(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EXCHANGE_RATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing -> this is just a cache
    }

    /*package-local*/ Cursor getExchangeRates(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return getReadableDatabase().query(ExchangeRateT.TABLE, projection, selection, selectionArgs, null, null, sortOrder);
    }

    /*package-local*/ void insertOrUpdateExchangeRate(ContentValues contentValues) {
        getWritableDatabase().insertWithOnConflict(ExchangeRateT.TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }
}