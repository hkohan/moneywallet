package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.andrognito.patternlockview.PatternLockView;
import com.oriondev.moneywallet.R;


public class ThemedPatternLockView extends PatternLockView implements ThemeEngine.ThemeConsumer {

    private BackgroundColor mBackgroundColor;

    public ThemedPatternLockView(Context context) {
        super(context);
        initialize(context, null);
    }

    public ThemedPatternLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThemedPatternLockView, 0, 0);
        try {
            mBackgroundColor = BackgroundColor.fromValue(typedArray.getInt(R.styleable.ThemedPatternLockView_theme_backgroundColor, 0));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        int backgroundColor = getBackgroundColor(theme);
        setCorrectStateColor(theme.getColorAccent());
        setNormalStateColor(theme.getBestColor(backgroundColor));
        /* TODO: ensure this is visible over background color */
        setWrongStateColor(theme.getErrorColor());
    }

    private int getBackgroundColor(ITheme theme) {
        if (mBackgroundColor != null) {
            if (mBackgroundColor == BackgroundColor.COLOR_PRIMARY) {
                return theme.getColorPrimary();
            } else {
                return theme.getColorPrimaryDark();
            }
        } else {
            return theme.getColorWindowBackground();
        }
    }
}