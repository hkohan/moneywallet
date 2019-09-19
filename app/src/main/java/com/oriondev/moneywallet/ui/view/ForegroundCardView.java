package com.oriondev.moneywallet.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.oriondev.moneywallet.R;


public class ForegroundCardView extends CardView {

    private int mMaxWidth = -1;
    private int mMaxHeight = -1;

    public ForegroundCardView(@NonNull Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public ForegroundCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public ForegroundCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ForegroundCardView, defStyleAttr, 0);
        try {
            mMaxWidth = typedArray.getDimensionPixelSize(R.styleable.ForegroundCardView_fcv_max_width, -1);
            mMaxHeight = typedArray.getDimensionPixelSize(R.styleable.ForegroundCardView_fcv_max_height, -1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // initialize the reference to the final values to pass to super class
        int finalWidthMeasureSpec = widthMeasureSpec;
        int finalHeightMeasureSpec = heightMeasureSpec;
        // first of all we need to get the current measured dimensions
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // check if width is larger than the maximum value
        if (mMaxWidth > -1) {
            int width = Math.min(widthSize, mMaxWidth);
            finalWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        }
        // check if height is larger than the maximum value
        if (mMaxHeight > -1) {
            int height = Math.min(heightSize, mMaxHeight);
            finalHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        // apply the measured dimensions
        super.onMeasure(finalWidthMeasureSpec, finalHeightMeasureSpec);
    }
}