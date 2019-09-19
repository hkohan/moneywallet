package com.oriondev.moneywallet.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class MaterialProgressBar extends View {

    private Paint mPaint;
    private int mReachedBarColor;
    private int mUnreachedBarColor;

    private long mMaxValue;
    private long mProgressValue;

    public MaterialProgressBar(Context context) {
        super(context);
        initialize();
    }

    public MaterialProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MaterialProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaterialProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    private void initialize() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mMaxValue = 0;
        mProgressValue = 0;

        mUnreachedBarColor = Color.GRAY;
        mReachedBarColor = Color.RED;
    }

    public void setReachedBarColor(int color) {
        mReachedBarColor = color;
        invalidate();
    }

    public void setUnreachedBarColor(int color) {
        mUnreachedBarColor = color;
        invalidate();
    }

    public void setMaxValue(long maxValue) {
        mMaxValue = maxValue;
        invalidate();
    }

    public void setProgressValue(long progressValue) {
        mProgressValue = progressValue;
        invalidate();
    }

    protected double getProgress() {
        if (mMaxValue > 0 && mProgressValue > 0) {
            double progress = (double) mProgressValue / mMaxValue;
            return progress > 1 ? 1 : progress;
        }
        return 0d;
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        // measure view
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (Math.min(measuredWidth, measuredHeight) == 0) {
            // skip drawing
            return;
        }
        // draw unreached bar
        mPaint.setColor(mUnreachedBarColor);
        c.drawRect(0, 0, measuredWidth, measuredHeight, mPaint);
        // calculate progress and draw on top
        mPaint.setColor(mReachedBarColor);
        int reachedBarWidth = (int) (getProgress() * measuredWidth);
        c.drawRect(0, 0, reachedBarWidth, measuredHeight, mPaint);
    }
}