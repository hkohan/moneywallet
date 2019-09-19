package com.oriondev.moneywallet.ui.preference;

import android.content.Context;
import android.support.v7.preference.PreferenceCategory;
import android.util.AttributeSet;

import com.oriondev.moneywallet.R;


public class ThemedPreferenceCategory extends PreferenceCategory {

    public ThemedPreferenceCategory(Context context) {
        super(context);
        initialize();
    }

    public ThemedPreferenceCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ThemedPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public ThemedPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    private void initialize() {
        setLayoutResource(R.layout.layout_preference_category_material_design);
    }
}