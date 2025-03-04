package com.oriondev.moneywallet.ui.fragment.multipanel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.model.BackupService;
import com.oriondev.moneywallet.ui.adapter.recycler.BackupServiceAdapter;
import com.oriondev.moneywallet.ui.fragment.base.MultiPanelFragment;
import com.oriondev.moneywallet.ui.fragment.secondary.BackupHandlerFragment;


public class BackupMultiPanelFragment extends MultiPanelFragment implements BackupServiceAdapter.Controller {

    private static final String ARG_ALLOW_BACKUP = "BackupMultiPanelFragment::Arguments::AllowBackup";
    private static final String ARG_ALLOW_RESTORE = "BackupMultiPanelFragment::Arguments::AllowRestore";
    private static final String SS_SHOW_COVER_LAYOUT = "BackupMultiPanelFragment::Arguments::ShowCoverLayout";

    public static BackupMultiPanelFragment newInstance(boolean allowBackup, boolean allowRestore) {
        BackupMultiPanelFragment fragment = new BackupMultiPanelFragment();
        Bundle arguments = new Bundle();
        arguments.putBoolean(ARG_ALLOW_BACKUP, allowBackup);
        arguments.putBoolean(ARG_ALLOW_RESTORE, allowRestore);
        fragment.setArguments(arguments);
        return fragment;
    }

    private ViewGroup mSecondaryPanelCoverLayout;
    private ViewGroup mSecondaryPanelFragmentContainer;

    private boolean mAllowBackup;
    private boolean mAllowRestore;

    private boolean mShowCoverLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mAllowBackup = arguments.getBoolean(ARG_ALLOW_BACKUP, true);
            mAllowRestore = arguments.getBoolean(ARG_ALLOW_RESTORE, true);
        } else {
            throw new IllegalStateException("Arguments bundle is null, please instantiate the fragment using the newInstance() method instead.");
        }
    }

    @Override
    protected void onCreatePrimaryPanel(LayoutInflater inflater, @NonNull ViewGroup primaryPanel, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recycler_view, primaryPanel, true);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new BackupServiceAdapter(this));
    }

    @Override
    protected void onCreateSecondaryPanel(LayoutInflater inflater, @NonNull ViewGroup secondaryPanel, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_secondary_panel_fragment_container, secondaryPanel, true);
        mSecondaryPanelCoverLayout = view.findViewById(R.id.secondary_panel_cover_layout);
        mSecondaryPanelFragmentContainer = view.findViewById(R.id.secondary_panel_fragment_container);
        // show the cover layout as default panel
        mShowCoverLayout = savedInstanceState == null || savedInstanceState.getBoolean(SS_SHOW_COVER_LAYOUT);
        showCoverLayout(mShowCoverLayout);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SS_SHOW_COVER_LAYOUT, mShowCoverLayout);
    }

    @Override
    protected int getTitleRes() {
        return R.string.title_activity_backup_services;
    }

    @Override
    protected boolean isFloatingActionButtonEnabled() {
        return false;
    }

    private void showCoverLayout(boolean show) {
        mSecondaryPanelCoverLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        mSecondaryPanelFragmentContainer.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onBackupServiceClick(BackupService service) {
        FragmentManager fragmentManager = getChildFragmentManager();
        String tag = getBackupServiceTag(service);
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager.beginTransaction().show(fragment).commit();
        } else if (service != null) {
            int containerId = mSecondaryPanelFragmentContainer.getId();
            fragment = BackupHandlerFragment.newInstance(service.getIdentifier(), mAllowBackup, mAllowRestore);
            fragmentManager.beginTransaction().replace(containerId, fragment, tag).commit();
        }
        mShowCoverLayout = service == null;
        showCoverLayout(mShowCoverLayout);
        showSecondaryPanel();
    }

    private String getBackupServiceTag(BackupService service) {
        return service != null ? "BackupMultiPanelFragment::Fragment::" + service.getIdentifier() : null;
    }
}