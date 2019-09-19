package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import com.oriondev.moneywallet.R;


public class ThemedCoordinatorLayout extends CoordinatorLayout implements ThemeEngine.ThemeConsumer {

    private BackgroundColor mBackgroundColor;

    public ThemedCoordinatorLayout(Context context) {
        super(context);
        initialize(context, null);
    }

    public ThemedCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public ThemedCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThemedCoordinatorLayout, 0, 0);
        try {
            mBackgroundColor = BackgroundColor.fromValue(typedArray.getInt(R.styleable.ThemedCoordinatorLayout_theme_backgroundColor, 0));
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
            setBackgroundColor(background);
        }
    }
}