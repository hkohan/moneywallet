package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.util.AttributeSet;

import com.oriondev.moneywallet.ui.view.MaterialProgressBar;


public class ThemedMaterialProgressBar extends MaterialProgressBar implements ThemeEngine.ThemeConsumer {

    public ThemedMaterialProgressBar(Context context) {
        super(context);
    }

    public ThemedMaterialProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedMaterialProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ThemedMaterialProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        setReachedBarColor(theme.getColorAccent());
        setUnreachedBarColor(theme.getIconColor());
    }
}