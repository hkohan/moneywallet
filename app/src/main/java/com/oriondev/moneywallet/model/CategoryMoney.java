package com.oriondev.moneywallet.model;


public class CategoryMoney {

    private final long mId;
    private final String mName;
    private final Icon mIcon;
    private final Money mMoney;

    public CategoryMoney(long id, String name, Icon icon, Money money) {
        mId = id;
        mName = name;
        mIcon = icon;
        mMoney = money;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public Icon getIcon() {
        return mIcon;
    }

    public Money getMoney() {
        return mMoney;
    }
}