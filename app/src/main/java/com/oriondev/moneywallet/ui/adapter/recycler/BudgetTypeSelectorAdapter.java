package com.oriondev.moneywallet.ui.adapter.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.storage.database.Contract;


public class BudgetTypeSelectorAdapter extends RecyclerView.Adapter<BudgetTypeSelectorAdapter.ViewHolder> {

    private static final Contract.BudgetType[] BUDGET_TYPES = new Contract.BudgetType[] {
            Contract.BudgetType.INCOMES,
            Contract.BudgetType.EXPENSES,
            Contract.BudgetType.CATEGORY
    };

    private final Controller mController;

    public BudgetTypeSelectorAdapter(Controller controller) {
        mController = controller;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_budget_type_selector_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contract.BudgetType budgetType = BUDGET_TYPES[position];
        switch (budgetType) {
            case INCOMES:
                holder.mAvatarImageView.setImageResource(R.drawable.ic_bank_transfer_in_24dp);
                holder.mPrimaryTextView.setText(R.string.hint_incomes);
                break;
            case EXPENSES:
                holder.mAvatarImageView.setImageResource(R.drawable.ic_bank_transfer_out_24dp);
                holder.mPrimaryTextView.setText(R.string.hint_expenses);
                break;
            case CATEGORY:
                holder.mAvatarImageView.setImageResource(R.drawable.ic_table_large_24dp);
                holder.mPrimaryTextView.setText(R.string.hint_category);
                break;
        }
        boolean selected = mController.isBudgetTypeSelected(budgetType);
        holder.mSelectorImageView.setVisibility(selected ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return BUDGET_TYPES.length;
    }

    /*package-local*/ class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mAvatarImageView;
        private TextView mPrimaryTextView;
        private ImageView mSelectorImageView;

        /*package-local*/ ViewHolder(View itemView) {
            super(itemView);
            mAvatarImageView = itemView.findViewById(R.id.avatar_image_view);
            mPrimaryTextView = itemView.findViewById(R.id.primary_text_view);
            mSelectorImageView = itemView.findViewById(R.id.selector_image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mController != null) {;
                mController.onBudgetTypeSelected(BUDGET_TYPES[getAdapterPosition()]);
            }
        }
    }

    public interface Controller {

        void onBudgetTypeSelected(Contract.BudgetType budgetType);

        boolean isBudgetTypeSelected(Contract.BudgetType budgetType);
    }
}