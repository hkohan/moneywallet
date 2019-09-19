package com.oriondev.moneywallet.model;

import com.oriondev.moneywallet.utils.CurrencyManager;


public class ExchangeRate {

    private final String mCurrency1;
    private final String mCurrency2;
    private final double mRate;
    private final long mTimestamp;

    public ExchangeRate(String currency1, String currency2, double rate, long timestamp) {
        mCurrency1 = currency1;
        mCurrency2 = currency2;
        mRate = rate;
        mTimestamp = timestamp;
    }

    public CurrencyUnit getCurrency1() {
        return CurrencyManager.getCurrency(mCurrency1);
    }

    public CurrencyUnit getCurrency2() {
        return CurrencyManager.getCurrency(mCurrency2);
    }

    public double getRate() {
        return mRate;
    }

    public long getTimestamp() {
        return mTimestamp;
    }
}