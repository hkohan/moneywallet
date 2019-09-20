package com.oriondev.moneywallet.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.broadcast.LocalAction;
import com.oriondev.moneywallet.broadcast.RecurrenceBroadcastReceiver;
import com.oriondev.moneywallet.service.UpgradeLegacyEditionIntentService;
import com.oriondev.moneywallet.storage.preference.PreferenceManager;
import com.oriondev.moneywallet.ui.activity.base.ThemedActivity;
import com.oriondev.moneywallet.ui.view.theme.ThemedDialog;
import com.pnikosis.materialishprogress.ProgressWheel;
import co.ronash.pushe.Pushe;


public class LauncherActivity extends ThemedActivity {

    private static final String SS_UPGRADE_ERROR = "LauncherActivity::SavedState::UpgradeLegacyEditionError";

    private static final int REQUEST_FIRST_START = 273;

    private String mUpgradeLegacyEditionError = null;

    private ProgressWheel mProgressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Pushe.initialize(this,true);
        if (UpgradeLegacyEditionIntentService.isLegacyEditionDetected(this)) {
            setContentView(R.layout.activity_launcher_legacy_edition_upgrade);
            mProgressWheel = findViewById(R.id.progress_wheel);
            // prepare the broadcast receiver
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(LocalAction.ACTION_LEGACY_EDITION_UPGRADE_STARTED);
            intentFilter.addAction(LocalAction.ACTION_LEGACY_EDITION_UPGRADE_FINISHED);
            intentFilter.addAction(LocalAction.ACTION_LEGACY_EDITION_UPGRADE_FAILED);
            LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, intentFilter);
            // start the service
            if (savedInstanceState == null) {
                mProgressWheel.setVisibility(View.INVISIBLE);
                startService(new Intent(this, UpgradeLegacyEditionIntentService.class));
            } else {
                mUpgradeLegacyEditionError = savedInstanceState.getString(SS_UPGRADE_ERROR);
                showUpgradeLegacyEditionErrorMessage();
            }
        } else {
            if (!PreferenceManager.isFirstStartDone()) {
                setContentView(R.layout.activity_launcher_first_start);
                Button firstStartButton = findViewById(R.id.first_start_button);
                Button restoreBackupButton = findViewById(R.id.restore_backup_button);
                firstStartButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LauncherActivity.this, TutorialActivity.class);
                        startActivityForResult(intent, REQUEST_FIRST_START);
                    }

                });
                restoreBackupButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LauncherActivity.this, BackupListActivity.class);
                        intent.putExtra(BackupListActivity.BACKUP_MODE, BackupListActivity.RESTORE_ONLY);
                        startActivityForResult(intent, REQUEST_FIRST_START);
                    }

                });
            } else {
                startMainActivity();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SS_UPGRADE_ERROR, mUpgradeLegacyEditionError);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showUpgradeLegacyEditionErrorMessage() {
        if (mProgressWheel != null) {
            mProgressWheel.setVisibility(View.INVISIBLE);
        }
        ThemedDialog.buildMaterialDialog(LauncherActivity.this)
                .title(R.string.title_failed)
                .content(R.string.message_error_legacy_upgrade_failed, mUpgradeLegacyEditionError)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onAny(new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startMainActivity();
                    }

                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FIRST_START) {
            if (resultCode == RESULT_OK) {
                PreferenceManager.setIsFirstStartDone(true);
                startMainActivity();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction() != null) {
                switch (intent.getAction()) {
                    case LocalAction.ACTION_LEGACY_EDITION_UPGRADE_STARTED:
                        if (mProgressWheel != null) {
                            mProgressWheel.setVisibility(View.VISIBLE);
                        }
                        break;
                    case LocalAction.ACTION_LEGACY_EDITION_UPGRADE_FINISHED:
                        startMainActivity();
                        break;
                    case LocalAction.ACTION_LEGACY_EDITION_UPGRADE_FAILED:
                        mUpgradeLegacyEditionError = intent.getStringExtra(UpgradeLegacyEditionIntentService.ERROR_MESSAGE);
                        showUpgradeLegacyEditionErrorMessage();
                        break;
                }
            }
        }

    };
}