package com.oriondev.moneywallet.picker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.oriondev.moneywallet.model.RecurrenceSetting;
import com.oriondev.moneywallet.ui.fragment.dialog.RecurrencePickerDialog;



public class RecurrencePicker extends Fragment implements RecurrencePickerDialog.Callback {

    private static final String SS_RECURRENCE_SETTING = "RecurrencePicker::SavedState::RecurrenceSetting";

    private static final String ARG_RECURRENCE_SETTING = "RecurrencePicker::Argument::RecurrenceSetting";

    private Controller mController;

    private RecurrenceSetting mRecurrenceSetting;

    private RecurrencePickerDialog mRecurrencePickerDialog;

    public static RecurrencePicker createPicker(FragmentManager fragmentManager, String tag, RecurrenceSetting recurrenceSetting) {
        RecurrencePicker recurrencePicker = (RecurrencePicker) fragmentManager.findFragmentByTag(tag);
        if (recurrencePicker == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(ARG_RECURRENCE_SETTING, recurrenceSetting);
            recurrencePicker = new RecurrencePicker();
            recurrencePicker.setArguments(arguments);
            fragmentManager.beginTransaction().add(recurrencePicker, tag).commit();
        }
        return recurrencePicker;
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
            mRecurrenceSetting = savedInstanceState.getParcelable(SS_RECURRENCE_SETTING);
        } else {
            Bundle arguments = getArguments();
            if (arguments != null && arguments.containsKey(ARG_RECURRENCE_SETTING)) {
                mRecurrenceSetting = arguments.getParcelable(ARG_RECURRENCE_SETTING);
            } else {
                throw new IllegalStateException("RecurrencePicker not initialized correctly. Please use RecurrencePicker.createPicker(...) instead.");
            }
        }
        mRecurrencePickerDialog = (RecurrencePickerDialog) getChildFragmentManager().findFragmentByTag(getDialogTag());
        if (mRecurrencePickerDialog == null) {
            mRecurrencePickerDialog = RecurrencePickerDialog.newInstance();
        }
        mRecurrencePickerDialog.setCallback(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fireCallbackSafely();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SS_RECURRENCE_SETTING, mRecurrenceSetting);
    }

    private void fireCallbackSafely() {
        if (mController != null) {
            mController.onRecurrenceSettingChanged(getTag(), mRecurrenceSetting);
        }
    }

    public RecurrenceSetting getCurrentSettings() {
        return mRecurrenceSetting;
    }

    private String getDialogTag() {
        return getTag() + "::DialogFragment";
    }

    public void showPicker() {
        mRecurrencePickerDialog.showPicker(getChildFragmentManager(), getDialogTag(), mRecurrenceSetting);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mController = null;
    }

    @Override
    public void onRecurrenceSettingChanged(RecurrenceSetting recurrenceSetting) {
        mRecurrenceSetting = recurrenceSetting;
        fireCallbackSafely();
    }

    public interface Controller {

        void onRecurrenceSettingChanged(String tag, RecurrenceSetting recurrenceSetting);
    }
}