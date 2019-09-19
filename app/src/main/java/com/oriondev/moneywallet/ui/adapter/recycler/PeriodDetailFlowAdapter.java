package com.oriondev.moneywallet.ui.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.model.CategoryMoney;
import com.oriondev.moneywallet.model.PeriodDetailFlowData;
import com.oriondev.moneywallet.utils.IconLoader;
import com.oriondev.moneywallet.utils.MoneyFormatter;


public class PeriodDetailFlowAdapter extends RecyclerView.Adapter<PeriodDetailFlowAdapter.ViewHolder> {

    private final Controller mController;
    private final boolean mIncomes;
    private final MoneyFormatter mMoneyFormatter;

    private PeriodDetailFlowData mData;

    public PeriodDetailFlowAdapter(Controller controller, boolean incomes) {
        mController = controller;
        mIncomes = incomes;
        mMoneyFormatter = MoneyFormatter.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_category_money_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoryMoney category = mData.getCategory(position);
        IconLoader.loadInto(category.getIcon(), holder.mIconImageView);
        holder.mNameTextView.setText(category.getName());
        if (mIncomes) {
            mMoneyFormatter.applyTintedIncome(holder.mMoneyTextView, category.getMoney());
        } else {
            mMoneyFormatter.applyTintedExpense(holder.mMoneyTextView, category.getMoney());
        }
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.getCategoryCount() : 0;
    }

    public void setData(PeriodDetailFlowData data) {
        mData = data;
        notifyDataSetChanged();
    }

    /*package-local*/ class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mIconImageView;
        private TextView mNameTextView;
        private TextView mMoneyTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mIconImageView = itemView.findViewById(R.id.icon_image_view);
            mNameTextView = itemView.findViewById(R.id.name_text_view);
            mMoneyTextView = itemView.findViewById(R.id.money_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mController != null) {
                int index = getAdapterPosition();
                if (mData != null) {
                    CategoryMoney category = mData.getCategory(index);
                    mController.onCategoryClick(category.getId());
                }
            }
        }
    }

    public interface Controller {

        void onCategoryClick(long id);
    }
}