package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.util.AttributeSet;

import com.oriondev.moneywallet.ui.view.text.MaterialTextView;


public class ThemedMaterialTextView extends MaterialTextView implements ThemeEngine.ThemeConsumer {

    public ThemedMaterialTextView(Context context) {
        super(context);
    }

    public ThemedMaterialTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedMaterialTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onApplyTheme(ITheme theme) {
        setLeftIconColor(theme.getIconColor());
        setTextColor(theme.getTextColorPrimary());
        setFloatingLabelColor(theme.getTextColorSecondary());
        setBottomLineColor(theme.getTextColorSecondary());
    }
}