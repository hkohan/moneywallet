package com.oriondev.moneywallet.storage.database.model;

public class TransactionModel extends BaseItem {

    public Long mId;
    public long mMoney;
    public String mDescription;
    public Long mCategory;
    public int mDirection;
    public Long mWallet;
    public Long mPlace;
    public String mNote;
    public Long mEvent;
    public boolean mConfirmed;
    public boolean mCountInTotal;
    public String mTag;
}