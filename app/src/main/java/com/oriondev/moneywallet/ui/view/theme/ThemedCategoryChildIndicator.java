package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.util.AttributeSet;

import com.oriondev.moneywallet.ui.view.CategoryChildIndicator;


public class ThemedCategoryChildIndicator extends CategoryChildIndicator implements ThemeEngine.ThemeConsumer {

    public ThemedCategoryChildIndicator(Context context) {
        super(context);
    }

    public ThemedCategoryChildIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedCategoryChildIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ThemedCategoryChildIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        setLineColor(theme.getIconColor());
    }
}