package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;


public class ThemedSwitchCompat extends SwitchCompat implements ThemeEngine.ThemeConsumer {

    public ThemedSwitchCompat(Context context) {
        super(context);
    }

    public ThemedSwitchCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedSwitchCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        TintHelper.applyTint(this, theme.getColorAccent(), theme.isDark());
        setTextColor(theme.getTextColorPrimary());
    }
}