package com.oriondev.moneywallet.storage.database.model;


public class Category extends BaseItem {

    public Long mId;
    public String mName;
    public String mIcon;
    public int mType;
    public Long mParent;
    public String mTag;
    public boolean mShowReport;
    public int mIndex;
}