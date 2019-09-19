package com.oriondev.moneywallet.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.model.CurrencyUnit;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.storage.database.DataContentProvider;
import com.oriondev.moneywallet.ui.activity.base.SinglePanelSimpleListActivity;
import com.oriondev.moneywallet.ui.adapter.recycler.AbstractCursorAdapter;
import com.oriondev.moneywallet.ui.adapter.recycler.CurrencyCursorAdapter;
import com.oriondev.moneywallet.ui.view.AdvancedRecyclerView;
import com.oriondev.moneywallet.utils.CurrencyManager;


public class CurrencyListActivity extends SinglePanelSimpleListActivity implements CurrencyCursorAdapter.CurrencyActionListener {

    public static final String ACTIVITY_MODE = "CurrencyListActivity::ActivityMode";
    public static final String RESULT_CURRENCY = "CurrencyListActivity::Result::SelectedCurrency";

    public static final int CURRENCY_MANAGER = 0;
    public static final int CURRENCY_PICKER = 1;

    private int mActivityMode;

    @Override
    protected void onPrepareRecyclerView(AdvancedRecyclerView recyclerView) {
        Intent intent = getIntent();
        if (intent != null) {
            mActivityMode = intent.getIntExtra(ACTIVITY_MODE, CURRENCY_MANAGER);
        } else {
            mActivityMode = CURRENCY_MANAGER;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setEmptyText(R.string.message_no_currency_found);
    }

    @Override
    protected AbstractCursorAdapter onCreateAdapter() {
        return new CurrencyCursorAdapter(this);
    }

    @Override
    @StringRes
    protected int getActivityTitleRes() {
        return R.string.title_activity_currency_list;
    }

    @Override
    protected boolean isFloatingActionButtonEnabled() {
        return mActivityMode == CURRENCY_MANAGER;
    }

    @Override
    protected void onFloatingActionButtonClick() {
        Intent intent = new Intent(this, NewEditCurrencyActivity.class);
        intent.putExtra(NewEditCurrencyActivity.MODE, NewEditCurrencyActivity.Mode.NEW_ITEM);
        startActivity(intent);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = DataContentProvider.CONTENT_CURRENCIES;
        String[] projection = new String[] {
                Contract.Currency.ISO,
                Contract.Currency.NAME,
                Contract.Currency.SYMBOL,
                Contract.Currency.DECIMALS,
                Contract.Currency.FAVOURITE
        };
        String sortBy = Contract.Currency.NAME;
        return new CursorLoader(this, uri, projection, null, null, sortBy);
    }

    @Override
    public void onCurrencyClick(String iso) {
        if (mActivityMode == CURRENCY_MANAGER) {
            Intent intent = new Intent(this, NewEditCurrencyActivity.class);
            intent.putExtra(NewEditCurrencyActivity.MODE, NewEditCurrencyActivity.Mode.EDIT_ITEM);
            intent.putExtra(NewEditCurrencyActivity.ISO, iso);
            startActivity(intent);
        } else if (mActivityMode == CURRENCY_PICKER) {
            CurrencyUnit currency = CurrencyManager.getCurrency(iso);
            Intent intent = new Intent();
            intent.putExtra(RESULT_CURRENCY, currency);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onCurrencyFavourite(String iso, boolean newValue) {
        // TODO handle it properly: if we update the database directly than the uri will be notify
        // TODO to be changed and the content provider will re-query the database. At the end the
        // TODO adapter will be refreshed and the new item will be at the top of the list.
        // TODO we need a wrapper to keep the data static until a refresh occurs.
    }
}