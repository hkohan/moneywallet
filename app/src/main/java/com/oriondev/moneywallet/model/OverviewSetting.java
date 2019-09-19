package com.oriondev.moneywallet.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.oriondev.moneywallet.utils.DateUtils;

import java.util.Date;


public class OverviewSetting implements Parcelable {

    private final Date mStartDate;
    private final Date mEndDate;
    private final Group mGroupType;
    private final Type mType;
    private final CashFlow mCashFlow;
    private final long mCategoryId;

    public OverviewSetting(Date startDate, Date endDate, Group groupType, CashFlow cashFlow) {
        mStartDate = DateUtils.setTime(startDate, 0, 0, 0, 0);
        mEndDate = DateUtils.setTime(endDate, 23, 59, 59, 999);
        mGroupType = groupType;
        mType = Type.CASH_FLOW;
        mCashFlow = cashFlow;
        mCategoryId = 0L;
    }

    public OverviewSetting(Date startDate, Date endDate, Group groupType, long categoryId) {
        mStartDate = DateUtils.setTime(startDate, 0, 0, 0, 0);
        mEndDate = DateUtils.setTime(endDate, 23, 59, 59, 999);
        mGroupType = groupType;
        mType = Type.CATEGORY;
        mCashFlow = CashFlow.NET_INCOMES;
        mCategoryId = categoryId;
    }

    private OverviewSetting(Parcel in) {
        mStartDate = (Date) in.readSerializable();
        mEndDate = (Date) in.readSerializable();
        mGroupType = (Group) in.readSerializable();
        mType = (Type) in.readSerializable();
        mCashFlow = (CashFlow) in.readSerializable();
        mCategoryId = in.readLong();
    }

    public static final Creator<OverviewSetting> CREATOR = new Creator<OverviewSetting>() {

        @Override
        public OverviewSetting createFromParcel(Parcel in) {
            return new OverviewSetting(in);
        }

        @Override
        public OverviewSetting[] newArray(int size) {
            return new OverviewSetting[size];
        }

    };

    public Date getStartDate() {
        return mStartDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public Group getGroupType() {
        return mGroupType;
    }

    public Type getType() {
        return mType;
    }

    public CashFlow getCashFlow() {
        return mCashFlow;
    }

    public long getCategoryId() {
        return mCategoryId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(mStartDate);
        dest.writeSerializable(mEndDate);
        dest.writeSerializable(mGroupType);
        dest.writeSerializable(mType);
        dest.writeSerializable(mCashFlow);
        dest.writeLong(mCategoryId);
    }

    public enum Type {
        CASH_FLOW,
        CATEGORY
    }

    public enum CashFlow {
        INCOMES,
        EXPENSES,
        NET_INCOMES
    }
}