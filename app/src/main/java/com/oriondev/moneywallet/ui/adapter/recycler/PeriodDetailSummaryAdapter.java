package com.oriondev.moneywallet.ui.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.model.PeriodDetailSummaryData;
import com.oriondev.moneywallet.model.PeriodMoney;
import com.oriondev.moneywallet.utils.DateFormatter;
import com.oriondev.moneywallet.utils.MoneyFormatter;


public class PeriodDetailSummaryAdapter extends RecyclerView.Adapter<PeriodDetailSummaryAdapter.ViewHolder> {

    private final Controller mController;

    private PeriodDetailSummaryData mData;

    private final MoneyFormatter mMoneyFormatter;

    public PeriodDetailSummaryAdapter(Controller controller) {
        mController = controller;
        mMoneyFormatter = MoneyFormatter.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_period_summary_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PeriodMoney periodMoney = mData.getPeriodMoney(position);
        if (!periodMoney.isTimeRange()) {
            DateFormatter.applyDateRange(holder.mNameTextView, periodMoney.getStartDate(), periodMoney.getEndDate());
        } else {
            DateFormatter.applyTimeRange(holder.mNameTextView, periodMoney.getStartDate(), periodMoney.getEndDate());
        }
        // TODO: maybe can be useful to display also incomes and expenses
        mMoneyFormatter.applyTinted(holder.mMoneyTextView, periodMoney.getNetIncomes());
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.getPeriodCount() : 0;
    }

    public void setData(PeriodDetailSummaryData data) {
        mData = data;
        notifyDataSetChanged();
    }

    /*package-local*/ class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameTextView;
        private TextView mMoneyTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.name_text_view);
            mMoneyTextView = itemView.findViewById(R.id.money_text_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mController != null) {
                int index = getAdapterPosition();
                if (mData != null) {
                    PeriodMoney periodMoney = mData.getPeriodMoney(index);
                    mController.onPeriodClick(periodMoney);
                }
            }
        }
    }

    public interface Controller {

        void onPeriodClick(PeriodMoney periodMoney);
    }
}