package com.oriondev.moneywallet.ui.fragment.stub;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oriondev.moneywallet.ui.view.AdvancedRecyclerView;

import java.util.ArrayList;
import java.util.List;


public class StubListFragment extends Fragment {

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(getActivity());
        ArrayAdapter adapter = new ArrayAdapter();
        for (int i = 0; i < 100; i++) {
            adapter.add("Item " + i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return recyclerView;
    }

    protected static class ArrayAdapter extends RecyclerView.Adapter<ArrayAdapter.StubViewHolder> {

        private List<String> mData;

        /*package-local*/ ArrayAdapter() {
            mData = new ArrayList<>();
        }

        public void add(String item) {
            mData.add(item);
        }

        @Override
        public StubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new StubViewHolder(view);
        }

        @Override
        public void onBindViewHolder(StubViewHolder holder, int position) {
            holder.mTextView.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        /*package-local*/ class StubViewHolder extends RecyclerView.ViewHolder {

            /*package-local*/ TextView mTextView;

            /*package-local*/ StubViewHolder(View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(android.R.id.text1);
                itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                    }

                });
            }
        }
    }
}