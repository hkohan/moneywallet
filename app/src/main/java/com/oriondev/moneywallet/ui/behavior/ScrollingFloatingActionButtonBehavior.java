package com.oriondev.moneywallet.ui.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;

import com.oriondev.moneywallet.R;

import java.util.List;


public class ScrollingFloatingActionButtonBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

    private int mToolbarHeight;

    public ScrollingFloatingActionButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R.attr.actionBarSize});
        mToolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton fab, View dependency) {
        return dependency instanceof AppBarLayout || dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton fab, View dependency) {
        if (dependency instanceof AppBarLayout) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
            int fabBottomMargin = lp.bottomMargin;
            int distanceToScroll = fab.getHeight() + fabBottomMargin;
            float ratio = dependency.getY() / (float) mToolbarHeight;
            fab.setTranslationY(distanceToScroll * ratio * -1);
        } else if (dependency instanceof Snackbar.SnackbarLayout) {
            float translationY = getFabTranslationYForSnackBar(parent, fab);
            float percentComplete = -translationY / dependency.getHeight();
            float scaleFactor = 1 - percentComplete;
            fab.setScaleX(scaleFactor);
            fab.setScaleY(scaleFactor);
        }
        return true;
    }

    private float getFabTranslationYForSnackBar(CoordinatorLayout parent, FloatingActionButton fab) {
        float minOffset = 0;
        final List<View> dependencies = parent.getDependencies(fab);
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            final View view = dependencies.get(i);
            if (view instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(fab, view)) {
                minOffset = Math.min(minOffset, view.getTranslationY() - view.getHeight());
            }
        }
        return minOffset;
    }
}