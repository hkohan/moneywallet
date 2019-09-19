package com.oriondev.moneywallet.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.utils.Utils;


public abstract class SinglePanelActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    private Toolbar mToolbar;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInflateRootLayout();
        onSetupRootLayout();
        onConfigureRootLayout(savedInstanceState);
        onSetupFloatingActionButton(mFloatingActionButton);
        onViewCreated(savedInstanceState);
        setupToolbar();
    }

    protected void onInflateRootLayout() {
        setContentView(R.layout.activity_single_panel);
    }

    protected void onSetupRootLayout() {
        mToolbar = findViewById(R.id.primary_toolbar);
        mFloatingActionButton = findViewById(R.id.floating_action_button);
    }

    private void setupToolbar() {
        int titleRes = getActivityTitleRes();
        if (titleRes > 0) {
            mToolbar.setTitle(getActivityTitleRes());
        }
        int iconRes = getNavigationIcon();
        if (iconRes > 0) {
            mToolbar.setNavigationIcon(getNavigationIcon());
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    onBackPressed();
                }

            });
        }
        int menuRes = onInflateMenu();
        if (menuRes > 0) {
            mToolbar.inflateMenu(menuRes);
            mToolbar.setOnMenuItemClickListener(this);
            onMenuCreated(mToolbar.getMenu());
        }
        onToolbarReady(mToolbar);
    }

    protected void onConfigureRootLayout(Bundle savedInstanceState) {
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup parent = Utils.findViewGroupByIds(this,
                R.id.primary_panel_container_frame_layout,
                R.id.primary_panel_container_card_view,
                R.id.primary_panel_container_linear_layout,
                R.id.primary_panel_container_coordinator_layout
        );
        onCreatePanelView(inflater, parent, savedInstanceState);
    }

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

    protected abstract void onCreatePanelView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);

    protected void onViewCreated(Bundle savedInstanceState) {

    }

    protected void onMenuCreated(Menu menu) {

    }

    protected void onToolbarReady(Toolbar toolbar) {

    }

    @StringRes
    protected abstract int getActivityTitleRes();

    protected void setToolbarSubtitle(String subtitle) {
        if (mToolbar != null) {
            mToolbar.setSubtitle(subtitle);
        }
    }

    @DrawableRes
    protected int getNavigationIcon() {
        return R.drawable.ic_arrow_back_black_24dp;
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