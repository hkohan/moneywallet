package com.oriondev.moneywallet.ui.adapter.recycler;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oriondev.moneywallet.R;

import java.util.ArrayList;
import java.util.List;


public class SettingCategoryAdapter extends RecyclerView.Adapter<SettingCategoryAdapter.ViewHolder> {

    private final List<Item> mItems;
    private final ActionListener mActionListener;

    public SettingCategoryAdapter(ActionListener actionListener) {
        mItems = new ArrayList<>();
        mActionListener = actionListener;
    }

    public void addCategory(int id, @DrawableRes int icon, @StringRes int primaryText, @StringRes int secondaryText) {
        mItems.add(new Item(id, icon, primaryText, secondaryText));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_setting_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = mItems.get(position);
        holder.mIconImageView.setImageResource(item.mIconRes);
        holder.mPrimaryTextView.setText(item.mPrimaryTextRes);
        holder.mSecondaryTextView.setText(item.mSecondaryTextRes);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mIconImageView;
        private TextView mPrimaryTextView;
        private TextView mSecondaryTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mIconImageView = itemView.findViewById(R.id.icon_image_view);
            mPrimaryTextView = itemView.findViewById(R.id.primary_text_view);
            mSecondaryTextView = itemView.findViewById(R.id.secondary_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mActionListener != null) {
                Item item = mItems.get(getAdapterPosition());
                mActionListener.onSettingCategoryClick(item.mId);
            }
        }
    }

    private class Item {

        private final int mId;
        private final int mIconRes;
        private final int mPrimaryTextRes;
        private final int mSecondaryTextRes;

        private Item(int id, int iconRes, int primaryTextRes, int secondaryTextRes) {
            mId = id;
            mIconRes = iconRes;
            mPrimaryTextRes = primaryTextRes;
            mSecondaryTextRes = secondaryTextRes;
        }
    }

    public interface ActionListener {

        void onSettingCategoryClick(int id);
    }
}