package com.oriondev.moneywallet.ui.fragment.multipanel;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.storage.database.DataContentProvider;
import com.oriondev.moneywallet.ui.activity.MapActivity;
import com.oriondev.moneywallet.ui.activity.NewEditItemActivity;
import com.oriondev.moneywallet.ui.activity.NewEditPlaceActivity;
import com.oriondev.moneywallet.ui.adapter.recycler.AbstractCursorAdapter;
import com.oriondev.moneywallet.ui.adapter.recycler.PlaceCursorAdapter;
import com.oriondev.moneywallet.ui.fragment.base.MultiPanelCursorListItemFragment;
import com.oriondev.moneywallet.ui.fragment.base.SecondaryPanelFragment;
import com.oriondev.moneywallet.ui.fragment.secondary.PlaceItemFragment;
import com.oriondev.moneywallet.ui.view.AdvancedRecyclerView;


public class PlaceMultiPanelFragment extends MultiPanelCursorListItemFragment implements PlaceCursorAdapter.ActionListener {

    private static final String SECONDARY_FRAGMENT_TAG = "PlaceMultiPanelFragment::Tag::SecondaryPanelFragment";

    @Override
    protected void onPrepareRecyclerView(AdvancedRecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setEmptyText(R.string.message_no_place_found);
    }

    @Override
    protected AbstractCursorAdapter onCreateAdapter() {
        return new PlaceCursorAdapter(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Activity activity = getActivity();
        if (activity != null) {
            Uri uri = DataContentProvider.CONTENT_PLACES;
            String[] projection = new String[] {
                    Contract.Place.ID,
                    Contract.Place.NAME,
                    Contract.Place.ICON,
                    Contract.Place.ADDRESS
            };
            String sortOrder = Contract.Place.NAME + " DESC";
            return new CursorLoader(activity, uri, projection, null, null, sortOrder);
        }
        return null;
    }

    @Override
    protected SecondaryPanelFragment onCreateSecondaryPanel() {
        return new PlaceItemFragment();
    }

    @Override
    protected String getSecondaryFragmentTag() {
        return SECONDARY_FRAGMENT_TAG;
    }

    @Override
    protected int getTitleRes() {
        return R.string.menu_place;
    }

    @MenuRes
    protected int onInflateMenu() {
        return R.menu.menu_place_multipanel_fragment;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_map:
                startActivity(new Intent(getActivity(), MapActivity.class));
                break;
        }
        return false;
    }

    @Override
    public void onPlaceClick(long id) {
        showItemId(id);
        showSecondaryPanel();
    }

    @Override
    protected void onFloatingActionButtonClick() {
        Intent intent = new Intent(getActivity(), NewEditPlaceActivity.class);
        intent.putExtra(NewEditItemActivity.MODE, NewEditItemActivity.Mode.NEW_ITEM);
        startActivity(intent);
    }
}