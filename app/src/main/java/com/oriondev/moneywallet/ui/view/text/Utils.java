package com.oriondev.moneywallet.ui.view.text;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;

/*package-local*/ class Utils {

    /*package-local*/ static float getPixels(int dp, Resources resources) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    /*package-local*/ static boolean isRtl(Resources resources) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && resources.getConfiguration().getLayoutDirection() == Configuration.SCREENLAYOUT_LAYOUTDIR_RTL;
    }

    /*package-local*/ static void setBackgroundCompat(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    /*package-local*/ static Drawable getDrawableCompat(Context context, @DrawableRes int drawable) {
        return ContextCompat.getDrawable(context, drawable);
    }
}