package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;


public class ThemedBarChart extends BarChart implements ThemeEngine.ThemeConsumer {

    public ThemedBarChart(Context context) {
        super(context);
    }

    public ThemedBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedBarChart(Context context, AttributeSet attrs, int defStyle) {
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
        // style yAxis left
        YAxis yLeftAxis = getAxisLeft();
        yLeftAxis.setZeroLineColor(iconColor);
        yLeftAxis.setAxisLineColor(iconColor);
        yLeftAxis.setGridColor(iconColor);
        yLeftAxis.setTextColor(textColor);
        // style yAxis right
        YAxis yRightAxis = getAxisRight();
        yRightAxis.setZeroLineColor(iconColor);
        yRightAxis.setAxisLineColor(iconColor);
        yRightAxis.setGridColor(iconColor);
        yRightAxis.setTextColor(textColor);
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