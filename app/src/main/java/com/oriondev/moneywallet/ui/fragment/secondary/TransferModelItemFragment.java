package com.oriondev.moneywallet.ui.fragment.secondary;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.model.CurrencyUnit;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.storage.database.DataContentProvider;
import com.oriondev.moneywallet.ui.activity.NewEditItemActivity;
import com.oriondev.moneywallet.ui.activity.NewEditTransferModelActivity;
import com.oriondev.moneywallet.ui.fragment.base.SecondaryPanelFragment;
import com.oriondev.moneywallet.ui.view.theme.ThemedDialog;
import com.oriondev.moneywallet.utils.CurrencyManager;
import com.oriondev.moneywallet.utils.MoneyFormatter;


public class TransferModelItemFragment extends SecondaryPanelFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TRANSFER_MODEL_LOADER_ID = 53326;

    private View mProgressLayout;
    private View mMainLayout;

    private TextView mCurrencyTextView;
    private TextView mMoneyTextView;

    private TextView mDescriptionTextView;
    private TextView mWalletFromTextView;
    private TextView mWalletToTextView;
    private TextView mTaxTextView;
    private TextView mEventTextView;
    private TextView mPlaceTextView;
    private TextView mNoteTextView;
    private CheckBox mConfirmedCheckBox;
    private CheckBox mCountInTotalCheckBox;

    private MoneyFormatter mMoneyFormatter = MoneyFormatter.getInstance();

    @Override
    protected void onCreateHeaderView(LayoutInflater inflater, @NonNull ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_header_show_money_item, parent, true);
        mCurrencyTextView = view.findViewById(R.id.currency_text_view);
        mMoneyTextView = view.findViewById(R.id.money_text_view);
    }

    @Override
    protected void onCreateBodyView(LayoutInflater inflater, @NonNull ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_panel_show_transfer_model_item, parent, true);
        mProgressLayout = view.findViewById(R.id.secondary_panel_progress_wheel);
        mMainLayout = view.findViewById(R.id.secondary_panel_layout);
        mDescriptionTextView = view.findViewById(R.id.description_text_view);
        mWalletFromTextView = view.findViewById(R.id.wallet_from_text_view);
        mWalletToTextView = view.findViewById(R.id.wallet_to_text_view);
        mTaxTextView = view.findViewById(R.id.tax_text_view);
        mEventTextView = view.findViewById(R.id.event_text_view);
        mPlaceTextView = view.findViewById(R.id.place_text_view);
        mNoteTextView = view.findViewById(R.id.note_text_view);
        mConfirmedCheckBox = view.findViewById(R.id.confirmed_checkbox);
        mCountInTotalCheckBox = view.findViewById(R.id.count_in_total_checkbox);
    }

    @Override
    protected String getTitle() {
        return getString(R.string.title_fragment_item_model);
    }

    @Override
    protected int onInflateMenu() {
        return R.menu.menu_edit_delete_item;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_item:
                Intent intent = new Intent(getActivity(), NewEditTransferModelActivity.class);
                intent.putExtra(NewEditItemActivity.MODE, NewEditItemActivity.Mode.EDIT_ITEM);
                intent.putExtra(NewEditItemActivity.ID, getItemId());
                startActivity(intent);
                break;
            case R.id.action_delete_item:
                showDeleteDialog(getActivity());
                break;
        }
        return false;
    }

    private void showDeleteDialog(Context context) {
        ThemedDialog.buildMaterialDialog(context)
                .title(R.string.title_warning)
                .content(R.string.message_delete_transfer_model)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Activity activity = getActivity();
                        if (activity != null) {
                            Uri uri = ContentUris.withAppendedId(DataContentProvider.CONTENT_TRANSFER_MODELS, getItemId());
                            ContentResolver contentResolver = activity.getContentResolver();
                            contentResolver.delete(uri, null, null);
                            navigateBackSafely();
                            showItemId(0L);
                        }
                    }

                })
                .show();
    }

    @Override
    protected void onShowItemId(long itemId) {
        setLoadingScreen(true);
        getLoaderManager().restartLoader(TRANSFER_MODEL_LOADER_ID, null, this);
    }

    private void setLoadingScreen(boolean loading) {
        if (loading) {
            mMoneyTextView.setText(null);
            mCurrencyTextView.setText(null);
            mDescriptionTextView.setText(null);
            mWalletFromTextView.setText(null);
            mWalletToTextView.setText(null);
            mTaxTextView.setText(null);
            mEventTextView.setText(null);
            mPlaceTextView.setText(null);
            mNoteTextView.setText(null);
            mConfirmedCheckBox.setText(null);
            mCountInTotalCheckBox.setText(null);
            mProgressLayout.setVisibility(View.VISIBLE);
            mMainLayout.setVisibility(View.GONE);
        } else {
            mProgressLayout.setVisibility(View.GONE);
            mMainLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Activity activity = getActivity();
        if (activity != null) {
            Uri uri = ContentUris.withAppendedId(DataContentProvider.CONTENT_TRANSFER_MODELS, getItemId());
            String[] projection = new String[] {
                    "*"
            };
            return new CursorLoader(getActivity(), uri, projection, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            String iso = cursor.getString(cursor.getColumnIndex(Contract.TransferModel.WALLET_FROM_CURRENCY));
            CurrencyUnit currency = CurrencyManager.getCurrency(iso);
            if (currency != null) {
                mCurrencyTextView.setText(currency.getSymbol());
            } else {
                mCurrencyTextView.setText("?");
            }
            long money = cursor.getLong(cursor.getColumnIndex(Contract.TransferModel.MONEY_FROM));
            mMoneyTextView.setText(mMoneyFormatter.getNotTintedString(currency, money, MoneyFormatter.CurrencyMode.ALWAYS_HIDDEN));
            String description = cursor.getString(cursor.getColumnIndex(Contract.TransferModel.DESCRIPTION));
            if (!TextUtils.isEmpty(description)) {
                mDescriptionTextView.setText(description);
                mDescriptionTextView.setVisibility(View.VISIBLE);
            } else {
                mDescriptionTextView.setVisibility(View.GONE);
            }
            String walletFrom = cursor.getString(cursor.getColumnIndex(Contract.TransferModel.WALLET_FROM_NAME));
            String walletTo = cursor.getString(cursor.getColumnIndex(Contract.TransferModel.WALLET_TO_NAME));
            mWalletFromTextView.setText(walletFrom);
            mWalletToTextView.setText(walletTo);
            if (cursor.getLong(cursor.getColumnIndex(Contract.TransferModel.MONEY_TAX)) > 0L) {
                long tax = cursor.getLong(cursor.getColumnIndex(Contract.TransferModel.MONEY_TAX));
                mTaxTextView.setText(mMoneyFormatter.getNotTintedString(currency, tax));
                mTaxTextView.setVisibility(View.VISIBLE);
            } else {
                mTaxTextView.setVisibility(View.GONE);
            }
            String event = cursor.getString(cursor.getColumnIndex(Contract.TransferModel.EVENT_NAME));
            if (!TextUtils.isEmpty(event)) {
                mEventTextView.setText(event);
                mEventTextView.setVisibility(View.VISIBLE);
            } else {
                mEventTextView.setVisibility(View.GONE);
            }
            String place = cursor.getString(cursor.getColumnIndex(Contract.TransferModel.PLACE_NAME));
            if (!TextUtils.isEmpty(place)) {
                mPlaceTextView.setText(place);
                mPlaceTextView.setVisibility(View.VISIBLE);
            } else {
                mPlaceTextView.setVisibility(View.GONE);
            }
            String note = cursor.getString(cursor.getColumnIndex(Contract.TransferModel.NOTE));
            if (!TextUtils.isEmpty(note)) {
                mNoteTextView.setText(note);
                mNoteTextView.setVisibility(View.VISIBLE);
            } else {
                mNoteTextView.setVisibility(View.GONE);
            }
            mConfirmedCheckBox.setChecked(cursor.getInt(cursor.getColumnIndex(Contract.TransferModel.CONFIRMED)) == 1);
            mCountInTotalCheckBox.setChecked(cursor.getInt(cursor.getColumnIndex(Contract.TransferModel.COUNT_IN_TOTAL)) == 1);
        } else {
            showItemId(0L);
        }
        setLoadingScreen(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // nothing to release
    }
}