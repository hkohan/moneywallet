package com.oriondev.moneywallet.storage.database.model;


public class Transaction extends BaseItem {

    public Long mId;
    public long mMoney;
    public String mDate;
    public String mDescription;
    public Long mCategory;
    public int mDirection;
    public int mType;
    public Long mWallet;
    public Long mPlace;
    public String mNote;
    public Long mSaving;
    public Long mDebt;
    public Long mEvent;
    public Long mRecurrence;
    public boolean mConfirmed;
    public boolean mCountInTotal;
    public String mTag;
}