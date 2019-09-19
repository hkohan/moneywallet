package com.oriondev.moneywallet.ui.activity.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.utils.Utils;


public abstract class SinglePanelAppBarActivity extends SinglePanelActivity {

    protected void onInflateRootLayout() {
        setContentView(R.layout.activity_single_panel_appbar);
    }

    protected void onConfigureRootLayout(Bundle savedInstanceState) {
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup headerContainer = findViewById(R.id.app_bar_container);
        ViewGroup panelContainer = Utils.findViewGroupByIds(this,
                R.id.primary_panel_container_frame_layout,
                R.id.primary_panel_container_card_view,
                R.id.primary_panel_container_linear_layout,
                R.id.primary_panel_container_coordinator_layout
        );
        onCreateHeaderView(inflater, headerContainer, savedInstanceState);
        onCreatePanelView(inflater, panelContainer, savedInstanceState);
    }

    protected abstract void onCreateHeaderView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);

    protected abstract void onCreatePanelView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);
}