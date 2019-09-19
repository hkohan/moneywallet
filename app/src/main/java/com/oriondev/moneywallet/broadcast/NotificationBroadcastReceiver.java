package com.oriondev.moneywallet.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;

import com.oriondev.moneywallet.service.BackupHandlerIntentService;
import com.oriondev.moneywallet.ui.notification.NotificationContract;


public class NotificationBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_RETRY_BACKUP_CREATION = "NotificationBroadcastReceiver::Action::RetryBackupCreation";

    public static final String ACTION_INTENT_ARGUMENTS = "NotificationBroadcastReceiver::Intent::Arguments";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null) {
            if (ACTION_RETRY_BACKUP_CREATION.equals(intent.getAction())) {
                // cancel the old notification
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.cancel(NotificationContract.NOTIFICATION_ID_BACKUP_ERROR);
                // re-start the intent service
                Bundle arguments = intent.getBundleExtra(ACTION_INTENT_ARGUMENTS);
                if (arguments != null) {
                    restartAutoBackupIntentService(context, arguments);
                }
            }
        }
    }

    private void restartAutoBackupIntentService(Context context, Bundle arguments) {
        Intent intent = new Intent(context, BackupHandlerIntentService.class);
        intent.putExtras(arguments);
        BackupHandlerIntentService.startInForeground(context, intent);
    }
}