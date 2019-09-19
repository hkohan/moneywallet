package com.oriondev.moneywallet.storage.database.model;


public class Wallet extends BaseItem {

    public Long mId;
    public String mName;
    public String mIcon;
    public String mCurrency;
    public long mStartMoney;
    public boolean mCountInTotal;
    public boolean mArchived;
    public String mNote;
    public String mTag;
    public int mIndex;
}