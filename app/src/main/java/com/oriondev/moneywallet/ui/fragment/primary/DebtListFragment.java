package com.oriondev.moneywallet.ui.fragment.primary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.broadcast.LocalAction;
import com.oriondev.moneywallet.broadcast.Message;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.storage.database.DataContentProvider;
import com.oriondev.moneywallet.storage.preference.PreferenceManager;
import com.oriondev.moneywallet.storage.wrapper.DebtHeaderCursor;
import com.oriondev.moneywallet.ui.activity.NewEditTransactionActivity;
import com.oriondev.moneywallet.ui.adapter.recycler.AbstractCursorAdapter;
import com.oriondev.moneywallet.ui.adapter.recycler.DebtCursorAdapter;
import com.oriondev.moneywallet.ui.fragment.base.CursorListFragment;
import com.oriondev.moneywallet.ui.view.AdvancedRecyclerView;



public class DebtListFragment extends CursorListFragment implements DebtCursorAdapter.ActionListener {

    private static final String ARG_DEBT_TYPE = "DebtListFragment::Arguments::DebtType";

    public static DebtListFragment newInstance(Contract.DebtType debtType) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_DEBT_TYPE, debtType);
        DebtListFragment fragment = new DebtListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void onPrepareRecyclerView(AdvancedRecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setEmptyText(R.string.message_no_debt_found);
    }

    @Override
    protected AbstractCursorAdapter onCreateAdapter() {
        return new DebtCursorAdapter(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Activity activity = getActivity();
        Bundle arguments = getArguments();
        if (activity != null && arguments != null) {
            Contract.DebtType debtType = (Contract.DebtType) arguments.getSerializable(ARG_DEBT_TYPE);
            if (debtType == null) {
                return null;
            }
            Uri uri = DataContentProvider.CONTENT_DEBTS;
            String[] projection = new String[] {
                    Contract.Debt.ID,
                    Contract.Debt.TYPE,
                    Contract.Debt.ICON,
                    Contract.Debt.DESCRIPTION,
                    Contract.Debt.MONEY,
                    Contract.Debt.PROGRESS,
                    Contract.Debt.WALLET_CURRENCY,
                    Contract.Debt.EXPIRATION_DATE,
                    Contract.Debt.ARCHIVED,
                    Contract.Debt.PLACE_ID,
                    Contract.Debt.PLACE_NAME
            };
            String selection;
            String[] selectionArgs;
            long currentWallet = PreferenceManager.getCurrentWallet();
            if (currentWallet == PreferenceManager.TOTAL_WALLET_ID) {
                selection = Contract.Debt.WALLET_COUNT_IN_TOTAL + " = 1";
                selectionArgs = null;
            } else {
                selection = Contract.Debt.WALLET_ID + " = ?";
                selectionArgs = new String[] {String.valueOf(currentWallet)};
            }
            selection += " AND " + Contract.Debt.TYPE + " = " + String.valueOf(debtType.getValue());
            String sortOrder = Contract.Debt.ARCHIVED + " ASC, " + Contract.Debt.DATE + " DESC";
            return new WrappedCursorLoader(activity, uri, projection, selection, selectionArgs, sortOrder, debtType);
        }
        return null;
    }

    private static class WrappedCursorLoader extends CursorLoader {

        private final Contract.DebtType mDebtType;

        private WrappedCursorLoader(@NonNull Context context, @NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                                    @Nullable String[] selectionArgs, @Nullable String sortOrder, Contract.DebtType debtType) {
            super(context, uri, projection, selection, selectionArgs, sortOrder);
            mDebtType = debtType;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = super.loadInBackground();
            return new DebtHeaderCursor(cursor, mDebtType);
        }
    }

    @Override
    public void onDebtClick(long id) {
        Activity activity = getActivity();
        if (activity != null) {
            Intent intent = new Intent(LocalAction.ACTION_ITEM_CLICK);
            intent.putExtra(Message.ITEM_ID, id);
            intent.putExtra(Message.ITEM_TYPE, Message.TYPE_DEBT);
            LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        }
    }

    @Override
    public void onPayClick(long id) {
        Intent intent = new Intent(getActivity(), NewEditTransactionActivity.class);
        intent.putExtra(NewEditTransactionActivity.TYPE, NewEditTransactionActivity.TYPE_DEBT);
        intent.putExtra(NewEditTransactionActivity.DEBT_ID, id);
        intent.putExtra(NewEditTransactionActivity.DEBT_ACTION, NewEditTransactionActivity.DEBT_PAY);
        startActivity(intent);
    }

    @Override
    public void onReceiveClick(long id) {
        Intent intent = new Intent(getActivity(), NewEditTransactionActivity.class);
        intent.putExtra(NewEditTransactionActivity.TYPE, NewEditTransactionActivity.TYPE_DEBT);
        intent.putExtra(NewEditTransactionActivity.DEBT_ID, id);
        intent.putExtra(NewEditTransactionActivity.DEBT_ACTION, NewEditTransactionActivity.DEBT_RECEIVE);
        startActivity(intent);
    }

    @Override
    protected boolean shouldRefreshOnCurrentWalletChange() {
        // this fragment content is dependant on the current
        // wallet when the loader is created, so the query
        // operation must be recreated from the beginning.
        return true;
    }
}