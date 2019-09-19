package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;

import com.oriondev.moneywallet.R;


public class ThemedAppBarLayout extends AppBarLayout implements ThemeEngine.ThemeConsumer {

    private BackgroundColor mBackgroundColor;

    public ThemedAppBarLayout(Context context) {
        super(context);
        initialize(context, null);
    }

    public ThemedAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThemedAppBarLayout, 0, 0);
        try {
            mBackgroundColor = BackgroundColor.fromValue(typedArray.getInt(R.styleable.ThemedAppBarLayout_theme_backgroundColor, 0));
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