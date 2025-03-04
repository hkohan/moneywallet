package com.oriondev.moneywallet.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.utils.Utils;


public abstract class SecondaryPanelFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private static final String SS_ITEM_ID = "SecondaryPanelFragment::SavedState::ItemId";

    private ViewGroup mEmptyLayout;
    private ViewGroup mMainLayout;
    private Toolbar mToolbar;

    private long mCurrentId;

    private boolean mIsCreated = false;
    private long mCachedId = 0;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_secondary_panel_item, container, false);
        mEmptyLayout = view.findViewById(R.id.empty_screen_secondary_panel_layout);
        mMainLayout = Utils.findViewGroupByIds(view,
                R.id.main_screen_secondary_panel_scroll_view,
                R.id.main_screen_secondary_panel_linear_layout
        );
        ViewGroup headerLayout = view.findViewById(R.id.header_secondary_panel_layout);
        mToolbar = view.findViewById(R.id.secondary_toolbar);
        ViewGroup bodyLayout = view.findViewById(R.id.body_secondary_panel_layout);
        onCreateHeaderView(inflater, headerLayout, savedInstanceState);
        onCreateBodyView(inflater, bodyLayout, savedInstanceState);
        if (mToolbar != null) {
            if (getParentFragment() instanceof MultiPanelController && !((MultiPanelController) getParentFragment()).isExtendedLayout()) {
                mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        navigateBackSafely();
                    }

                });
            }
            mToolbar.setTitle(getTitle());
            int menuRes = onInflateMenu();
            if (menuRes > 0) {
                mToolbar.inflateMenu(menuRes);
                mToolbar.setOnMenuItemClickListener(this);
            }
        }
        mIsCreated = true;
        return view;
    }

    protected abstract void onCreateHeaderView(LayoutInflater inflater, @NonNull ViewGroup parent, @Nullable Bundle savedInstanceState);

    protected abstract void onCreateBodyView(LayoutInflater inflater, @NonNull ViewGroup parent, @Nullable Bundle savedInstanceState);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (mCachedId > 0) {
            showItemId(mCachedId);
            mCachedId = 0L;
        } else {
            if (savedInstanceState != null) {
                showItemId(savedInstanceState.getLong(SS_ITEM_ID, 0L));
            } else {
                showItemId(0L);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(SS_ITEM_ID, mCurrentId);
    }

    protected void navigateBackSafely() {
        if (getParentFragment() instanceof MultiPanelController) {
            ((MultiPanelController) getParentFragment()).navigateBack();
        }
    }

    public void showItemId(long itemId) {
        if (mIsCreated) {
            mCurrentId = itemId;
            if (mCurrentId > 0L) {
                mEmptyLayout.setVisibility(View.GONE);
                mMainLayout.setVisibility(View.VISIBLE);
                onShowItemId(mCurrentId);
            } else {
                mEmptyLayout.setVisibility(View.VISIBLE);
                mMainLayout.setVisibility(View.GONE);
            }
        } else {
            mCachedId = itemId;
        }
    }

    protected abstract String getTitle();

    @MenuRes
    protected abstract int onInflateMenu();

    @Override
    public abstract boolean onMenuItemClick(MenuItem item);

    protected void setMenuItemVisibility(int id, boolean visible) {
        Menu menu = mToolbar.getMenu();
        if (menu != null) {
            MenuItem menuItem = menu.findItem(id);
            if (menuItem != null) {
                menuItem.setVisible(visible);
            }
        }
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    public long getItemId() {
        return mCurrentId;
    }

    protected abstract void onShowItemId(long itemId);
}