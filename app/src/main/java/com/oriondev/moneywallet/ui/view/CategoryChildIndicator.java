package com.oriondev.moneywallet.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;


public class CategoryChildIndicator extends View {

    private Paint mPaint;
    private boolean mIsLast;

    public CategoryChildIndicator(Context context) {
        super(context);
        initialize();
    }

    public CategoryChildIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public CategoryChildIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CategoryChildIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    private void initialize() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mIsLast = true;
    }

    public void setLast(boolean last) {
        mIsLast = last;
    }

    public void setLineColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    @Override
    public void onDraw(Canvas c) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int lineSize = 8;
        if (width == 0 || height == 0) {
            return;
        }
        int startX = (width / 2) - (lineSize / 2);
        int endX = startX + lineSize;
        int startY = 0;
        int endY = mIsLast ? ((height / 2) + (lineSize / 2)) : height;
        c.drawRect(startX, startY, endX, endY, mPaint);
        endX = width;
        startY = (height / 2) + (lineSize / 2);
        endY = startY + lineSize;
        c.drawRect(startX, startY, endX, endY, mPaint);
    }
}