package com.oriondev.moneywallet.ui.preference;

import android.content.Context;
import android.support.v7.preference.Preference;
import android.util.AttributeSet;

import com.oriondev.moneywallet.R;

public class ThemedPreference extends Preference {

    public ThemedPreference(Context context) {
        super(context);
        initialize();
    }

    public ThemedPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ThemedPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        setLayoutResource(R.layout.layout_preference_material_design);
    }
}