package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;


public class ThemedFloatingActionButton extends FloatingActionButton implements ThemeEngine.ThemeConsumer {

    public ThemedFloatingActionButton(Context context) {
        super(context);
    }

    public ThemedFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        boolean isDark = theme.isDark();
        int colorAccent = theme.getColorAccent();
        int colorPressed = Util.shiftColor(colorAccent, isDark ? 0.9f : 1.1f);
        int colorActivated = Util.shiftColor(colorAccent, isDark ? 1.1f : 0.9f);
        int colorRipple = theme.getColorRipple();
        ColorStateList stateList = new ColorStateList(
                new int[][] {
                        new int[] {-android.R.attr.state_pressed},
                        new int[] {android.R.attr.state_pressed}
                },
                new int[] {colorAccent, colorPressed}
        );
        setRippleColor(colorRipple);
        setBackgroundTintList(stateList);
        setIconColor(theme);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        setIconColor(ThemeEngine.getTheme());
    }

    private void setIconColor(ITheme theme) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int color = theme.getBestColor(theme.getColorAccent());
            super.setImageDrawable(TintHelper.createTintedDrawable(drawable, color));
        }
    }
}