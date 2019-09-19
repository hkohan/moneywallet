package com.oriondev.moneywallet.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oriondev.moneywallet.R;



public abstract class MultiPanelViewPagerFragment extends MultiPanelAppBarFragment implements ViewPager.OnPageChangeListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreatePrimaryAppBar(LayoutInflater inflater, @NonNull ViewGroup primaryAppBar, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_multi_panel_view_pager_app_bar, primaryAppBar, true);
        mTabLayout = view.findViewById(R.id.tab_layout);
    }

    @Override
    protected void onCreatePrimaryPanel(LayoutInflater inflater, @NonNull ViewGroup primaryPanel, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_multi_panel_view_pager_body, primaryPanel, true);
        mViewPager = view.findViewById(R.id.view_pager);
        // setup the view pager and the tab layout
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(onCreatePagerAdapter(getChildFragmentManager()));
        mViewPager.addOnPageChangeListener(this);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onCreateSecondaryPanel(LayoutInflater inflater, @NonNull ViewGroup secondaryPanel, @Nullable Bundle savedInstanceState) {

    }

    @NonNull
    protected abstract PagerAdapter onCreatePagerAdapter(FragmentManager fragmentManager);

    protected int getViewPagerPosition() {
        return mViewPager.getCurrentItem();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(this);
    }
}