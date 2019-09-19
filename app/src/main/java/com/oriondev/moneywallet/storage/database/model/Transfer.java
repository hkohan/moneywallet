package com.oriondev.moneywallet.storage.database.model;

public class Transfer extends BaseItem {

    public Long mId;
    public String mDescription;
    public String mDate;
    public Long mTransactionFrom;
    public Long mTransactionTo;
    public Long mTransactionTax;
    public String mNote;
    public Long mPlace;
    public Long mEvent;
    public Long mRecurrence;
    public boolean mConfirmed;
    public boolean mCountInTotal;
    public String mTag;
}