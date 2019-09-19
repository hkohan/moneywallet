package com.oriondev.moneywallet.storage.database.model;


public class TransferModel extends BaseItem {

    public Long mId;
    public String mDescription;
    public Long mFromWallet;
    public Long mToWallet;
    public long mFromMoney;
    public long mToMoney;
    public long mTaxMoney;
    public String mNote;
    public Long mEvent;
    public Long mPlace;
    public boolean mConfirmed;
    public boolean mCountInTotal;
    public String mTag;
}