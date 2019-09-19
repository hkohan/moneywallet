package com.oriondev.moneywallet.storage.database.model;


public class Budget extends BaseItem {

    public Long mId;
    public int mType;
    public Long mCategory;
    public String mStartDate;
    public String mEndDate;
    public long mMoney;
    public String mCurrency;
    public String mTag;
}