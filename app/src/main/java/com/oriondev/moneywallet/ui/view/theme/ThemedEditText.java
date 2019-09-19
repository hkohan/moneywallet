package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.oriondev.moneywallet.R;


public class ThemedEditText extends AppCompatEditText implements ThemeEngine.ThemeConsumer {

    private BackgroundColor mBackgroundColor;

    public ThemedEditText(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public ThemedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public ThemedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThemedEditText, defStyleAttr, 0);
        try {
            mBackgroundColor = BackgroundColor.fromValue(typedArray.getInt(R.styleable.ThemedEditText_theme_backgroundColor, 0));
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
            setHintTextColor(theme.getBestHintColor(backgroundColor));
            TintHelper.setCursorTint(this, theme.getColorAccent());
        }
    }
}