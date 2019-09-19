package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.oriondev.moneywallet.ui.view.calendar.TimelineView;


public class ThemedTimelineView extends TimelineView implements ThemeEngine.ThemeConsumer {

    public ThemedTimelineView(Context context) {
        super(context);
    }

    public ThemedTimelineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedTimelineView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        int textColor = theme.getTextColorPrimary();
        setDayLabelColor(textColor);
        setDateLabelColor(textColor);
        setDateLabelSelectedColor(theme.getColorAccent());
        EdgeGlowUtil.setEdgeGlowColor(this, theme.getColorPrimary(), null);
    }
}