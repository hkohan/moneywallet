package com.oriondev.moneywallet.ui.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.fragment.primary.TransactionModelListFragment;
import com.oriondev.moneywallet.ui.fragment.primary.TransferModelListFragment;


public class ModelViewPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public ModelViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TransactionModelListFragment();
            case 1:
                return new TransferModelListFragment();
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
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
                return mContext.getString(R.string.menu_model_tab_transactions);
            case 1:
                return mContext.getString(R.string.menu_model_tab_transfers);
        }
        return null;
    }
}