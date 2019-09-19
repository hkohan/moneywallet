package com.oriondev.moneywallet.ui.fragment.stub;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.fragment.base.MultiPanelFragment;


public class StubMultiPanelFragment extends MultiPanelFragment {

    @Override
    protected void onCreatePrimaryPanel(LayoutInflater inflater, @NonNull ViewGroup primaryPanel, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(primaryPanel.getContext());
        textView.setText("Primary panel");
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showSecondaryPanel();
            }

        });
        textView.setBackgroundColor(Color.RED);

        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.setBehavior(new AppBarLayout.ScrollingViewBehavior());

        primaryPanel.addView(textView, params);
    }

    @Override
    protected void onCreateSecondaryPanel(LayoutInflater inflater, @NonNull ViewGroup secondaryPanel, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(secondaryPanel.getContext());
        textView.setText("Secondary panel");
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                hideSecondaryPanel();
            }

        });
        secondaryPanel.addView(textView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        );
    }

    @Override
    protected int getTitleRes() {
        return R.string.app_name;
    }
}