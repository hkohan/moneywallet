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
import com.oriondev.moneywallet.ui.view.CardButton;
import com.oriondev.moneywallet.utils.CurrencyManager;
import com.oriondev.moneywallet.utils.IconLoader;
import com.oriondev.moneywallet.utils.MoneyFormatter;


public class TransactionModelCursorAdapter extends AbstractCursorAdapter<TransactionModelCursorAdapter.TransactionModelViewHolder> {

    private int mIndexId;
    private int mIndexCategoryName;
    private int mIndexCategoryIcon;
    private int mIndexWalletCurrency;
    private int mIndexDirection;
    private int mIndexMoney;
    private int mIndexDescription;

    private MoneyFormatter mMoneyFormatter;

    private final ActionListener mActionListener;

    public TransactionModelCursorAdapter(ActionListener actionListener) {
        super(null, Contract.TransactionModel.ID);
        mActionListener = actionListener;
        mMoneyFormatter = MoneyFormatter.getInstance();
    }

    @Override
    protected void onLoadColumnIndices(@NonNull Cursor cursor) {
        mIndexId = cursor.getColumnIndex(Contract.TransactionModel.ID);
        mIndexCategoryName = cursor.getColumnIndex(Contract.TransactionModel.CATEGORY_NAME);
        mIndexCategoryIcon = cursor.getColumnIndex(Contract.TransactionModel.CATEGORY_ICON);
        mIndexWalletCurrency = cursor.getColumnIndex(Contract.TransactionModel.WALLET_CURRENCY);
        mIndexDirection = cursor.getColumnIndex(Contract.TransactionModel.DIRECTION);
        mIndexMoney = cursor.getColumnIndex(Contract.TransactionModel.MONEY);
        mIndexDescription = cursor.getColumnIndex(Contract.TransactionModel.DESCRIPTION);
    }

    @Override
    public void onBindViewHolder(TransactionModelViewHolder holder, Cursor cursor) {
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
    }

    @Override
    public TransactionModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_model_transaction_item, parent, false);
        return new TransactionModelViewHolder(itemView);
    }

    /*package-local*/ class TransactionModelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mAvatarImageView;
        private TextView mPrimaryTextView;
        private TextView mMoneyTextView;
        private TextView mSecondaryTextView;
        private CardButton mAddButton;
        private CardButton mEditButton;

        /*package-local*/ TransactionModelViewHolder(View itemView) {
            super(itemView);
            mAvatarImageView = itemView.findViewById(R.id.avatar_image_view);
            mPrimaryTextView = itemView.findViewById(R.id.primary_text_view);
            mMoneyTextView = itemView.findViewById(R.id.money_text_view);
            mSecondaryTextView = itemView.findViewById(R.id.secondary_text_view);
            mAddButton = itemView.findViewById(R.id.add_button);
            mEditButton = itemView.findViewById(R.id.edit_button);
            itemView.setOnClickListener(this);
            mAddButton.setOnClickListener(this);
            mEditButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mActionListener != null) {
                Cursor cursor = getSafeCursor(getAdapterPosition());
                if (cursor != null) {
                    long id = cursor.getLong(mIndexId);
                    if (v == mAddButton) {
                        mActionListener.onModelAddClick(id);
                    } else if (v == mEditButton) {
                        mActionListener.onModelEditClick(id);
                    } else {
                        mActionListener.onModelClick(id);
                    }
                }
            }
        }
    }

    public interface ActionListener {

        void onModelClick(long id);

        void onModelAddClick(long id);

        void onModelEditClick(long id);
    }
}