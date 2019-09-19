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
import java.util.Locale;

public class RecurrentTransferCursorAdapter extends AbstractCursorAdapter<RecurrentTransferCursorAdapter.RecurrentTransferViewHolder> {

    private int mIndexId;
    private int mIndexWalletFromName;
    private int mIndexWalletFromCurrency;
    private int mIndexWalletToName;
    private int mIndexWalletToIcon;
    private int mIndexMoneyFrom;
    private int mIndexNextOccurrence;

    private MoneyFormatter mMoneyFormatter;

    private final ActionListener mActionListener;

    public RecurrentTransferCursorAdapter(ActionListener actionListener) {
        super(null, Contract.RecurrentTransfer.ID);
        mActionListener = actionListener;
        mMoneyFormatter = MoneyFormatter.getInstance();
    }

    @Override
    protected void onLoadColumnIndices(@NonNull Cursor cursor) {
        mIndexId = cursor.getColumnIndex(Contract.RecurrentTransfer.ID);
        mIndexWalletFromName = cursor.getColumnIndex(Contract.RecurrentTransfer.WALLET_FROM_NAME);
        mIndexWalletFromCurrency = cursor.getColumnIndex(Contract.RecurrentTransfer.WALLET_FROM_CURRENCY);
        mIndexWalletToName = cursor.getColumnIndex(Contract.RecurrentTransfer.WALLET_TO_NAME);
        mIndexWalletToIcon = cursor.getColumnIndex(Contract.RecurrentTransfer.WALLET_TO_ICON);
        mIndexMoneyFrom = cursor.getColumnIndex(Contract.RecurrentTransfer.MONEY_FROM);
        mIndexNextOccurrence = cursor.getColumnIndex(Contract.RecurrentTransfer.NEXT_OCCURRENCE);
    }

    @Override
    public void onBindViewHolder(RecurrentTransferViewHolder holder, Cursor cursor) {
        Icon icon = IconLoader.parse(cursor.getString(mIndexWalletToIcon));
        IconLoader.loadInto(icon, holder.mAvatarImageView);
        holder.mPrimaryTextView.setText(R.string.system_category_transfer);
        long moneyFrom = cursor.getLong(mIndexMoneyFrom);
        CurrencyUnit currencyFrom = CurrencyManager.getCurrency(cursor.getString(mIndexWalletFromCurrency));
        mMoneyFormatter.applyNotTinted(holder.mMoneyTextView, currencyFrom, moneyFrom);
        String walletFrom = cursor.getString(mIndexWalletFromName);
        String walletTo = cursor.getString(mIndexWalletToName);
        holder.mSecondaryTextView.setText(String.format(Locale.ENGLISH, "%s → %s", walletFrom, walletTo));
        if (cursor.isNull(mIndexNextOccurrence)) {
            holder.mDateTextView.setText(R.string.hint_recurrence_finished);
        } else {
            Date nextOccurrence = DateUtils.getDateFromSQLDateString(cursor.getString(mIndexNextOccurrence));
            DateFormatter.applyDate(holder.mDateTextView, nextOccurrence);
        }
    }

    @NonNull
    @Override
    public RecurrentTransferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_transfer_item, parent, false);
        return new RecurrentTransferViewHolder(itemView);
    }

    /*package-local*/ class RecurrentTransferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mAvatarImageView;
        private TextView mPrimaryTextView;
        private TextView mMoneyTextView;
        private TextView mSecondaryTextView;
        private TextView mDateTextView;

        /*package-local*/ RecurrentTransferViewHolder(View itemView) {
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
                    mActionListener.onRecurrentTransferClick(cursor.getLong(mIndexId));
                }
            }
        }
    }

    public interface ActionListener {

        void onRecurrentTransferClick(long id);
    }
}