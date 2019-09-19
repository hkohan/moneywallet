package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;


public class ThemedCheckBox extends AppCompatCheckBox implements ThemeEngine.ThemeConsumer {

    public ThemedCheckBox(Context context) {
        super(context);
    }

    public ThemedCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        TintHelper.applyTint(this, theme.getColorAccent(), theme.isDark());
        setTextColor(theme.getTextColorPrimary());
        setHintTextColor(theme.getTextColorPrimary());
    }
}