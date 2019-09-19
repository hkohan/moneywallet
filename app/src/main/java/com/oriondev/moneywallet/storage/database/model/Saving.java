package com.oriondev.moneywallet.storage.database.model;


public class Saving extends BaseItem {

    public Long mId;
    public String mDescription;
    public String mIcon;
    public long mStartMoney;
    public long mEndMoney;
    public Long mWallet;
    public String mEndDate;
    public boolean mComplete;
    public String mNote;
    public String mTag;
}