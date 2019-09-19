package com.oriondev.moneywallet.ui.fragment.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.ui.adapter.recycler.BudgetTypeSelectorAdapter;
import com.oriondev.moneywallet.ui.view.theme.ThemedDialog;


public class BudgetTypePickerDialog extends DialogFragment implements BudgetTypeSelectorAdapter.Controller {

    private static final String SS_SELECTED_BUDGET_TYPE = "BudgetTypePickerDialog::SavedState::CurrentBudgetType";

    public static BudgetTypePickerDialog newInstance() {
        return new BudgetTypePickerDialog();
    }

    private Callback mCallback;
    private Contract.BudgetType mBudgetType;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        if (activity == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        if (savedInstanceState != null) {
            mBudgetType = (Contract.BudgetType) savedInstanceState.getSerializable(SS_SELECTED_BUDGET_TYPE);
        }
        return ThemedDialog.buildMaterialDialog(activity)
                .title(R.string.dialog_budget_type_picker_title)
                .adapter(new BudgetTypeSelectorAdapter(this), null)
                .show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SS_SELECTED_BUDGET_TYPE, mBudgetType);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void showPicker(FragmentManager fragmentManager, String tag, Contract.BudgetType budgetType) {
        mBudgetType = budgetType;
        show(fragmentManager, tag);
    }

    @Override
    public void onBudgetTypeSelected(Contract.BudgetType budgetType) {
        mCallback.onBudgetTypeSelected(budgetType);
        dismiss();
    }

    @Override
    public boolean isBudgetTypeSelected(Contract.BudgetType budgetType) {
        return mBudgetType == budgetType;
    }

    public interface Callback {

        void onBudgetTypeSelected(Contract.BudgetType budgetType);
    }
}