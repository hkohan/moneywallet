package com.oriondev.moneywallet.ui.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oriondev.moneywallet.model.Icon;
import com.oriondev.moneywallet.utils.IconLoader;


public class DoubleIconDrawable extends Drawable {

    private final Drawable mDrawable1;
    private final Drawable mDrawable2;

    public DoubleIconDrawable(Context context, Icon icon1, Icon icon2) {
        mDrawable1 = icon1 != null ? icon1.getDrawable(context) : IconLoader.UNKNOWN.getDrawable(context);
        mDrawable2 = icon2 != null ? icon2.getDrawable(context) : IconLoader.UNKNOWN.getDrawable(context);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        if (width == 0 || height == 0) {
            return;
        }
        mDrawable1.setBounds(0, 0, width / 2, height);
        mDrawable2.setBounds(width / 2, 0, width, height);
        mDrawable1.draw(canvas);
        mDrawable2.draw(canvas);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}