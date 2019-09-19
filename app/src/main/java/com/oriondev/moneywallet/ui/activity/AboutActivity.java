package com.oriondev.moneywallet.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.activity.base.SinglePanelActivity;
import com.oriondev.moneywallet.ui.fragment.single.AboutFragment;


public class AboutActivity extends SinglePanelActivity {

    private static final String FRAGMENT_TAG = "AboutActivity::Tag::AboutFragment";

    @Override
    protected void onCreatePanelView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .replace(parent.getId(), new AboutFragment(), FRAGMENT_TAG)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .show(fragment)
                    .commit();
        }
    }

    @Override
    protected int getActivityTitleRes() {
        return R.string.title_activity_about;
    }

    @Override
    protected boolean isFloatingActionButtonEnabled() {
        return false;
    }
}