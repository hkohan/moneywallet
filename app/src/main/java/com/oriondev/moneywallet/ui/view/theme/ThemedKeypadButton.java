package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.oriondev.moneywallet.R;


public class ThemedKeypadButton extends AppCompatButton implements ThemeEngine.ThemeConsumer {

    private BackgroundColor mBackgroundColor;

    public ThemedKeypadButton(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public ThemedKeypadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public ThemedKeypadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThemedKeypadButton, defStyleAttr, 0);
        try {
            mBackgroundColor = BackgroundColor.fromValue(typedArray.getInt(R.styleable.ThemedKeypadButton_theme_backgroundColor, 0));
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
            setBackgroundColor(backgroundColor);
            setTextColor(theme.getBestColor(backgroundColor));
        }
    }
}