package com.oriondev.moneywallet.storage.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.oriondev.moneywallet.model.ExchangeRate;

import java.util.HashMap;
import java.util.Map;


public class ExchangeRateCache {

    private final Map<String, CacheObj> mCacheMemory;
    private final SQLCache mCacheStorage;

    public ExchangeRateCache(Context context) {
        mCacheMemory = new HashMap<>();
        mCacheStorage = new SQLCache(context);
        loadCacheInMemory();
    }

    private void loadCacheInMemory() {
        String[] projection = new String[] {
                SQLCache.ExchangeRateT.CURRENCY_ISO,
                SQLCache.ExchangeRateT.RATE,
                SQLCache.ExchangeRateT.TIMESTAMP
        };
        Cursor cursor = mCacheStorage.getExchangeRates(projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()){
                CacheObj cacheObj = new CacheObj();
                cacheObj.mCurrency = cursor.getString(cursor.getColumnIndex(SQLCache.ExchangeRateT.CURRENCY_ISO));
                cacheObj.mRate = cursor.getDouble(cursor.getColumnIndex(SQLCache.ExchangeRateT.RATE));
                cacheObj.mTimestamp = cursor.getLong(cursor.getColumnIndex(SQLCache.ExchangeRateT.TIMESTAMP));
                mCacheMemory.put(cacheObj.mCurrency, cacheObj);
            }
            cursor.close();
        }
    }

    public void setExchangeRate(String currency, double rate, long timestamp) {
        CacheObj cacheObj = new CacheObj();
        cacheObj.mCurrency = currency;
        cacheObj.mRate = rate;
        cacheObj.mTimestamp = timestamp;
        mCacheMemory.put(currency, cacheObj);
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLCache.ExchangeRateT.CURRENCY_ISO, currency);
        contentValues.put(SQLCache.ExchangeRateT.RATE, rate);
        contentValues.put(SQLCache.ExchangeRateT.TIMESTAMP, timestamp);
        mCacheStorage.insertOrUpdateExchangeRate(contentValues);
    }

    public ExchangeRate getExchangeRate(String currency1, String currency2) {
        if (TextUtils.equals(currency1, currency2)) {
            return new ExchangeRate(currency1, currency2, 1, System.currentTimeMillis());
        }
        CacheObj rate1 = mCacheMemory.get(currency1);
        CacheObj rate2 = mCacheMemory.get(currency2);
        if (rate1 != null && rate2 != null) {
            double rate = (1d / rate1.mRate) * rate2.mRate;
            long timestamp = Math.min(rate1.mTimestamp, rate2.mTimestamp);
            return new ExchangeRate(currency1, currency2, rate, timestamp);
        }
        return null;
    }

    private class CacheObj {

        private String mCurrency;
        private double mRate;
        private long mTimestamp;
    }
}