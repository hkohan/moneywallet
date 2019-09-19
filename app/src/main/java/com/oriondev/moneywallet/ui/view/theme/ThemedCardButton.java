package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.util.AttributeSet;

import com.oriondev.moneywallet.ui.view.CardButton;


public class ThemedCardButton extends CardButton implements ThemeEngine.ThemeConsumer {

    public ThemedCardButton(Context context) {
        super(context);
    }

    public ThemedCardButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedCardButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        setTextColor(theme.getColorAccent());
    }
}