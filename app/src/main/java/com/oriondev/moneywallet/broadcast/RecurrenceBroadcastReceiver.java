package com.oriondev.moneywallet.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.oriondev.moneywallet.service.RecurrenceHandlerIntentService;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.storage.database.DataContentProvider;
import com.oriondev.moneywallet.utils.DateUtils;

import java.util.Date;


public class RecurrenceBroadcastReceiver extends BroadcastReceiver {

    public static void scheduleRecurrenceTask(Context context) {
        cancelPendingIntent(context);
        ContentResolver contentResolver = context.getContentResolver();
        Date nextOccurrence1 = getMinNextRecurrentTransactionOccurrence(contentResolver);
        Date nextOccurrence2 = getMinNextRecurrentTransferOccurrence(contentResolver);
        Date nextOccurrence = getMinDate(nextOccurrence1, nextOccurrence2);
        if (nextOccurrence != null) {
            System.out.println("[ALARM] Next occurrence is at: " + nextOccurrence.toString());
            if (DateUtils.isBeforeNow(nextOccurrence)) {
                startBackgroundTask(context);
            } else {
                schedulePendingIntent(context, nextOccurrence);
            }
        }
    }

    private static Date getMinNextRecurrentTransactionOccurrence(ContentResolver contentResolver) {
        Uri uri = DataContentProvider.CONTENT_RECURRENT_TRANSACTIONS;
        String[] projection = new String[] {
                "MIN(" + Contract.RecurrentTransaction.NEXT_OCCURRENCE + ")"
        };
        String selection = Contract.RecurrentTransaction.NEXT_OCCURRENCE  + " IS NOT NULL";
        Cursor cursor = contentResolver.query(uri, projection, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String nextOccurrenceString = cursor.getString(0);
                if (!TextUtils.isEmpty(nextOccurrenceString)) {
                    return DateUtils.getDateFromSQLDateString(nextOccurrenceString);
                }
            }
            cursor.close();
        }
        return null;
    }

    private static Date getMinNextRecurrentTransferOccurrence(ContentResolver contentResolver) {
        Uri uri = DataContentProvider.CONTENT_RECURRENT_TRANSFERS;
        String[] projection = new String[] {
                "MIN(" + Contract.RecurrentTransfer.NEXT_OCCURRENCE + ")"
        };
        String selection = Contract.RecurrentTransfer.NEXT_OCCURRENCE  + " IS NOT NULL";
        Cursor cursor = contentResolver.query(uri, projection, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String nextOccurrenceString = cursor.getString(0);
                if (!TextUtils.isEmpty(nextOccurrenceString)) {
                    return DateUtils.getDateFromSQLDateString(nextOccurrenceString);
                }
            }
            cursor.close();
        }
        return null;
    }

    private static Date getMinDate(Date date1, Date date2) {
        if (date1 != null) {
            if (date2 != null) {
                if (DateUtils.isBefore(date1, date2)) {
                    return date1;
                } else {
                    return date2;
                }
            } else {
                return date1;
            }
        } else if (date2 != null) {
            return date2;
        }
        return null;
    }

    private static void schedulePendingIntent(Context context, Date date) {
        PendingIntent pendingIntent = createPendingIntent(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
            System.out.println("[ALARM] RecurrenceTask scheduled at: " + date.toString());
        }
    }

    private static void cancelPendingIntent(Context context) {
        PendingIntent pendingIntent = createPendingIntent(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private static PendingIntent createPendingIntent(Context context) {
        Intent intent = new Intent(context, RecurrenceBroadcastReceiver.class);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        startBackgroundTask(context);
    }

    private static void startBackgroundTask(Context context) {
        System.out.println("[ALARM] RecurrenceTask fired now");
        RecurrenceHandlerIntentService.enqueueWork(context, new Intent());
    }
}