package com.oriondev.moneywallet.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class SquaredImageView extends AppCompatImageView {

    public SquaredImageView(Context context) {
        super(context);
    }

    public SquaredImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquaredImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}