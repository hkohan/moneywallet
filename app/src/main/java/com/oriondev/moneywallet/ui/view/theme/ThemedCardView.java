package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

public class ThemedCardView extends CardView implements ThemeEngine.ThemeConsumer {

    public ThemedCardView(@NonNull Context context) {
        super(context);
    }

    public ThemedCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        setBackgroundColor(theme.getColorCardBackground());
    }
}