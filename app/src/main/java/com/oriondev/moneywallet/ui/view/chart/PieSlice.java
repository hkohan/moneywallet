package com.oriondev.moneywallet.ui.view.chart;

import android.graphics.drawable.Drawable;

public class PieSlice {

    private String mName;
    private long mValue;
    private Drawable mIcon;
    private boolean mIsExtra = false;

    private float mStartAngle = 0;
    private float mSweepAngle = 0;
    private float mIconAngle = 0;

    public PieSlice(String name, long value, Drawable icon) {
        this(name, value, icon, false);
    }

    public PieSlice(String name, long value, Drawable icon, boolean isExtra) {
        mName = name;
        mValue = value;
        mIcon = icon;
        mIsExtra = isExtra;
    }

    public void setIconAngle(float angle) {
        mIconAngle = angle;
    }

    public void increaseIconAngle(float degrees) {
        mIconAngle += degrees;
    }

    public void decreaseIconAngle(float degrees) {
        mIconAngle -= degrees;
    }

    public void setValue(long value) {
        mValue = value;
    }

    public long getValue() {
        return mValue;
    }

    public String getName() {
        return mName;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public float getIconAngle() {
        return mIconAngle;
    }

    public boolean isExtra() {
        return mIsExtra;
    }

    public float getStartAngle() {
        return mStartAngle;
    }

    public void setStartAngle(float startAngle) {
        mStartAngle = startAngle;
    }

    public float getSweepAngle() {
        return mSweepAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        mSweepAngle = sweepAngle;
    }
}