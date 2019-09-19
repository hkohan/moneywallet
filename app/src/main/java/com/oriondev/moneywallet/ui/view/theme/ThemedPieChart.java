package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.oriondev.moneywallet.ui.view.chart.PieChart;


public class ThemedPieChart extends PieChart implements ThemeEngine.ThemeConsumer {

    public ThemedPieChart(Context context) {
        super(context);
    }

    public ThemedPieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedPieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ThemedPieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        setHoleColor(theme.getColorWindowForeground());
        setLineColor(theme.getIconColor());
    }
}