package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.util.AttributeSet;

import com.pnikosis.materialishprogress.ProgressWheel;


public class ThemedProgressWheel extends ProgressWheel implements ThemeEngine.ThemeConsumer {

    public ThemedProgressWheel(Context context) {
        super(context);
    }

    public ThemedProgressWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        // This view is commonly used on the background color so we can
        // double check if the color to use as bar-color is very similar
        // to the color of the background. In this case we can dynamically
        // calculate the best color to use instead of the color accent.
        int barColor = theme.getColorAccent();
        int windowBackground = theme.getColorWindowBackground();
        if (!Util.isColorVisible(barColor, windowBackground)) {
            barColor = theme.getBestIconColor(windowBackground);
        }
        setBarColor(barColor);
    }
}