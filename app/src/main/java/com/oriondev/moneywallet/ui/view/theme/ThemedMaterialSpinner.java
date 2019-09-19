package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.jaredrummler.materialspinner.MaterialSpinner;


public class ThemedMaterialSpinner extends MaterialSpinner implements ThemeEngine.ThemeConsumer {

    private static final int COLOR_BACKGROUND_LIGHT = Color.WHITE;
    private static final int COLOR_BACKGROUND_DARK = Color.parseColor("#424242");

    public ThemedMaterialSpinner(Context context) {
        super(context);
    }

    public ThemedMaterialSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedMaterialSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        setTextColor(theme.getTextColorPrimary());
        setArrowColor(theme.getIconColor());
        setBackgroundColor(theme.isDark() ? COLOR_BACKGROUND_DARK : COLOR_BACKGROUND_LIGHT);
    }
}