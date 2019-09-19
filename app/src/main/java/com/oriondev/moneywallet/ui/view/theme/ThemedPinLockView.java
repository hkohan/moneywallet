package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.andrognito.pinlockview.PinLockView;
import com.oriondev.moneywallet.R;


public class ThemedPinLockView extends PinLockView implements ThemeEngine.ThemeConsumer {

    private BackgroundColor mBackgroundColor;

    public ThemedPinLockView(Context context) {
        super(context);
        initialize(context, null);
    }

    public ThemedPinLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThemedPinLockView, 0, 0);
        try {
            mBackgroundColor = BackgroundColor.fromValue(typedArray.getInt(R.styleable.ThemedPinLockView_theme_backgroundColor, 0));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        if (mBackgroundColor != null) {
            int backgroundColor = mBackgroundColor.getColor(theme);
            setTextColor(theme.getBestTextColor(backgroundColor));
        }
    }
}