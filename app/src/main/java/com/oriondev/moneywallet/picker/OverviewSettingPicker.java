package com.oriondev.moneywallet.picker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.oriondev.moneywallet.model.Group;
import com.oriondev.moneywallet.model.OverviewSetting;
import com.oriondev.moneywallet.storage.preference.PreferenceManager;
import com.oriondev.moneywallet.ui.fragment.dialog.OverviewSettingDialog;
import com.oriondev.moneywallet.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;


public class OverviewSettingPicker extends Fragment implements OverviewSettingDialog.Callback {

    private static final String SS_OVERVIEW_SETTING = "OverviewSettingPicker::SavedState::OverviewSetting";

    private Controller mController;

    private OverviewSetting mOverviewSetting;

    private OverviewSettingDialog mOverviewSettingDialog;

    public static OverviewSettingPicker createPicker(FragmentManager fragmentManager, String tag) {
        OverviewSettingPicker placePicker = (OverviewSettingPicker) fragmentManager.findFragmentByTag(tag);
        if (placePicker == null) {
            placePicker = new OverviewSettingPicker();
            fragmentManager.beginTransaction().add(placePicker, tag).commit();
        }
        return placePicker;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Controller) {
            mController = (Controller) context;
        } else if (getParentFragment() instanceof Controller) {
            mController = (Controller) getParentFragment();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mOverviewSetting = savedInstanceState.getParcelable(SS_OVERVIEW_SETTING);
        } else {
            Date startDate;
            Date endDate;
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.SUNDAY);
            Group groupType = PreferenceManager.getCurrentGroupType();
            switch (groupType) {
                case DAILY:
                    // we consider the current week
                    int firstDayOfWeek = PreferenceManager.getFirstDayOfWeek();
                    int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    if (currentDayOfWeek < firstDayOfWeek) {
                        calendar.add(Calendar.DAY_OF_MONTH, -7);
                    }
                    calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
                    startDate = calendar.getTime();
                    endDate = DateUtils.addDays(calendar, 6);
                    break;
                case WEEKLY:
                    // we consider the current month
                    int firstDayOfMonth = PreferenceManager.getFirstDayOfMonth();
                    int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    if (currentDayOfMonth < firstDayOfMonth) {
                        calendar.add(Calendar.MONTH, -1);
                    }
                    calendar.set(Calendar.DAY_OF_MONTH, firstDayOfMonth);
                    startDate = calendar.getTime();
                    endDate = DateUtils.addMonthAndDay(calendar, 1, -1);
                    break;
                case MONTHLY:
                    // we consider the current year
                    int currentYear = calendar.get(Calendar.YEAR);
                    calendar.set(currentYear, Calendar.JANUARY, 1);
                    startDate = calendar.getTime();
                    calendar.set(currentYear, Calendar.DECEMBER, 31);
                    endDate = calendar.getTime();
                    break;
                case YEARLY:
                    // we consider the last three years
                    currentYear = calendar.get(Calendar.YEAR);
                    calendar.set(currentYear - 3, Calendar.JANUARY, 1);
                    startDate = calendar.getTime();
                    calendar.set(currentYear, Calendar.DECEMBER, 31);
                    endDate = calendar.getTime();
                    break;
                default:
                    startDate = calendar.getTime();
                    endDate = calendar.getTime();
                    break;
            }
            mOverviewSetting = new OverviewSetting(startDate, endDate, groupType, OverviewSetting.CashFlow.NET_INCOMES);
        }
        mOverviewSettingDialog = (OverviewSettingDialog) getChildFragmentManager().findFragmentByTag(getDialogTag());
        if (mOverviewSettingDialog == null) {
            mOverviewSettingDialog = OverviewSettingDialog.newInstance();
        }
        mOverviewSettingDialog.setCallback(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fireCallbackSafely();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SS_OVERVIEW_SETTING, mOverviewSetting);
    }

    private void fireCallbackSafely() {
        if (mController != null) {
            mController.onOverviewSettingChanged(getTag(), mOverviewSetting);
        }
    }

    public OverviewSetting getCurrentSettings() {
        return mOverviewSetting;
    }

    private String getDialogTag() {
        return getTag() + "::DialogFragment";
    }

    public void showPicker() {
        mOverviewSettingDialog.showPicker(getChildFragmentManager(), getDialogTag(), mOverviewSetting);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mController = null;
    }

    @Override
    public void onOverviewSettingChanged(OverviewSetting overviewSetting) {
        mOverviewSetting = overviewSetting;
        fireCallbackSafely();
    }

    public interface Controller {

        void onOverviewSettingChanged(String tag, OverviewSetting overviewSetting);
    }
}