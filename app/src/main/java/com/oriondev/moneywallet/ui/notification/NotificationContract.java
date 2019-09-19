package com.oriondev.moneywallet.ui.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.oriondev.moneywallet.R;


public class NotificationContract {

    public static final String NOTIFICATION_CHANNEL_BACKUP = "channel_backup";
    public static final String NOTIFICATION_CHANNEL_EXCHANGE_RATE = "channel_exchange_rate";
    public static final String NOTIFICATION_CHANNEL_REMINDER = "channel_reminder";
    public static final String NOTIFICATION_CHANNEL_ERROR = "channel_error";

    public static final int NOTIFICATION_ID_BACKUP_PROGRESS = 23454;
    public static final int NOTIFICATION_ID_BACKUP_ERROR = 23455;
    public static final int NOTIFICATION_ID_EXCHANGE_RATE_PROGRESS = 23456;
    public static final int NOTIFICATION_ID_EXCHANGE_RATE_ERROR = 23457;
    public static final int NOTIFICATION_ID_REMINDER = 23458;

    public static void initializeNotificationChannels(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // create backup channel: in this channel are posted all the notifications
            // regarding the progress of background operations while creating or
            // restoring a backup file (also auto backup)
            NotificationChannel channelBackup = new NotificationChannel(
                    NotificationContract.NOTIFICATION_CHANNEL_BACKUP,
                    context.getString(R.string.notification_channel_name_backup),
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationChannel channelExchangeRate = new NotificationChannel(
                    NotificationContract.NOTIFICATION_CHANNEL_EXCHANGE_RATE,
                    context.getString(R.string.notification_channel_name_exchange_rate),
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationChannel channelReminder = new NotificationChannel(
                    NotificationContract.NOTIFICATION_CHANNEL_REMINDER,
                    context.getString(R.string.notification_channel_name_reminder),
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationChannel channelError = new NotificationChannel(
                    NotificationContract.NOTIFICATION_CHANNEL_ERROR,
                    context.getString(R.string.notification_channel_name_error),
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            // disable the badge for all the channels
            channelBackup.setShowBadge(false);
            channelExchangeRate.setShowBadge(false);
            channelReminder.setShowBadge(false);
            channelError.setShowBadge(false);
            // Register the channels with the system
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channelBackup);
                notificationManager.createNotificationChannel(channelExchangeRate);
                notificationManager.createNotificationChannel(channelReminder);
                notificationManager.createNotificationChannel(channelError);
            }
        }
    }
}