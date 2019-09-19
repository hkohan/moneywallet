package com.oriondev.moneywallet.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class BootBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            DailyBroadcastReceiver.scheduleDailyNotification(context);
            RecurrenceBroadcastReceiver.scheduleRecurrenceTask(context);
            AutoBackupBroadcastReceiver.scheduleAutoBackupTask(context);
        }
    }
}