package com.oriondev.moneywallet.picker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.ui.fragment.dialog.BudgetTypePickerDialog;


public class BudgetTypePicker extends Fragment implements BudgetTypePickerDialog.Callback {

    private static final String SS_CURRENT_TYPE = "BudgetTypePicker::SavedState::CurrentType";

    private static final String ARG_DEFAULT_TYPE = "BudgetTypePicker::Arguments::DefaultType";

    private Controller mController;

    private Contract.BudgetType mCurrentType;

    private BudgetTypePickerDialog mBudgetTypePickerDialog;

    public static BudgetTypePicker createPicker(FragmentManager fragmentManager, String tag, Contract.BudgetType defaultType) {
        BudgetTypePicker budgetTypePicker = (BudgetTypePicker) fragmentManager.findFragmentByTag(tag);
        if (budgetTypePicker == null) {
            budgetTypePicker = new BudgetTypePicker();
            Bundle arguments = new Bundle();
            arguments.putSerializable(ARG_DEFAULT_TYPE, defaultType);
            budgetTypePicker.setArguments(arguments);
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
            mCurrentType = (Contract.BudgetType) savedInstanceState.getSerializable(SS_CURRENT_TYPE);
        } else {
            Bundle arguments = getArguments();
            if (arguments != null) {
                mCurrentType = (Contract.BudgetType) arguments.getSerializable(ARG_DEFAULT_TYPE);
            } else {
                mCurrentType = Contract.BudgetType.EXPENSES;
            }
        }
        mBudgetTypePickerDialog = (BudgetTypePickerDialog) getChildFragmentManager().findFragmentByTag(getDialogTag());
        if (mBudgetTypePickerDialog == null) {
            mBudgetTypePickerDialog = BudgetTypePickerDialog.newInstance();
        }
        mBudgetTypePickerDialog.setCallback(this);
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
            mController.onTypeChanged(getTag(), mCurrentType);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SS_CURRENT_TYPE, mCurrentType);
    }

    public boolean isSelected() {
        return mCurrentType != null;
    }

    public Contract.BudgetType getCurrentType() {
        return mCurrentType;
    }

    public void showPicker() {
        mBudgetTypePickerDialog.showPicker(getChildFragmentManager(), getDialogTag(), mCurrentType);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mController = null;
    }

    @Override
    public void onBudgetTypeSelected(Contract.BudgetType budgetType) {
        mCurrentType = budgetType;
        fireCallbackSafely();
    }

    public interface Controller {

        void onTypeChanged(String tag, Contract.BudgetType type);
    }
}