package com.oriondev.moneywallet.ui.view.theme;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ThemedScrollView extends ScrollView implements ThemeEngine.ThemeConsumer {

    public ThemedScrollView(Context context) {
        super(context);
    }

    public ThemedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ThemedScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        EdgeGlowUtil.setEdgeGlowColor(this, theme.getColorPrimary());
    }
}