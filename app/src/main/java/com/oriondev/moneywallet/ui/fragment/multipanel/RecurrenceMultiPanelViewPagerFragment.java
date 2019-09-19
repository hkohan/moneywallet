package com.oriondev.moneywallet.ui.fragment.multipanel;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.broadcast.LocalAction;
import com.oriondev.moneywallet.broadcast.Message;
import com.oriondev.moneywallet.ui.activity.NewEditItemActivity;
import com.oriondev.moneywallet.ui.activity.NewEditRecurrentTransactionActivity;
import com.oriondev.moneywallet.ui.activity.NewEditRecurrentTransferActivity;
import com.oriondev.moneywallet.ui.adapter.pager.RecurrenceViewPagerAdapter;
import com.oriondev.moneywallet.ui.fragment.base.MultiPanelViewPagerMultiItemFragment;
import com.oriondev.moneywallet.ui.fragment.base.SecondaryPanelFragment;
import com.oriondev.moneywallet.ui.fragment.secondary.RecurrentTransactionItemFragment;
import com.oriondev.moneywallet.ui.fragment.secondary.RecurrentTransferItemFragment;


public class RecurrenceMultiPanelViewPagerFragment extends MultiPanelViewPagerMultiItemFragment {

    private static final String SECONDARY_FRAGMENT_TAG = "RecurrenceMultiPanelViewPagerFragment::Tag::SecondaryPanelFragment";

    private static final int TYPE_TRANSACTION = 0;
    private static final int TYPE_TRANSFER = 1;

    @Override
    protected SecondaryPanelFragment onCreateSecondaryPanel(int index) {
        switch (index) {
            case TYPE_TRANSACTION:
                return new RecurrentTransactionItemFragment();
            case TYPE_TRANSFER:
                return new RecurrentTransferItemFragment();
            default:
                throw new IllegalArgumentException("Unknown item type: " + index);
        }
    }

    @Override
    protected String getSecondaryFragmentTag(int index) {
        return SECONDARY_FRAGMENT_TAG + "::Type" + String.valueOf(index);
    }

    @Override
    protected void onFloatingActionButtonClick() {
        Intent intent;
        switch (getViewPagerPosition()) {
            case 0:
                intent = new Intent(getActivity(), NewEditRecurrentTransactionActivity.class);
                break;
            case 1:
                intent = new Intent(getActivity(), NewEditRecurrentTransferActivity.class);
                break;
            default:
                return;
        }
        intent.putExtra(NewEditItemActivity.MODE, NewEditItemActivity.Mode.NEW_ITEM);
        startActivity(intent);
    }

    @NonNull
    @Override
    protected PagerAdapter onCreatePagerAdapter(FragmentManager fragmentManager) {
        return new RecurrenceViewPagerAdapter(fragmentManager, getActivity());
    }

    @Override
    protected int getTitleRes() {
        return R.string.menu_recurrences;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        IntentFilter filter = new IntentFilter(LocalAction.ACTION_ITEM_CLICK);
        LocalBroadcastManager.getInstance(context).registerReceiver(mItemClickReceiver, filter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Activity activity = getActivity();
        if (activity != null) {
            LocalBroadcastManager.getInstance(activity).unregisterReceiver(mItemClickReceiver);
        }
    }

    private BroadcastReceiver mItemClickReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                long id = intent.getLongExtra(Message.ITEM_ID, 0L);
                switch (intent.getIntExtra(Message.ITEM_TYPE, 0)) {
                    case Message.TYPE_RECURRENT_TRANSACTION:
                        showItemId(TYPE_TRANSACTION, id);
                        break;
                    case Message.TYPE_RECURRENT_TRANSFER:
                        showItemId(TYPE_TRANSFER, id);
                        break;
                    default:
                        return;
                }
                showSecondaryPanel();
            }
        }

    };
}