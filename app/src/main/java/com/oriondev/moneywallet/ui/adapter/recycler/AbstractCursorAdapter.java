package com.oriondev.moneywallet.ui.adapter.recycler;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;



public abstract class AbstractCursorAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private Cursor mCursor;

    private boolean mDataValid;
    private String mIdColumn;
    private int mRowIdColumn;

    private DataSetObserver mDataSetObserver;

    public AbstractCursorAdapter(Cursor cursor, String id) {
        mCursor = cursor;
        mDataValid = cursor != null;
        mIdColumn = id;
        mRowIdColumn = mDataValid ? mCursor.getColumnIndex(id) : -1;
        mDataSetObserver = new NotifyingDataSetObserver();
        if (cursor != null) {
            mCursor.registerDataSetObserver(mDataSetObserver);
            onLoadColumnIndices(mCursor);
        }
    }

    private void prepareNewCursor(@NonNull Cursor cursor) {
        onLoadColumnIndices(cursor);
    }

    protected abstract void onLoadColumnIndices(@NonNull Cursor cursor);

    public boolean isDataValid() {
        return mDataValid;
    }

    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public int getItemCount() {
        if (mDataValid && mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        if (mDataValid && mCursor != null && mCursor.moveToPosition(position)) {
            return mCursor.getLong(mRowIdColumn);
        }
        return 0L;
    }

    protected Cursor getSafeCursor(int position) {
        if (mDataValid && mCursor != null && mCursor.moveToPosition(position)) {
            return mCursor;
        }
        return null;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (!mDataValid) {
            throw new IllegalStateException("this should only be called when cursor data is valid");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position: " + position);
        }
        onBindViewHolder(holder, mCursor);
    }

    public abstract void onBindViewHolder(VH holder, Cursor cursor);

    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        final Cursor oldCursor = mCursor;
        if (oldCursor != null && mDataSetObserver != null) {
            oldCursor.unregisterDataSetObserver(mDataSetObserver);
        }
        mCursor = newCursor;
        if (mCursor != null) {
            if (mDataSetObserver != null) {
                mCursor.registerDataSetObserver(mDataSetObserver);
            }
            mRowIdColumn = newCursor.getColumnIndexOrThrow(mIdColumn);
            mDataValid = true;
            prepareNewCursor(mCursor);
            notifyDataSetChanged();
        } else {
            mRowIdColumn = -1;
            mDataValid = false;
            notifyDataSetChanged();
        }
        return oldCursor;
    }

    private class NotifyingDataSetObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            mDataValid = true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            mDataValid = false;
            notifyDataSetChanged();
        }
    }
}