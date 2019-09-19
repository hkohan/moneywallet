package com.oriondev.moneywallet.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.oriondev.moneywallet.R;
import com.pnikosis.materialishprogress.ProgressWheel;


public class AdvancedRecyclerView extends SwipeRefreshLayout {

    private RecyclerView mRecyclerView;
    private ProgressWheel mProgressWheel;
    private TextView mEmptyTextView;

    private int mEmptyTextRes;
    private int mErrorTextRes;
    private State mCurrentState;

    public AdvancedRecyclerView(Context context) {
        super(context);
        initialize(context);
    }

    public AdvancedRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(@NonNull Context context) {
        inflate(context, R.layout.view_advanced_recycler, this);
        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressWheel = findViewById(R.id.progress_wheel);
        mEmptyTextView = findViewById(R.id.empty_text_view);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public ProgressWheel getProgressWheel() {
        return mProgressWheel;
    }

    public TextView getTextView() {
        return mEmptyTextView;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void setEmptyText(@StringRes int resId) {
        mEmptyTextRes = resId;
    }

    public void setErrorText(@StringRes int resId) {
        mErrorTextRes = resId;
    }

    public void setState(State state) {
        if (mCurrentState != state) {
            switch (state) {
                case EMPTY:
                    mRecyclerView.setVisibility(View.GONE);
                    mProgressWheel.setVisibility(View.GONE);
                    mEmptyTextView.setText(mEmptyTextRes);
                    mEmptyTextView.setVisibility(View.VISIBLE);
                    setRefreshing(false);
                    break;
                case LOADING:
                    mRecyclerView.setVisibility(View.GONE);
                    mProgressWheel.setVisibility(View.VISIBLE);
                    mEmptyTextView.setVisibility(View.GONE);
                    setRefreshing(false);
                    break;
                case REFRESHING:
                    mRecyclerView.setVisibility(View.GONE);
                    mProgressWheel.setVisibility(View.GONE);
                    mEmptyTextView.setVisibility(View.GONE);
                    setRefreshing(true);
                    break;
                case READY:
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mProgressWheel.setVisibility(View.GONE);
                    mEmptyTextView.setVisibility(View.GONE);
                    setRefreshing(false);
                    break;
                case ERROR:
                    mRecyclerView.setVisibility(View.GONE);
                    mProgressWheel.setVisibility(View.GONE);
                    mEmptyTextView.setText(mErrorTextRes);
                    mEmptyTextView.setVisibility(View.VISIBLE);
                    setRefreshing(false);
                    break;
            }
        }
    }

    public enum State {
        EMPTY,
        LOADING,
        REFRESHING,
        READY,
        ERROR
    }
}