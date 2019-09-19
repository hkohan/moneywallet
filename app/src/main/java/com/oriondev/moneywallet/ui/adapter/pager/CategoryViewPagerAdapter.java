package com.oriondev.moneywallet.ui.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.ui.fragment.primary.CategoryListFragment;


public class CategoryViewPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private final boolean mShowSubCategories;
    private final boolean mShowSystemCategories;

    public CategoryViewPagerAdapter(FragmentManager fm, Context context, boolean showSubCategories, boolean showSystemCategories) {
        super(fm);
        mContext = context;
        mShowSubCategories = showSubCategories;
        mShowSystemCategories = showSystemCategories;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CategoryListFragment.newInstance(Contract.CategoryType.INCOME, mShowSubCategories);
            case 1:
                return CategoryListFragment.newInstance(Contract.CategoryType.EXPENSE, mShowSubCategories);
            case 2:
                return CategoryListFragment.newInstance(Contract.CategoryType.SYSTEM, mShowSubCategories);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mShowSystemCategories ? 3 : 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.menu_category_tab_income);
            case 1:
                return mContext.getString(R.string.menu_category_tab_expense);
            case 2:
                return mContext.getString(R.string.menu_category_tab_system);
        }
        return null;
    }
}