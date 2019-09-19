package com.oriondev.moneywallet.ui.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.model.License;

import java.util.List;


public class LicenseAdapter extends RecyclerView.Adapter<LicenseAdapter.ViewHolder> {

    private final Controller mController;
    private List<License> mLicenses;

    public LicenseAdapter(Controller controller) {
        mController = controller;
    }

    public void setLicenses(List<License> licenses) {
        mLicenses = licenses;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_license_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        License license = mLicenses.get(position);
        holder.mPrimaryTextView.setText(license.getName());
        holder.mSecondaryTextView.setText(license.getTypeName());
    }

    @Override
    public int getItemCount() {
        return mLicenses!= null ? mLicenses.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mPrimaryTextView;
        private TextView mSecondaryTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mPrimaryTextView = itemView.findViewById(R.id.primary_text_view);
            mSecondaryTextView = itemView.findViewById(R.id.secondary_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mController != null) {
                mController.onLicenseClick(mLicenses.get(getAdapterPosition()));
            }
        }
    }

    public interface Controller {

        void onLicenseClick(License license);
    }
}