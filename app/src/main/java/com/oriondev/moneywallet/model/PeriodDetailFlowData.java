package com.oriondev.moneywallet.model;

import com.oriondev.moneywallet.ui.view.chart.PieData;

import java.util.List;


public class PeriodDetailFlowData {

    private final Money mTotalMoney;
    // private final List<PieData> mPieDataList;
    private final List<PieData> mPieDataList;
    private final List<CategoryMoney> mCategoryMoneyList;

    public PeriodDetailFlowData(Money totalMoney, List<PieData> pieDataList, List<CategoryMoney> categoryMoneyList) {
        mTotalMoney = totalMoney;
        mPieDataList = pieDataList;
        mCategoryMoneyList = categoryMoneyList;
    }

    public Money getTotalMoney() {
        return mTotalMoney;
    }

    public PieData getChartData(int index) {
        return mPieDataList.get(index);
    }

    public int getChartCount() {
        return mPieDataList.size();
    }

    public CategoryMoney getCategory(int index) {
        return mCategoryMoneyList.get(index);
    }

    public int getCategoryCount() {
        return mCategoryMoneyList.size();
    }
}