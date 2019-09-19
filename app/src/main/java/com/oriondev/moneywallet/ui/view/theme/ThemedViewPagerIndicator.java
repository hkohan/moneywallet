package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.util.AttributeSet;

import com.itsronald.widget.ViewPagerIndicator;


public class ThemedViewPagerIndicator extends ViewPagerIndicator implements ThemeEngine.ThemeConsumer {

    public ThemedViewPagerIndicator(Context context) {
        super(context);
    }

    public ThemedViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ThemedViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        setSelectedDotColor(theme.getColorPrimary());
        setUnselectedDotColor(theme.getIconColor());
    }
}