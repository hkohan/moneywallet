package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


public class ThemedRecyclerView extends RecyclerView implements ThemeEngine.ThemeConsumer {

    public ThemedRecyclerView(Context context) {
        super(context);
    }

    public ThemedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        EdgeGlowUtil.setEdgeGlowColor(this, theme.getColorPrimary(), null);
    }

    public static void applyTheme(RecyclerView recyclerView, ITheme theme) {
        EdgeGlowUtil.setEdgeGlowColor(recyclerView, theme.getColorPrimary(), null);
    }
}