package com.oriondev.moneywallet.ui.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.model.ChangeLog;
import com.oriondev.moneywallet.storage.cache.TypefaceCache;

import java.util.List;

public class ChangeLogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChangeLog> mChangeLogList;

    public ChangeLogAdapter() {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ChangeLog.TYPE_HEADER:
                return new HeaderViewHolder(inflater.inflate(R.layout.adapter_change_log_header, parent, false));
            case ChangeLog.TYPE_ITEM:
                return new ItemViewHolder(inflater.inflate(R.layout.adapter_change_log_item, parent, false));
            default:
                throw new IllegalArgumentException("Invalid item view type: " + viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mChangeLogList.get(position).getType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChangeLog item = mChangeLogList.get(position);
        if (holder instanceof HeaderViewHolder) {
            Context context = ((HeaderViewHolder) holder).mVersionTextView.getContext();
            String versionName = String.format(context.getString(R.string.relative_string_version_name), item.getVersionName());
            ((HeaderViewHolder) holder).mVersionTextView.setText(versionName);
            ((HeaderViewHolder) holder).mDateTextView.setText(item.getVersionDate());
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).mDescriptionTextView.setText(Html.fromHtml(item.getChangeText()));
            ((ItemViewHolder) holder).mIndexTextView.setText(getIndex(position));
        }
    }

    private String getIndex(int position) {
        int index = 0;
        while (mChangeLogList.get(position).getType() != ChangeLog.TYPE_HEADER) {
            index ++;
            position --;
        }
        return String.valueOf(index) + " - ";
    }

    public void setChangeLogs(List<ChangeLog> changeLogList) {
        mChangeLogList = changeLogList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mChangeLogList != null ? mChangeLogList.size() : 0;
    }

    /*package-local*/ static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView mVersionTextView;
        private TextView mDateTextView;

        /*package-local*/ HeaderViewHolder(View itemView) {
            super(itemView);
            mVersionTextView = itemView.findViewById(R.id.version_name_text_view);
            mDateTextView = itemView.findViewById(R.id.version_date_text_view);
            mVersionTextView.setTypeface(TypefaceCache.get(itemView.getContext(), TypefaceCache.ROBOTO_MEDIUM));
            mDateTextView.setTypeface(TypefaceCache.get(itemView.getContext(), TypefaceCache.ROBOTO_MEDIUM));
        }
    }

    /*package-local*/ static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mDescriptionTextView;
        private TextView mIndexTextView;

        /*package-local*/ ItemViewHolder(View itemView) {
            super(itemView);
            mDescriptionTextView = itemView.findViewById(R.id.description_text_view);
            mIndexTextView = itemView.findViewById(R.id.index_text_view);
            mDescriptionTextView.setTypeface(TypefaceCache.get(itemView.getContext(), TypefaceCache.ROBOTO_REGULAR));
            mIndexTextView.setTypeface(TypefaceCache.get(itemView.getContext(), TypefaceCache.ROBOTO_REGULAR));
        }
    }
}