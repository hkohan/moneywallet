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
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.utils.CurrencyManager;
import com.oriondev.moneywallet.utils.IconLoader;
import com.oriondev.moneywallet.utils.MoneyFormatter;


public class WalletCursorAdapter extends AbstractCursorAdapter<WalletCursorAdapter.WalletViewHolder> {

    private int mIndexId;
    private int mIndexName;
    private int mIndexIcon;
    private int mIndexCurrency;
    private int mIndexStartMoney;
    private int mIndexTotalMoney;
    private int mIndexCountInTotal;

    private MoneyFormatter mMoneyFormatter;

    private final WalletActionListener mListener;

    public WalletCursorAdapter(WalletActionListener listener) {
        super(null, Contract.Wallet.ID);
        mListener = listener;
        mMoneyFormatter = MoneyFormatter.getInstance();
    }

    @Override
    protected void onLoadColumnIndices(@NonNull Cursor cursor) {
        mIndexId = cursor.getColumnIndex(Contract.Wallet.ID);
        mIndexName = cursor.getColumnIndex(Contract.Wallet.NAME);
        mIndexIcon = cursor.getColumnIndex(Contract.Wallet.ICON);
        mIndexCurrency = cursor.getColumnIndex(Contract.Wallet.CURRENCY);
        mIndexStartMoney = cursor.getColumnIndex(Contract.Wallet.START_MONEY);
        mIndexTotalMoney = cursor.getColumnIndex(Contract.Wallet.TOTAL_MONEY);
        mIndexCountInTotal = cursor.getColumnIndex(Contract.Wallet.COUNT_IN_TOTAL);
    }

    @Override
    public WalletViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_wallet_item, parent, false);
        return new WalletViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WalletViewHolder holder, Cursor cursor) {
        IconLoader.parseAndLoad(cursor.getString(mIndexIcon), holder.mIconView);
        holder.mNameView.setText(cursor.getString(mIndexName));
        CurrencyUnit currency = CurrencyManager.getCurrency(cursor.getString(mIndexCurrency));
        long money = cursor.getLong(mIndexStartMoney) + cursor.getLong(mIndexTotalMoney);
        mMoneyFormatter.applyNotTinted(holder.mMoneyView, currency, money);
    }

    /*package-local*/ class WalletViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mIconView;
        private TextView mNameView;
        private TextView mMoneyView;

        /*package-local*/ WalletViewHolder(View itemView) {
            super(itemView);
            mIconView = itemView.findViewById(R.id.icon_image_view);
            mNameView = itemView.findViewById(R.id.name_text_view);
            mMoneyView = itemView.findViewById(R.id.money_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                Cursor cursor = getSafeCursor(getAdapterPosition());
                if (cursor != null) {
                    mListener.onWalletClick(cursor.getLong(mIndexId));
                }
            }
        }
    }

    public interface WalletActionListener {

        void onWalletClick(long id);
    }
}