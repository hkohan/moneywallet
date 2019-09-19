package com.oriondev.moneywallet.model;


public enum Group {

    DAILY(0), WEEKLY(1), MONTHLY(2), YEARLY(3);

    private final int mValue;

    Group(int value) {
        mValue = value;
    }

    public int getType() {
        return mValue;
    }

    public static Group fromType(int type) {
        switch (type) {
            case 0:
                return DAILY;
            case 1:
                return WEEKLY;
            case 2:
                return MONTHLY;
            case 3:
                return YEARLY;
        }
        throw new IllegalArgumentException("Group type not recognized: " + String.valueOf(type));
    }
}