package com.oriondev.moneywallet.ui.activity.base;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.activity.ToolbarController;
import com.oriondev.moneywallet.ui.fragment.base.MultiPanelFragment;


public abstract class MultiPanelActivity extends BaseActivity implements ToolbarController {

    private MultiPanelFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_container);
        FragmentManager fragmentManager = getSupportFragmentManager();
        String tag = getMultiPanelFragmentTag();
        mFragment = (MultiPanelFragment) fragmentManager.findFragmentByTag(tag);
        if (mFragment != null) {
            fragmentManager.beginTransaction().show(mFragment).commit();
        } else {
            mFragment = onCreateMultiPanelFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mFragment, tag)
                    .commit();
        }
    }

    protected abstract MultiPanelFragment onCreateMultiPanelFragment();

    protected abstract String getMultiPanelFragmentTag();

    @Override
    public void onBackPressed() {
        if (mFragment != null && !mFragment.navigateBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }

        });
    }
}