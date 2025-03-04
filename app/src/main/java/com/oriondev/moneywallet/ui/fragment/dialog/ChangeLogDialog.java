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
import com.oriondev.moneywallet.background.ChangeLogLoader;
import com.oriondev.moneywallet.model.ChangeLog;
import com.oriondev.moneywallet.ui.adapter.recycler.ChangeLogAdapter;
import com.oriondev.moneywallet.ui.view.theme.ThemedDialog;

import java.util.List;


public class ChangeLogDialog extends DialogFragment implements LoaderManager.LoaderCallbacks<List<ChangeLog>> {

    private static final int LOADER_ID = 3647;

    public static void showSafely(FragmentManager fragmentManager, String tag) {
        ChangeLogDialog dialog = (ChangeLogDialog) fragmentManager.findFragmentByTag(tag);
        if (dialog == null) {
            dialog = new ChangeLogDialog();
            dialog.show(fragmentManager, tag);
        } else {
            fragmentManager.beginTransaction().show(dialog).commit();
        }
    }

    private ChangeLogAdapter mAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        if (activity == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        mAdapter = new ChangeLogAdapter();
        MaterialDialog.Builder builder = ThemedDialog.buildMaterialDialog(activity)
                .title(R.string.dialog_change_log_title)
                .positiveText(android.R.string.ok)
                .adapter(mAdapter, new LinearLayoutManager(activity));
        getLoaderManager().restartLoader(LOADER_ID, null, this);
        return builder.build();
    }

    @Override
    public Loader<List<ChangeLog>> onCreateLoader(int id, Bundle args) {
        Activity activity = getActivity();
        if (activity != null) {
            return new ChangeLogLoader(activity);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<ChangeLog>> loader, List<ChangeLog> data) {
        if (mAdapter != null) {
            mAdapter.setChangeLogs(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ChangeLog>> loader) {
        if (mAdapter != null) {
            mAdapter.setChangeLogs(null);
        }
    }

}