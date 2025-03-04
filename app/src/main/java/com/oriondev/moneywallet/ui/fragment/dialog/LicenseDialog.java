package com.oriondev.moneywallet.ui.fragment.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.background.LicenseLoader;
import com.oriondev.moneywallet.model.License;
import com.oriondev.moneywallet.ui.adapter.recycler.LicenseAdapter;
import com.oriondev.moneywallet.ui.view.theme.ThemedDialog;

import java.util.List;


public class LicenseDialog extends DialogFragment implements LoaderManager.LoaderCallbacks<List<License>>,LicenseAdapter.Controller {

    private static final int LOADER_ID = 3698;

    public static void showSafely(FragmentManager fragmentManager, String tag, Callback callback) {
        LicenseDialog dialog = (LicenseDialog) fragmentManager.findFragmentByTag(tag);
        if (dialog == null) {
            dialog = new LicenseDialog();
            dialog.setCallback(callback);
            dialog.show(fragmentManager, tag);
        } else {
            dialog.setCallback(callback);
            fragmentManager.beginTransaction().show(dialog).commit();
        }
    }

    private Callback mCallback;
    private LicenseAdapter mAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        if (activity == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        mAdapter = new LicenseAdapter(this);
        MaterialDialog.Builder builder = ThemedDialog.buildMaterialDialog(activity)
                .title(R.string.title_licenses)
                .adapter(mAdapter, new LinearLayoutManager(activity))
                .positiveText(android.R.string.ok);
        getLoaderManager().restartLoader(LOADER_ID, null, this);
        return builder.build();
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public Loader<List<License>> onCreateLoader(int id, Bundle args) {
        Activity activity = getActivity();
        if (activity != null) {
            return new LicenseLoader(activity);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<License>> loader, List<License> data) {
        if (mAdapter != null) {
            mAdapter.setLicenses(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<License>> loader) {
        if (mAdapter != null) {
            mAdapter.setLicenses(null);
        }
    }

    @Override
    public void onLicenseClick(License license) {
        if (mCallback != null) {
            mCallback.onLicenseClick(license);
        }
    }

    public interface Callback {

        void onLicenseClick(License license);
    }
}