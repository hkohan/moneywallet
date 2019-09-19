package com.oriondev.moneywallet.ui.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.fragment.primary.BudgetListFragment;


public class BudgetViewPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public BudgetViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return BudgetListFragment.newInstance(position == 1);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.menu_budget_tab_running);
            case 1:
                return mContext.getString(R.string.menu_budget_tab_expired);
        }
        return null;
    }
}