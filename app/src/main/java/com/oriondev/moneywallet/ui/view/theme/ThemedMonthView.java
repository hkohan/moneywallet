package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.view.calendar.MonthView;


public class ThemedMonthView extends MonthView implements ThemeEngine.ThemeConsumer {

    private BackgroundColor mBackgroundColor;

    public ThemedMonthView(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public ThemedMonthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public ThemedMonthView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context, attrs, defStyle);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThemedMonthView, defStyleAttr, 0);
        try {
            mBackgroundColor = BackgroundColor.fromValue(typedArray.getInt(R.styleable.ThemedMonthView_theme_backgroundColor, 0));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        if (mBackgroundColor != null) {
            int background = mBackgroundColor.getColor(theme);
            int color = theme.getBestTextColor(background);
            setBackgroundColor(background);
            setDefaultColor(color);
            setColorSelected(theme.getColorAccent());
            setColorBeforeSelection(color);
            EdgeGlowUtil.setEdgeGlowColor(this, theme.getColorPrimary(), null);
        }
    }
}