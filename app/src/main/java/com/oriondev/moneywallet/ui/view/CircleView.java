package com.oriondev.moneywallet.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {

    private Paint mPaint;
    private float mRadius;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mRadius = 0.9f;
    }

    public void setColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    public void setRadius(float radius) {
        mRadius = radius;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas c) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (width == 0 || height == 0) return;
        int radius = Math.min(width, height) / 2;
        c.drawCircle(width/2, height/2, radius * mRadius, mPaint);
    }
}