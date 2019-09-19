package com.oriondev.moneywallet.background;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public abstract class AbstractGenericLoader<T> extends AsyncTaskLoader<T> {

    private T mGenericData;

    public AbstractGenericLoader(Context context) {
        super(context);
    }

    @Override
    public abstract T loadInBackground();

    /* Runs on the UI thread */
    @Override
    public void deliverResult(T genericData) {
        mGenericData = genericData;
        if (isStarted()) {
            super.deliverResult(genericData);
        }
    }

    /**
     * Starts an asynchronous load of the contacts list data. When the result is ready the callbacks
     * will be called on the UI thread. If a previous load has been completed and is still valid
     * the result may be passed to the callbacks immediately.
     * <p/>
     * Must be called from the UI thread
     */
    @Override
    protected void onStartLoading() {
        if (mGenericData != null) {
            deliverResult(mGenericData);
        }
        if (takeContentChanged() || mGenericData == null) {
            forceLoad();
        }
    }

    /**
     * Must be called from the UI thread
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        // Ensure the loader is stopped
        onStopLoading();
        mGenericData = null;
    }
}