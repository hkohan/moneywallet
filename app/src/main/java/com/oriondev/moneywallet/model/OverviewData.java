package com.oriondev.moneywallet.model;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.RadarData;

import java.util.List;


public class OverviewData {

    private final BarData mBarData;
    private final LineData mLineData;
    private final RadarData mRadarData;
    private final List<PeriodMoney> mPeriodMoneyList;

    public OverviewData(BarData barData, LineData lineData, RadarData radarData, List<PeriodMoney> periodMoneyList) {
        mBarData = barData;
        mLineData = lineData;
        mRadarData = radarData;
        mPeriodMoneyList = periodMoneyList;
    }

    public BarData getBarData() {
        return mBarData;
    }

    public LineData getLineData() {
        return mLineData;
    }

    public RadarData getRadarData() {
        return mRadarData;
    }

    public PeriodMoney getPeriodMoney(int index) {
        return mPeriodMoneyList.get(index);
    }

    public int getPeriodCount() {
        return mPeriodMoneyList.size();
    }
}