package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;


public class ThemedViewPager extends ViewPager implements ThemeEngine.ThemeConsumer {

    public ThemedViewPager(@NonNull Context context) {
        super(context);
    }

    public ThemedViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        EdgeGlowUtil.setEdgeGlowColor(this, theme.getColorPrimary());
    }
}