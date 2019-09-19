package com.oriondev.moneywallet.ui.fragment.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.activity.DrawerController;
import com.oriondev.moneywallet.ui.activity.ToolbarController;
import com.oriondev.moneywallet.utils.Utils;

public abstract class MultiPanelFragment extends Fragment implements MultiPanelController, Toolbar.OnMenuItemClickListener {

    private static final String SAVED_STATE_SECONDARY_PANEL_VISIBLE = "MultiPanelFragment::SecondaryPanelVisible";

    private Toolbar mPrimaryToolbar;

    private ViewGroup mPrimaryPanel;
    private ViewGroup mSecondaryPanel;
    private ViewGroup mPrimaryPanelBodyContainer;
    private FloatingActionButton mFloatingActionButton;
    private boolean mExtendedLayout;
    private boolean mSecondaryPanelVisible;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onInflateRootLayout(inflater, container, savedInstanceState);
        onSetupRootLayout(view);
        onConfigureRootLayout(inflater, container, savedInstanceState);
        setupPrimaryToolbar(mPrimaryToolbar);
        setupPanelVisibility(savedInstanceState);
        return view;
    }

    protected View onInflateRootLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_multi_panel, container, false);
    }

    protected void onSetupRootLayout(View view) {
        mPrimaryToolbar = view.findViewById(R.id.primary_toolbar);
        mPrimaryPanel = Utils.findViewGroupByIds(view,
                R.id.primary_panel_constraint_layout,
                R.id.primary_panel_card_view,
                R.id.primary_panel_coordinator_layout
        );
        mSecondaryPanel = Utils.findViewGroupByIds(view,
                R.id.secondary_panel_frame_layout,
                R.id.secondary_panel_card_view
        );
        mPrimaryPanelBodyContainer = Utils.findViewGroupByIds(view,
                R.id.primary_panel_body_container_frame_layout,
                R.id.primary_panel_body_container_card_view
        );
        mExtendedLayout = view.findViewById(R.id.half_screen_vertical_guideline) != null; // TODO find a way to identify the layout
        mFloatingActionButton = view.findViewById(R.id.floating_action_button);
        if (mFloatingActionButton != null) {
            if (isFloatingActionButtonEnabled()) {
                mFloatingActionButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        onFloatingActionButtonClick();
                    }

                });
            } else {
                mFloatingActionButton.setVisibility(View.GONE);
            }
        }
    }

    protected void onConfigureRootLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup primaryContainer = mPrimaryPanelBodyContainer != null ? mPrimaryPanelBodyContainer : mPrimaryPanel;
        onCreatePrimaryPanel(inflater, primaryContainer, savedInstanceState);
        onCreateSecondaryPanel(inflater, mSecondaryPanel, savedInstanceState);
    }

    protected abstract void onCreatePrimaryPanel(LayoutInflater inflater, @NonNull ViewGroup primaryPanel, @Nullable Bundle savedInstanceState);

    protected abstract void onCreateSecondaryPanel(LayoutInflater inflater, @NonNull ViewGroup secondaryPanel, @Nullable Bundle savedInstanceState);

    protected void setupPrimaryToolbar(Toolbar toolbar) {
        // setup toolbar title and menu (if provided)
        toolbar.setTitle(getTitleRes());
        int menuResId = onInflateMenu();
        if (menuResId > 0) {
            toolbar.inflateMenu(menuResId);
            toolbar.setOnMenuItemClickListener(this);
        }
        // attach toolbar to the activity
        Activity activity = getActivity();
        if (activity instanceof ToolbarController) {
            ((ToolbarController) activity).setToolbar(toolbar);
        }
    }

    /**
     * Block the navigation drawer if a drawer controller is registered in the background activity.
     * @param locked state to apply to the drawer.
     */
    protected void setDrawerLocked(boolean locked) {
        Activity activity = getActivity();
        if (activity instanceof DrawerController) {
            ((DrawerController) activity).setDrawerLockMode(
                    locked ? DrawerLayout.LOCK_MODE_LOCKED_CLOSED : DrawerLayout.LOCK_MODE_UNLOCKED
            );
        }
    }

    private void setupPanelVisibility(@Nullable Bundle savedInstanceState) {
        if (mExtendedLayout) {
            // both panels should be visible
            mPrimaryPanel.setVisibility(View.VISIBLE);
            mSecondaryPanel.setVisibility(View.VISIBLE);
            // restore the flag of the secondary panel visibility
            mSecondaryPanelVisible = savedInstanceState != null && savedInstanceState.getBoolean(SAVED_STATE_SECONDARY_PANEL_VISIBLE, false);
            // register the navigation drawer as unlocked
            setDrawerLocked(false);
        } else {
            if (savedInstanceState != null) {
                // take a look at previous state and check if details panel was visible
                if (savedInstanceState.getBoolean(SAVED_STATE_SECONDARY_PANEL_VISIBLE, false)) {
                    showSecondaryPanel();
                } else {
                    hideSecondaryPanel();
                }
            } else {
                // this is a small screen (only one panel at time can be visible) and not exists a
                // previous state to look for: make visible only the primary panel.
                hideSecondaryPanel();
            }
        }
    }

    protected void showSecondaryPanel() {
        mSecondaryPanelVisible = true;
        if (!mExtendedLayout) {
            mPrimaryPanel.setVisibility(View.GONE);
            mSecondaryPanel.setVisibility(View.VISIBLE);
            // lock the navigation drawer
            setDrawerLocked(true);
        }
    }

    protected void hideSecondaryPanel() {
        if (!mExtendedLayout) {
            mSecondaryPanelVisible = false;
            mPrimaryPanel.setVisibility(View.VISIBLE);
            mSecondaryPanel.setVisibility(View.GONE);
            // unlock the navigation drawer
            setDrawerLocked(false);
        }
    }

    protected Toolbar getPrimaryToolbar() {
        return mPrimaryToolbar;
    }

    protected ViewGroup getPrimaryPanel() {
        return mPrimaryPanel;
    }

    @StringRes
    protected abstract int getTitleRes();

    @MenuRes
    protected int onInflateMenu() {
        return 0;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    /**
     * Check if we are running using an extended layout.
     * @return true if is an extended layout, false otherwise.
     */
    @Override
    public boolean isExtendedLayout() {
        return mExtendedLayout;
    }

    protected boolean isFloatingActionButtonEnabled() {
        return true;
    }

    protected void onFloatingActionButtonClick() {
        // override this method if you need to intercept the fab click
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_STATE_SECONDARY_PANEL_VISIBLE, mSecondaryPanelVisible);
    }

    @Override
    public boolean navigateBack() {
        if (!mExtendedLayout) {
            boolean visible = mSecondaryPanel.getVisibility() == View.VISIBLE;
            hideSecondaryPanel();
            return visible;
        }
        return false;
    }
}
