package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

import com.oriondev.moneywallet.R;


public class ThemedKeypadImageButton extends AppCompatImageButton implements ThemeEngine.ThemeConsumer {

    private BackgroundColor mBackgroundColor;

    public ThemedKeypadImageButton(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public ThemedKeypadImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public ThemedKeypadImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThemedKeypadImageButton, defStyleAttr, 0);
        try {
            mBackgroundColor = BackgroundColor.fromValue(typedArray.getInt(R.styleable.ThemedKeypadImageButton_theme_backgroundColor, 0));
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
            setColorFilter(theme.getBestColor(backgroundColor), PorterDuff.Mode.SRC_IN);
        }
    }
}