package com.oriondev.moneywallet.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oriondev.moneywallet.R;


public abstract class MultiPanelAppBarFragment extends MultiPanelFragment {

    private ViewGroup mAppBarContainer;
    private ViewGroup mLeftAppBarContainer;

    @Override
    protected View onInflateRootLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_multi_panel_appbar, container, false);
    }

    protected void onSetupRootLayout(View view) {
        super.onSetupRootLayout(view);
        mAppBarContainer = view.findViewById(R.id.primary_app_bar_container);
        mLeftAppBarContainer = view.findViewById(R.id.left_primary_app_bar_container);
    }

    protected void onConfigureRootLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup appBarContainer = mLeftAppBarContainer != null ? mLeftAppBarContainer : mAppBarContainer;
        onCreatePrimaryAppBar(inflater, appBarContainer, savedInstanceState);
        super.onConfigureRootLayout(inflater, container, savedInstanceState);
    }

    protected abstract void onCreatePrimaryAppBar(LayoutInflater inflater, @NonNull ViewGroup primaryAppBar, @Nullable Bundle savedInstanceState);

    protected abstract void onCreatePrimaryPanel(LayoutInflater inflater, @NonNull ViewGroup primaryPanel, @Nullable Bundle savedInstanceState);

    protected abstract void onCreateSecondaryPanel(LayoutInflater inflater, @NonNull ViewGroup secondaryPanel, @Nullable Bundle savedInstanceState);
}