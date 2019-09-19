package com.oriondev.moneywallet.ui.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.fragment.single.PeriodDetailFlowFragment;
import com.oriondev.moneywallet.ui.fragment.single.PeriodDetailSummaryFragment;

import java.util.Date;


public class PeriodViewPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private final Date mStartDate;
    private final Date mEndDate;

    public PeriodViewPagerAdapter(FragmentManager fm, Context context, Date startDate, Date endDate) {
        super(fm);
        mContext = context;
        mStartDate = startDate;
        mEndDate = endDate;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return PeriodDetailFlowFragment.newInstance(mStartDate, mEndDate, true);
            case 1:
                return PeriodDetailFlowFragment.newInstance(mStartDate, mEndDate, false);
            case 2:
                return PeriodDetailSummaryFragment.newInstance(mStartDate, mEndDate);
            default:
                throw new IllegalArgumentException("Invalid position");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.hint_incomes);
            case 1:
                return mContext.getString(R.string.hint_expenses);
            case 2:
                return mContext.getString(R.string.hint_summary);
        }
        return null;
    }
}