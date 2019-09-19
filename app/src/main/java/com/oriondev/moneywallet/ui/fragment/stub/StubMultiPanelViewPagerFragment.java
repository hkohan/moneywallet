package com.oriondev.moneywallet.ui.fragment.stub;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.fragment.base.MultiPanelViewPagerFragment;


public class StubMultiPanelViewPagerFragment extends MultiPanelViewPagerFragment {

    @NonNull
    @Override
    protected PagerAdapter onCreatePagerAdapter(FragmentManager fragmentManager) {
        return new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return new StubListFragment();
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "Stub " + position;
            }
        };
    }

    @Override
    protected int getTitleRes() {
        return R.string.app_name;
    }
}