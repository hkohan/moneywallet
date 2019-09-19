package com.oriondev.moneywallet.ui.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.fragment.primary.RecurrentTransactionListFragment;
import com.oriondev.moneywallet.ui.fragment.primary.RecurrentTransferListFragment;


public class RecurrenceViewPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public RecurrenceViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RecurrentTransactionListFragment();
            case 1:
                return new RecurrentTransferListFragment();
            default:
                throw new IllegalArgumentException("Invalid adapter position: " + position);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.menu_transaction_tab_transactions);
            case 1:
                return mContext.getString(R.string.menu_transaction_tab_transfers);
        }
        return null;
    }
}