package com.oriondev.moneywallet.ui.adapter.recycler;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.model.CurrencyUnit;
import com.oriondev.moneywallet.model.Icon;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.utils.CurrencyManager;
import com.oriondev.moneywallet.utils.DateFormatter;
import com.oriondev.moneywallet.utils.DateUtils;
import com.oriondev.moneywallet.utils.IconLoader;
import com.oriondev.moneywallet.utils.MoneyFormatter;

import java.util.Date;


public class RecurrentTransactionCursorAdapter extends AbstractCursorAdapter<RecurrentTransactionCursorAdapter.RecurrentTransactionViewHolder> {

    private int mIndexId;
    private int mIndexCategoryName;
    private int mIndexCategoryIcon;
    private int mIndexWalletCurrency;
    private int mIndexDirection;
    private int mIndexMoney;
    private int mIndexDescription;
    private int mIndexNextOccurrence;

    private MoneyFormatter mMoneyFormatter;

    private final ActionListener mActionListener;

    public RecurrentTransactionCursorAdapter(ActionListener actionListener) {
        super(null, Contract.RecurrentTransaction.ID);
        mActionListener = actionListener;
        mMoneyFormatter = MoneyFormatter.getInstance();
    }

    @Override
    protected void onLoadColumnIndices(@NonNull Cursor cursor) {
        mIndexId = cursor.getColumnIndex(Contract.RecurrentTransaction.ID);
        mIndexCategoryName = cursor.getColumnIndex(Contract.RecurrentTransaction.CATEGORY_NAME);
        mIndexCategoryIcon = cursor.getColumnIndex(Contract.RecurrentTransaction.CATEGORY_ICON);
        mIndexWalletCurrency = cursor.getColumnIndex(Contract.RecurrentTransaction.WALLET_CURRENCY);
        mIndexDirection = cursor.getColumnIndex(Contract.RecurrentTransaction.DIRECTION);
        mIndexMoney = cursor.getColumnIndex(Contract.RecurrentTransaction.MONEY);
        mIndexDescription = cursor.getColumnIndex(Contract.RecurrentTransaction.DESCRIPTION);
        mIndexNextOccurrence = cursor.getColumnIndex(Contract.RecurrentTransaction.NEXT_OCCURRENCE);
    }

    @Override
    public void onBindViewHolder(RecurrentTransactionViewHolder holder, Cursor cursor) {
        Icon icon = IconLoader.parse(cursor.getString(mIndexCategoryIcon));
        IconLoader.loadInto(icon, holder.mAvatarImageView);
        holder.mPrimaryTextView.setText(cursor.getString(mIndexCategoryName));
        CurrencyUnit currency = CurrencyManager.getCurrency(cursor.getString(mIndexWalletCurrency));
        long money = cursor.getLong(mIndexMoney);
        if (cursor.getInt(mIndexDirection) == Contract.Direction.INCOME) {
            mMoneyFormatter.applyTintedIncome(holder.mMoneyTextView, currency, money);
        } else {
            mMoneyFormatter.applyTintedExpense(holder.mMoneyTextView, currency, money);
        }
        holder.mSecondaryTextView.setText(cursor.getString(mIndexDescription));
        if (cursor.isNull(mIndexNextOccurrence)) {
            holder.mDateTextView.setText(R.string.hint_recurrence_finished);
        } else {
            Date nextOccurrence = DateUtils.getDateFromSQLDateString(cursor.getString(mIndexNextOccurrence));
            DateFormatter.applyDate(holder.mDateTextView, nextOccurrence);
        }
    }

    @NonNull
    @Override
    public RecurrentTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_transaction_item, parent, false);
        return new RecurrentTransactionViewHolder(itemView);
    }

    /*package-local*/ class RecurrentTransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mAvatarImageView;
        private TextView mPrimaryTextView;
        private TextView mMoneyTextView;
        private TextView mSecondaryTextView;
        private TextView mDateTextView;

        /*package-local*/ RecurrentTransactionViewHolder(View itemView) {
            super(itemView);
            mAvatarImageView = itemView.findViewById(R.id.avatar_image_view);
            mPrimaryTextView = itemView.findViewById(R.id.primary_text_view);
            mMoneyTextView = itemView.findViewById(R.id.money_text_view);
            mSecondaryTextView = itemView.findViewById(R.id.secondary_text_view);
            mDateTextView = itemView.findViewById(R.id.date_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mActionListener != null) {
                Cursor cursor = getSafeCursor(getAdapterPosition());
                if (cursor != null) {
                    mActionListener.onRecurrentTransactionClick(cursor.getLong(mIndexId));
                }
            }
        }
    }

    public interface ActionListener {

        void onRecurrentTransactionClick(long id);
    }
}