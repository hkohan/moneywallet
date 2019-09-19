package com.oriondev.moneywallet.picker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.oriondev.moneywallet.model.DataFormat;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.ui.fragment.dialog.BudgetTypePickerDialog;
import com.oriondev.moneywallet.ui.fragment.dialog.DataFormatPickerDialog;


public class ImportExportFormatPicker extends Fragment implements DataFormatPickerDialog.Callback {

    private static final String SS_CURRENT_FORMAT = "ImportExportFormatPicker::SavedState::CurrentFormat";

    private Controller mController;

    private DataFormat mCurrentFormat;

    private DataFormatPickerDialog mDataFormatPickerDialog;

    public static ImportExportFormatPicker createPicker(FragmentManager fragmentManager, String tag) {
        ImportExportFormatPicker budgetTypePicker = (ImportExportFormatPicker) fragmentManager.findFragmentByTag(tag);
        if (budgetTypePicker == null) {
            budgetTypePicker = new ImportExportFormatPicker();
            fragmentManager.beginTransaction().add(budgetTypePicker, tag).commit();
        }
        return budgetTypePicker;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Controller) {
            mController = (Controller) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentFormat = (DataFormat) savedInstanceState.getSerializable(SS_CURRENT_FORMAT);
        } else {
            mCurrentFormat = null;
        }
        mDataFormatPickerDialog = (DataFormatPickerDialog) getChildFragmentManager().findFragmentByTag(getDialogTag());
        if (mDataFormatPickerDialog == null) {
            mDataFormatPickerDialog = DataFormatPickerDialog.newInstance();
        }
        mDataFormatPickerDialog.setCallback(this);
    }

    private String getDialogTag() {
        return getTag() + "::DialogFragment";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fireCallbackSafely();
    }

    private void fireCallbackSafely() {
        if (mController != null) {
            mController.onFormatChanged(getTag(), mCurrentFormat);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SS_CURRENT_FORMAT, mCurrentFormat);
    }

    public boolean isSelected() {
        return mCurrentFormat != null;
    }

    public void setCurrentFormat(DataFormat dataFormat) {
        mCurrentFormat = dataFormat;
        fireCallbackSafely();
    }

    public DataFormat getCurrentFormat() {
        return mCurrentFormat;
    }

    public void showPicker(DataFormat[] dataFormats) {
        int index = -1;
        for (int i = 0; i < dataFormats.length; i++) {
            if (dataFormats[i] == mCurrentFormat) {
                index = i;
            }
        }
        mDataFormatPickerDialog.showPicker(getChildFragmentManager(), getDialogTag(), dataFormats, index);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mController = null;
    }

    @Override
    public void onDataFormatSelected(DataFormat dataFormat) {
        mCurrentFormat = dataFormat;
        fireCallbackSafely();
    }

    public interface Controller {

        void onFormatChanged(String tag, DataFormat format);
    }
}