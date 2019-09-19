package com.oriondev.moneywallet.ui.preference;

import android.content.Context;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.AttributeSet;

import com.oriondev.moneywallet.R;


public class ThemedSwitchPreference extends SwitchPreferenceCompat {

    public ThemedSwitchPreference(Context context) {
        super(context);
        initialize();
    }

    public ThemedSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ThemedSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        setLayoutResource(R.layout.layout_preference_material_design);
    }
}