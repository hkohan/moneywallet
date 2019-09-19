package com.oriondev.moneywallet.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.oriondev.moneywallet.broadcast.LocalAction;
import com.oriondev.moneywallet.broadcast.RecurrenceBroadcastReceiver;
import com.oriondev.moneywallet.storage.database.DataContentProvider;
import com.oriondev.moneywallet.storage.database.LegacyEditionImporter;
import com.oriondev.moneywallet.storage.preference.PreferenceManager;
import com.oriondev.moneywallet.utils.CurrencyManager;


public class UpgradeLegacyEditionIntentService extends IntentService {

    public static final String ERROR_MESSAGE = "UpgradeLegacyEditionIntentService::ErrorMessage";

    private final LocalBroadcastManager mBroadcastManager;

    public static boolean isLegacyEditionDetected(Context context) {
        // check if exists a legacy database to import
        return context.getDatabasePath(LegacyEditionImporter.DATABASE_NAME).exists();
    }

    public UpgradeLegacyEditionIntentService() {
        super("UpgradeLegacyEditionIntentService");
        mBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        notifyServiceStarted();
        Exception exception = null;
        try {
            LegacyEditionImporter importer = new LegacyEditionImporter(this);
            importer.importDatabase();
            importer.importAttachments();
            importer.importPreferences();
        } catch (Exception e) {
            exception = e;
        }
        if (exception != null) {
            notifyServiceFailed(exception.getMessage());
        } else {
            DataContentProvider.notifyDatabaseIsChanged(this);
            CurrencyManager.invalidateCache(this);
            PreferenceManager.setLastTimeDataIsChanged(0L);
            RecurrenceBroadcastReceiver.scheduleRecurrenceTask(this);
            notifyServiceFinished();
        }
    }

    private void notifyServiceStarted() {
        Intent intent = new Intent(LocalAction.ACTION_LEGACY_EDITION_UPGRADE_STARTED);
        mBroadcastManager.sendBroadcast(intent);
    }

    private void notifyServiceFailed(String message) {
        Intent intent = new Intent(LocalAction.ACTION_LEGACY_EDITION_UPGRADE_FAILED);
        intent.putExtra(ERROR_MESSAGE, message);
        mBroadcastManager.sendBroadcast(intent);
    }

    private void notifyServiceFinished() {
        Intent intent = new Intent(LocalAction.ACTION_LEGACY_EDITION_UPGRADE_FINISHED);
        mBroadcastManager.sendBroadcast(intent);
    }
}