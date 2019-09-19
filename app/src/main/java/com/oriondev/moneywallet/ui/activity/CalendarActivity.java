package com.oriondev.moneywallet.ui.activity;

import com.oriondev.moneywallet.ui.activity.base.MultiPanelActivity;
import com.oriondev.moneywallet.ui.fragment.base.MultiPanelFragment;
import com.oriondev.moneywallet.ui.fragment.multipanel.CalendarMultiPanelFragment;


public class CalendarActivity extends MultiPanelActivity {

    private static final String TAG_FRAGMENT_CALENDAR = "CalendarActivity::CalendarMultiPanelFragment";

    @Override
    protected MultiPanelFragment onCreateMultiPanelFragment() {
        return new CalendarMultiPanelFragment();
    }

    @Override
    protected String getMultiPanelFragmentTag() {
        return TAG_FRAGMENT_CALENDAR;
    }
}