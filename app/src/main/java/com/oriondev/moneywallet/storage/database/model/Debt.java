package com.oriondev.moneywallet.storage.database.model;


public class Debt extends BaseItem {

    public Long mId;
    public int mType;
    public String mIcon;
    public String mDescription;
    public String mDate;
    public String mExpirationDate;
    public Long mWallet;
    public String mNote;
    public Long mPlace;
    public long mMoney;
    public boolean mArchived;
    public String mTag;
}