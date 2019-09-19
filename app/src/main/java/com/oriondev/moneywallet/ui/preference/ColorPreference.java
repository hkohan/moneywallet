package com.oriondev.moneywallet.ui.preference;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.view.CircleView;


public class ColorPreference extends ThemedPreference {

    private int mColor;

    public ColorPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public ColorPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ColorPreference(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        setWidgetLayoutResource(R.layout.layout_preference_color_picker);
        mColor = Color.BLACK;
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        CircleView circleView = (CircleView) holder.findViewById(R.id.circle_view);
        if (circleView != null) {
            circleView.setColor(mColor);
        }
    }

    public void setColor(int newColor) {
        mColor = newColor;
        notifyChanged();
    }
}