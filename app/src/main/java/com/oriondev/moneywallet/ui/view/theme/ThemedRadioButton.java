package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;


public class ThemedRadioButton extends AppCompatRadioButton implements ThemeEngine.ThemeConsumer {

    public ThemedRadioButton(Context context) {
        super(context);
    }

    public ThemedRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        TintHelper.applyTint(this, theme.getColorAccent(), theme.isDark());
        setTextColor(theme.getTextColorPrimary());
        setHintTextColor(theme.getHintTextColor());
    }
}