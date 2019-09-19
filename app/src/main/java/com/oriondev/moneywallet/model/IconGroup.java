package com.oriondev.moneywallet.model;

import java.util.List;


public class IconGroup {

    private final String mGroupName;
    private final List<Icon> mGroupIcons;

    public IconGroup(String name, List<Icon> iconList) {
        mGroupName = name;
        mGroupIcons = iconList;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public List<Icon> getGroupIcons() {
        return mGroupIcons;
    }

    public int size() {
        return mGroupIcons.size();
    }
}