package com.oriondev.moneywallet.ui.activity;

import com.oriondev.moneywallet.ui.activity.base.MultiPanelActivity;
import com.oriondev.moneywallet.ui.fragment.base.MultiPanelFragment;
import com.oriondev.moneywallet.ui.fragment.multipanel.WalletMultiPanelFragment;


public class WalletListActivity extends MultiPanelActivity {

    private static final String TAG_FRAGMENT_WALLET_LIST = "WalletListActivity::WalletMultiPanelFragment";

    @Override
    protected MultiPanelFragment onCreateMultiPanelFragment() {
        return new WalletMultiPanelFragment();
    }

    @Override
    protected String getMultiPanelFragmentTag() {
        return TAG_FRAGMENT_WALLET_LIST;
    }
}