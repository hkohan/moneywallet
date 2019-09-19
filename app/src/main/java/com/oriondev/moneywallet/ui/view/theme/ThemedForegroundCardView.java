package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.oriondev.moneywallet.ui.view.ForegroundCardView;


public class ThemedForegroundCardView extends ForegroundCardView implements ThemeEngine.ThemeConsumer {

    public ThemedForegroundCardView(@NonNull Context context) {
        super(context);
    }

    public ThemedForegroundCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedForegroundCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        setBackgroundColor(theme.getColorWindowForeground());
    }
}