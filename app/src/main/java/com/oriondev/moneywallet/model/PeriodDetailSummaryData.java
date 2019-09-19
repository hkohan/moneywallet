package com.oriondev.moneywallet.model;

import com.github.mikephil.charting.data.BarData;

import java.util.List;


public class PeriodDetailSummaryData {

    private final Money mNetIncomes;
    private final List<BarData> mBarDataList;
    private final List<CurrencyUnit> mBarDataCurrencies;
    private final List<PeriodMoney> mPeriodList;

    public PeriodDetailSummaryData(Money netIncomes, List<BarData> barDataList, List<CurrencyUnit> currencyUnitList, List<PeriodMoney> periodMoneyList) {
        mNetIncomes = netIncomes;
        mBarDataList = barDataList;
        mBarDataCurrencies = currencyUnitList;
        mPeriodList = periodMoneyList;
    }

    public Money getNetIncomes() {
        return mNetIncomes;
    }

    public BarData getChartData(int index) {
        return mBarDataList.get(index);
    }

    public CurrencyUnit getChartCurrency(int index) {
        return mBarDataCurrencies.get(index);
    }

    public int getChartCount() {
        return mBarDataList.size();
    }

    public PeriodMoney getPeriodMoney(int index) {
        return mPeriodList.get(index);
    }

    public int getPeriodCount() {
        return mPeriodList.size();
    }
}