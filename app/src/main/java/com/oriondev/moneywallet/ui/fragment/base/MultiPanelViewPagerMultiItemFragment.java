package com.oriondev.moneywallet.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;


public abstract class MultiPanelViewPagerMultiItemFragment extends MultiPanelViewPagerFragment {

    private static final String SS_INDEX = "MultiPanelViewPagerMultiItemFragment::SavedState::CurrentIndex";

    private int mContainerId;

    private int mIndex;

    private SecondaryPanelFragment mSecondaryFragment;

    @Override
    protected void onCreateSecondaryPanel(LayoutInflater inflater, @NonNull ViewGroup secondaryPanel, @Nullable Bundle savedInstanceState) {
        mContainerId = secondaryPanel.getId();
        if (savedInstanceState != null) {
            replaceSecondaryFragment(savedInstanceState.getInt(SS_INDEX, 0));
        } else {
            replaceSecondaryFragment(0);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SS_INDEX, mIndex);
    }

    protected abstract SecondaryPanelFragment onCreateSecondaryPanel(int index);

    protected abstract String getSecondaryFragmentTag(int index);

    public void showItemId(int index, long id) {
        if (index != mIndex) {
            replaceSecondaryFragment(index);
        }
        mSecondaryFragment.showItemId(id);
        showSecondaryPanel();
    }

    private void replaceSecondaryFragment(int index) {
        String tag = getSecondaryFragmentTag(index);
        FragmentManager fragmentManager = getChildFragmentManager();
        mSecondaryFragment = (SecondaryPanelFragment) fragmentManager.findFragmentByTag(tag);
        if (mSecondaryFragment != null) {
            fragmentManager.beginTransaction().show(mSecondaryFragment).commitNow();
        } else {
            mSecondaryFragment = onCreateSecondaryPanel(index);
            fragmentManager.beginTransaction()
                    .replace(mContainerId, mSecondaryFragment, tag)
                    .commitNow();
        }
        mIndex = index;
    }
}