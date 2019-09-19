package com.oriondev.moneywallet;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.oriondev.moneywallet.broadcast.AutoBackupBroadcastReceiver;
import com.oriondev.moneywallet.broadcast.DailyBroadcastReceiver;
import com.oriondev.moneywallet.broadcast.RecurrenceBroadcastReceiver;
import com.oriondev.moneywallet.storage.preference.BackendManager;
import com.oriondev.moneywallet.storage.preference.PreferenceManager;
import com.oriondev.moneywallet.ui.notification.NotificationContract;
import com.oriondev.moneywallet.ui.view.theme.ThemeEngine;
import com.oriondev.moneywallet.utils.CurrencyManager;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.initialize(this);
        BackendManager.initialize(this);
        ThemeEngine.initialize(this);
        CurrencyManager.initialize(this);
        NotificationContract.initializeNotificationChannels(this);
        initializeScheduledTimers();
    }

    private void initializeScheduledTimers() {
        // The application may be killed by the OS when resources are needed or by the user for
        // every kind of reasons. When the application is killed all the scheduled operations are
        // canceled by the OS. This is the best place where all those things can be scheduled again.
        DailyBroadcastReceiver.scheduleDailyNotification(this);
        RecurrenceBroadcastReceiver.scheduleRecurrenceTask(this);
        AutoBackupBroadcastReceiver.scheduleAutoBackupTask(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}