package com.oriondev.moneywallet.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.background.IconGroupLoader;
import com.oriondev.moneywallet.model.Icon;
import com.oriondev.moneywallet.model.IconGroup;
import com.oriondev.moneywallet.ui.activity.base.SinglePanelActivity;
import com.oriondev.moneywallet.ui.adapter.recycler.IconAdapter;
import com.oriondev.moneywallet.ui.view.AdvancedRecyclerView;

import java.util.List;


public class IconListActivity extends SinglePanelActivity implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<List<IconGroup>>, IconAdapter.Controller {

    public static final String RESULT_ICON = "IconListActivity::Result::SelectedIcon";

    private static final int ICON_LOADER_ID = 46;

    private static final int ICON_WIDTH_DP = 64;

    private AdvancedRecyclerView mAdvancedRecyclerView;
    private IconAdapter mAdapter;

    private int mIconSpan;

    @Override
    protected void onCreatePanelView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_activity_single_panel_body_list, parent, true);
        mAdvancedRecyclerView = view.findViewById(R.id.advanced_recycler_view);
        mIconSpan = getIconSpanCount();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, mIconSpan);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                return mAdapter.isHeader(position) ? mIconSpan : 1;
            }

        });
        mAdvancedRecyclerView.setLayoutManager(gridLayoutManager);
        mAdvancedRecyclerView.setEmptyText(R.string.message_no_icon_found);
        mAdapter = new IconAdapter(this);
        mAdvancedRecyclerView.setAdapter(mAdapter);
        mAdvancedRecyclerView.setOnRefreshListener(this);
        mAdvancedRecyclerView.setState(AdvancedRecyclerView.State.LOADING);
    }

    private int getIconSpanCount() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        // on large screen the panel width is not equal to the display width
        // so we have to dynamically calculate the panel width
        int spanCount;
        if (dpWidth < 600) {
            // small screen, probably a smart phone
            spanCount = (int) (dpWidth / ICON_WIDTH_DP);
        } else if (dpWidth >= 600 && dpWidth < 840) {
            // small tablet screen, the max panel width is 540dp
            spanCount = 540 / ICON_WIDTH_DP;
        } else {
            // large tablet screen, the max panel width is 640dp
            spanCount = 640 / ICON_WIDTH_DP;
        }
        return spanCount;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        getSupportLoaderManager().restartLoader(ICON_LOADER_ID, null, this);
    }

    @Override
    protected int getActivityTitleRes() {
        return R.string.title_activity_icon_list;
    }

    @Override
    protected boolean isFloatingActionButtonEnabled() {
        return false;
    }

    @Override
    public void onIconClick(Icon icon) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_ICON, icon);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onRefresh() {
        getSupportLoaderManager().restartLoader(ICON_LOADER_ID, null, this);
        mAdvancedRecyclerView.setState(AdvancedRecyclerView.State.REFRESHING);
    }

    @NonNull
    @Override
    public Loader<List<IconGroup>> onCreateLoader(int id, Bundle args) {
        return new IconGroupLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<IconGroup>> loader, List<IconGroup> iconGroups) {
        mAdapter.setIconGroups(iconGroups);
        if (iconGroups != null && iconGroups.size() > 0) {
            mAdvancedRecyclerView.setState(AdvancedRecyclerView.State.READY);
        } else {
            mAdvancedRecyclerView.setState(AdvancedRecyclerView.State.EMPTY);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<IconGroup>> loader) {
        // nothing to release
    }
}