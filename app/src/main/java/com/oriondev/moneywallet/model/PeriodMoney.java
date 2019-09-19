package com.oriondev.moneywallet.model;

import com.oriondev.moneywallet.utils.DateUtils;

import java.util.Date;


public class PeriodMoney {

    private final Date mStartDate;
    private final Date mEndDate;
    private final Money mIncomes;
    private final Money mExpenses;
    private final Money mNetIncomes;

    public PeriodMoney(Date startDate, Date endDate) {
        mStartDate = startDate;
        mEndDate = endDate;
        mIncomes = new Money();
        mExpenses = new Money();
        mNetIncomes = new Money();
    }

    public void addIncome(String currency, long money) {
        mIncomes.addMoney(currency, money);
        mNetIncomes.addMoney(currency, money);
    }

    public void addExpense(String currency, long money) {
        mExpenses.addMoney(currency, money);
        mNetIncomes.removeMoney(currency, money);
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public Money getIncomes() {
        return mIncomes;
    }

    public Money getExpenses() {
        return mExpenses;
    }

    public Money getNetIncomes() {
        return mNetIncomes;
    }

    public boolean isTimeRange() {
        return DateUtils.isTheSameDayAndHour(mStartDate, mEndDate);
    }
}