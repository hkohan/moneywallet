package com.oriondev.moneywallet.ui.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.ui.fragment.primary.DebtListFragment;


public class DebtViewPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public DebtViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DebtListFragment.newInstance(Contract.DebtType.DEBT);
            case 1:
                return DebtListFragment.newInstance(Contract.DebtType.CREDIT);
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
                return mContext.getString(R.string.menu_debt_tab_debt);
            case 1:
                return mContext.getString(R.string.menu_debt_tab_credit);
        }
        return null;
    }
}