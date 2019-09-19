package com.oriondev.moneywallet.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.activity.base.SinglePanelViewPagerActivity;
import com.oriondev.moneywallet.ui.adapter.pager.PeriodViewPagerAdapter;
import com.oriondev.moneywallet.utils.DateFormatter;

import java.util.Date;


public class PeriodDetailActivity extends SinglePanelViewPagerActivity {

    public static final String START_DATE = "PeriodDetailActivity::Arguments::StartDate";
    public static final String END_DATE = "PeriodDetailActivity::Arguments::EndDate";

    @NonNull
    @Override
    protected PagerAdapter onCreatePagerAdapter(FragmentManager fragmentManager) {
        Intent intent = getIntent();
        if (intent != null) {
            Date startDate = (Date) intent.getSerializableExtra(START_DATE);
            Date endDate = (Date) intent.getSerializableExtra(END_DATE);
            if (startDate != null && endDate != null) {
                setToolbarSubtitle(DateFormatter.getDateRange(this, startDate, endDate));
            }
            return new PeriodViewPagerAdapter(fragmentManager, this, startDate, endDate);
        } else {
            return new PeriodViewPagerAdapter(fragmentManager, this, null, null);
        }
    }

    @Override
    protected int getActivityTitleRes() {
        return R.string.menu_overview;
    }

    @Override
    protected boolean isFloatingActionButtonEnabled() {
        return false;
    }
}