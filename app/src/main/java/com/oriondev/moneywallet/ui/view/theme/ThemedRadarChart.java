package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;


public class ThemedRadarChart extends RadarChart implements ThemeEngine.ThemeConsumer {

    public ThemedRadarChart(Context context) {
        super(context);
    }

    public ThemedRadarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedRadarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        int textColor = theme.getTextColorSecondary();
        int iconColor = theme.getIconColor();
        // style xAxis
        XAxis xAxis = getXAxis();
        xAxis.setAxisLineColor(iconColor);
        xAxis.setGridColor(iconColor);
        xAxis.setTextColor(textColor);
        // style yAxis
        YAxis yAxis = getYAxis();
        yAxis.setZeroLineColor(iconColor);
        yAxis.setAxisLineColor(iconColor);
        yAxis.setGridColor(iconColor);
        yAxis.setTextColor(textColor);
        // style legend
        Legend legend = getLegend();
        legend.setTextColor(textColor);
        // style description
        Description description = getDescription();
        description.setTextColor(textColor);
        // style the chart itself
        setNoDataTextColor(textColor);
    }
}