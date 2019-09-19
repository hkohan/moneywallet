package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;


public class ThemedNestedScrollView extends NestedScrollView implements ThemeEngine.ThemeConsumer {

    public ThemedNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public ThemedNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        EdgeGlowUtil.setEdgeGlowColor(this, theme.getColorPrimary());
    }
}