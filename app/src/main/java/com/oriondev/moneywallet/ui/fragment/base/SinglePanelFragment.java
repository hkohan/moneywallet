package com.oriondev.moneywallet.ui.fragment.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.activity.ToolbarController;
import com.oriondev.moneywallet.utils.Utils;


public abstract class SinglePanelFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private Toolbar mToolbar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_single_panel, container, false);
        mToolbar = view.findViewById(R.id.primary_toolbar);
        ViewGroup parent = Utils.findViewGroupByIds(view,
                R.id.primary_panel_container_frame_layout,
                R.id.primary_panel_container_card_view,
                R.id.primary_panel_container_linear_layout,
                R.id.primary_panel_container_coordinator_layout
        );
        onCreatePanelView(inflater, parent, savedInstanceState);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floating_action_button);
        onSetupFloatingActionButton(floatingActionButton);
        setupPrimaryToolbar(mToolbar);
        return view;
    }

    protected abstract void onCreatePanelView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);

    @StringRes
    protected abstract int getTitleRes();

    protected void onSetupFloatingActionButton(FloatingActionButton floatingActionButton) {
        if (floatingActionButton != null) {
            if (isFloatingActionButtonEnabled()) {
                floatingActionButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        onFloatingActionButtonClick();
                    }

                });
            } else {
                floatingActionButton.setVisibility(View.GONE);
            }
        }
    }

    protected void setupPrimaryToolbar(Toolbar toolbar) {
        // setup toolbar title and menu (if provided)
        toolbar.setTitle(getTitleRes());
        int menuResId = onInflateMenu();
        if (menuResId > 0) {
            toolbar.inflateMenu(menuResId);
            toolbar.setOnMenuItemClickListener(this);
            onMenuCreated(toolbar.getMenu());
        }
        // attach toolbar to the activity
        Activity activity = getActivity();
        if (activity instanceof ToolbarController) {
            ((ToolbarController) activity).setToolbar(toolbar);
        }
    }

    protected void onMenuCreated(Menu menu) {

    }

    protected void setToolbarSubtitle(String subtitle) {
        if (mToolbar != null) {
            mToolbar.setSubtitle(subtitle);
        }
    }

    @MenuRes
    protected int onInflateMenu() {
        return 0;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    protected boolean isFloatingActionButtonEnabled() {
        return true;
    }

    protected void onFloatingActionButtonClick() {
        // override this method if you have to handle the floating action button click event
    }
}