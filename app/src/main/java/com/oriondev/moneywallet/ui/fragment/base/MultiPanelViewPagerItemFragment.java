package com.oriondev.moneywallet.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;


public abstract class MultiPanelViewPagerItemFragment extends MultiPanelViewPagerFragment {

    private SecondaryPanelFragment mSecondaryFragment;

    @Override
    protected void onCreateSecondaryPanel(LayoutInflater inflater, @NonNull ViewGroup secondaryPanel, @Nullable Bundle savedInstanceState) {
        FragmentManager fragmentManager = getChildFragmentManager();
        String fragmentTag = getSecondaryFragmentTag();
        mSecondaryFragment = (SecondaryPanelFragment) fragmentManager.findFragmentByTag(fragmentTag);
        if (mSecondaryFragment != null) {
            fragmentManager.beginTransaction().show(mSecondaryFragment).commitNow();
        } else {
            mSecondaryFragment = onCreateSecondaryPanel();
            fragmentManager.beginTransaction()
                    .replace(secondaryPanel.getId(), mSecondaryFragment, fragmentTag)
                    .commitNow();
        }
    }

    protected abstract SecondaryPanelFragment onCreateSecondaryPanel();

    protected abstract String getSecondaryFragmentTag();

    public void showItemId(long id) {
        if (mSecondaryFragment != null) {
            mSecondaryFragment.showItemId(id);
        }
    }
}