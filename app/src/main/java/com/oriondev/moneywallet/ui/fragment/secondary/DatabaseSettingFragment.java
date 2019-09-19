package com.oriondev.moneywallet.ui.fragment.secondary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.activity.BackupListActivity;
import com.oriondev.moneywallet.ui.activity.ImportExportActivity;


public class DatabaseSettingFragment extends PreferenceFragmentCompat {

    private Preference mBackupServicesPreference;
    private Preference mImportDataPreference;
    private Preference mExportDataPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_database);
        mBackupServicesPreference = findPreference("backup_services");
        mImportDataPreference = findPreference("import_data");
        mExportDataPreference = findPreference("export_data");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBackupServicesPreference.setOnPreferenceClickListener(new android.support.v7.preference.Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(android.support.v7.preference.Preference preference) {
                Intent intent = new Intent(getActivity(), BackupListActivity.class);
                intent.putExtra(BackupListActivity.BACKUP_MODE, BackupListActivity.FULL);
                startActivity(intent);
                return false;
            }

        });
        mImportDataPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ImportExportActivity.class);
                intent.putExtra(ImportExportActivity.MODE, ImportExportActivity.MODE_IMPORT);
                startActivity(intent);
                return false;
            }

        });
        mExportDataPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ImportExportActivity.class);
                intent.putExtra(ImportExportActivity.MODE, ImportExportActivity.MODE_EXPORT);
                startActivity(intent);
                return false;
            }

        });
    }

    @Override
    public RecyclerView onCreateRecyclerView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        RecyclerView recyclerView = super.onCreateRecyclerView(inflater, parent, savedInstanceState);
        recyclerView.setPadding(0, 0, 0, 0);
        return recyclerView;
    }
}