package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;


public class ThemedSeekBar extends AppCompatSeekBar implements ThemeEngine.ThemeConsumer {

    public ThemedSeekBar(Context context) {
        super(context);
    }

    public ThemedSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        TintHelper.applyTint(this, theme.getColorAccent(), theme.isDark());
    }
}