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
import com.oriondev.moneywallet.model.Icon;
import com.oriondev.moneywallet.model.Money;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.utils.DateFormatter;
import com.oriondev.moneywallet.utils.DateUtils;
import com.oriondev.moneywallet.utils.IconLoader;
import com.oriondev.moneywallet.utils.MoneyFormatter;

import java.util.Date;


public class EventCursorAdapter extends AbstractCursorAdapter<EventCursorAdapter.ViewHolder> {

    private int mIndexId;
    private int mIndexIcon;
    private int mIndexName;
    private int mIndexEndDate;
    private int mIndexProgress;

    private MoneyFormatter mMoneyFormatter;

    private final ActionListener mActionListener;

    public EventCursorAdapter(ActionListener actionListener) {
        super(null, Contract.Event.ID);
        mActionListener = actionListener;
        mMoneyFormatter = MoneyFormatter.getInstance();
    }

    @Override
    protected void onLoadColumnIndices(@NonNull Cursor cursor) {
        mIndexId = cursor.getColumnIndex(Contract.Event.ID);
        mIndexIcon = cursor.getColumnIndex(Contract.Event.ICON);
        mIndexName = cursor.getColumnIndex(Contract.Event.NAME);
        mIndexEndDate = cursor.getColumnIndex(Contract.Event.END_DATE);
        mIndexProgress = cursor.getColumnIndex(Contract.Event.PROGRESS);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        Icon icon = IconLoader.parse(cursor.getString(mIndexIcon));
        IconLoader.loadInto(icon, holder.mAvatarImageView);
        holder.mPrimaryTextView.setText(cursor.getString(mIndexName));
        Money money = Money.parse(cursor.getString(mIndexProgress));
        mMoneyFormatter.applyTinted(holder.mMoneyTextView, money);
        Date date = DateUtils.getDateFromSQLDateString(cursor.getString(mIndexEndDate));
        applyRelativeDate(date, holder.mSecondaryTextView);
    }

    private void applyRelativeDate(Date date, TextView textView) {
        if (date.getTime() <= System.currentTimeMillis()) {
            DateFormatter.applyDateFromToday(textView, date, R.string.relative_string_ended_on);
        } else {
            DateFormatter.applyDateFromToday(textView, date, R.string.relative_string_will_end_on);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_event_item, parent, false);
        return new ViewHolder(itemView);
    }

    /*package-local*/ class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mAvatarImageView;
        private TextView mPrimaryTextView;
        private TextView mMoneyTextView;
        private TextView mSecondaryTextView;

        /*package-local*/ ViewHolder(View itemView) {
            super(itemView);
            mAvatarImageView = itemView.findViewById(R.id.avatar_image_view);
            mPrimaryTextView = itemView.findViewById(R.id.primary_text_view);
            mMoneyTextView = itemView.findViewById(R.id.money_text_view);
            mSecondaryTextView = itemView.findViewById(R.id.secondary_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mActionListener != null) {
                Cursor cursor = getSafeCursor(getAdapterPosition());
                if (cursor != null) {
                    mActionListener.onEventClick(cursor.getLong(mIndexId));
                }
            }
        }
    }

    public interface ActionListener {

        void onEventClick(long id);
    }

}