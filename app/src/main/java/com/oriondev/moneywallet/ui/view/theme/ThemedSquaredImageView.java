package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.view.SquaredImageView;


public class ThemedSquaredImageView extends SquaredImageView implements ThemeEngine.ThemeConsumer {

    private BackgroundColor mBackgroundColor;

    public ThemedSquaredImageView(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public ThemedSquaredImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public ThemedSquaredImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThemedSquaredImageView, defStyleAttr, 0);
        try {
            mBackgroundColor = BackgroundColor.fromValue(typedArray.getInt(R.styleable.ThemedSquaredImageView_theme_backgroundColor, 0));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        if (mBackgroundColor != null) {
            int color = mBackgroundColor.getColor(theme);
            setColorFilter(theme.getBestIconColor(color), PorterDuff.Mode.SRC_IN);
        }
    }
}