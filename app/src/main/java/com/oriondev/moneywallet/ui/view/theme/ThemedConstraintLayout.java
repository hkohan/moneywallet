package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

import com.oriondev.moneywallet.R;


public class ThemedConstraintLayout extends ConstraintLayout implements ThemeEngine.ThemeConsumer {

    private BackgroundColor mBackgroundColor;

    public ThemedConstraintLayout(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public ThemedConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public ThemedConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThemedConstraintLayout, defStyleAttr, 0);
        try {
            mBackgroundColor = BackgroundColor.fromValue(typedArray.getInt(R.styleable.ThemedConstraintLayout_theme_backgroundColor, 0));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        if (mBackgroundColor != null) {
            int background = getBackgroundColor(theme);
            setBackgroundColor(background);
        }
    }

    private int getBackgroundColor(ITheme theme) {
        if (mBackgroundColor != null) {
            if (mBackgroundColor == BackgroundColor.COLOR_PRIMARY) {
                return theme.getColorPrimary();
            } else {
                return theme.getColorPrimaryDark();
            }
        } else {
            return theme.getColorPrimary();
        }
    }

    public enum BackgroundColor {
        COLOR_PRIMARY(0),
        COLOR_PRIMARY_DARK(1);

        private int mValue;

        BackgroundColor(int value) {
            mValue = value;
        }

        static BackgroundColor fromValue(int value) {
            switch (value) {
                case 0:
                    return COLOR_PRIMARY;
                case 1:
                    return COLOR_PRIMARY_DARK;
                default:
                    return null;
            }
        }
    }
}