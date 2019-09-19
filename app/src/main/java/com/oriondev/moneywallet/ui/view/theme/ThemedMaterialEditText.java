package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.view.text.MaterialEditText;

public class ThemedMaterialEditText extends MaterialEditText implements ThemeEngine.ThemeConsumer {

    private BackgroundColor mBackgroundColor;

    public ThemedMaterialEditText(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public ThemedMaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public ThemedMaterialEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThemedMaterialEditText, defStyleAttr, 0);
        try {
            mBackgroundColor = BackgroundColor.fromValue(typedArray.getInt(R.styleable.ThemedMaterialEditText_theme_backgroundColor, BackgroundColor.getValue(BackgroundColor.COLOR_WINDOW_FOREGROUND)));
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
            setFloatingLabelColorNormal(theme.getBestHintColor(backgroundColor));
            setHintTextColor(theme.getBestHintColor(backgroundColor));
            setFloatingLabelColorFocused(theme.getColorAccent());
            setLeftIconColorNormal(theme.getBestIconColor(backgroundColor));
            setLeftIconColorFocused(theme.getColorAccent());
            setBottomLineColorNormal(theme.getBestIconColor(backgroundColor));
            setBottomLineColorFocused(theme.getColorAccent());
            setBottomLineColorError(theme.getErrorColor());
            TintHelper.setCursorTint(this, theme.getColorAccent());
        }
    }
}