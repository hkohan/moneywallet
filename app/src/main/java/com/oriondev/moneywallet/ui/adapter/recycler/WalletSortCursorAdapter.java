package com.oriondev.moneywallet.ui.adapter.recycler;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.model.Icon;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.utils.IconLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WalletSortCursorAdapter extends AbstractCursorAdapter<WalletSortCursorAdapter.WalletViewHolder> {

    private int mIndexWalletId;
    private int mIndexWalletIcon;
    private int mIndexWalletName;

    private final WalletSortListener mListener;
    private final List<Integer> mSortedIndices;

    public WalletSortCursorAdapter(WalletSortListener listener) {
        super(null, Contract.Wallet.ID);
        mListener = listener;
        mSortedIndices = new ArrayList<>();
    }

    @Override
    protected void onLoadColumnIndices(@NonNull Cursor cursor) {
        mIndexWalletId = cursor.getColumnIndex(Contract.Wallet.ID);
        mIndexWalletIcon = cursor.getColumnIndex(Contract.Wallet.ICON);
        mIndexWalletName = cursor.getColumnIndex(Contract.Wallet.NAME);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewHolder holder, int position) {
        if (position >= 0 && position < mSortedIndices.size()) {
            super.onBindViewHolder(holder, mSortedIndices.get(position));
        }
    }

    @Override
    public void onBindViewHolder(WalletViewHolder holder, Cursor cursor) {
        Icon icon = IconLoader.parse(cursor.getString(mIndexWalletIcon));
        IconLoader.loadInto(icon, holder.mIconImageView);
        holder.mNameTextView.setText(cursor.getString(mIndexWalletName));
    }

    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_wallet_sort_item, parent, false);
        return new WalletViewHolder(itemView);
    }

    @Override
    public long getItemId(int position) {
        if (position >= 0 && position < mSortedIndices.size()) {
            return super.getItemId(mSortedIndices.get(position));
        }
        return 0L;
    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        Cursor oldCursor = super.swapCursor(newCursor);
        mSortedIndices.clear();
        if (newCursor != null) {
            for (int i = 0; i < newCursor.getCount(); i++) {
                mSortedIndices.add(i);
            }
        }
        return oldCursor;
    }

    public boolean moveItem(int from, int to) {
        if (Math.min(from, to) >= 0 && Math.max(from, to) < mSortedIndices.size()) {
            Collections.swap(mSortedIndices, from, to);
            notifyItemMoved(from, to);
            return true;
        }
        return false;
    }

    public List<Long> getSortedCategoryIds() {
        List<Long> categoryIds = new ArrayList<>();
        for (int i = 0; i < mSortedIndices.size(); i++) {
            Cursor cursor = getSafeCursor(mSortedIndices.get(i));
            if (cursor != null) {
                categoryIds.add(cursor.getLong(mIndexWalletId));
            }
        }
        return categoryIds;
    }

    /*package-local*/ class WalletViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

        private ImageView mIconImageView;
        private TextView mNameTextView;
        private ImageView mActionImageView;

        /*package-local*/ WalletViewHolder(View itemView) {
            super(itemView);
            mIconImageView = itemView.findViewById(R.id.icon_image_view);
            mNameTextView = itemView.findViewById(R.id.name_text_view);
            mActionImageView = itemView.findViewById(R.id.action_image_view);
            mActionImageView.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                if (mListener != null) {
                    mListener.onWalletDragStarted(this);
                }
            }
            return false;
        }
    }

    public interface WalletSortListener {

        void onWalletDragStarted(RecyclerView.ViewHolder viewHolder);
    }
}