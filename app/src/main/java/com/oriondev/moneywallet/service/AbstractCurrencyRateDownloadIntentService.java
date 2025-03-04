package com.oriondev.moneywallet.service;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.service.openexchangerates.OpenExchangeRatesCurrencyRateDownloadIntentService;
import com.oriondev.moneywallet.broadcast.LocalAction;
import com.oriondev.moneywallet.model.CurrencyUnit;
import com.oriondev.moneywallet.storage.cache.ExchangeRateCache;
import com.oriondev.moneywallet.storage.preference.PreferenceManager;
import com.oriondev.moneywallet.ui.notification.NotificationContract;
import com.oriondev.moneywallet.utils.CurrencyManager;
import com.oriondev.moneywallet.utils.Utils;


public abstract class AbstractCurrencyRateDownloadIntentService extends IntentService {

    public static Intent buildIntent(Activity activity) {
        int service = PreferenceManager.getCurrentExchangeRateService();
        switch (service) {
            case PreferenceManager.SERVICE_OPEN_EXCHANGE_RATE:
                return new Intent(activity, OpenExchangeRatesCurrencyRateDownloadIntentService.class);
            default:
                throw new IllegalStateException("Unknown exchange rate service: " + service);
        }
    }

    private ExchangeRateCache mCache;
    private NotificationCompat.Builder mBuilder;

    public AbstractCurrencyRateDownloadIntentService(String name) {
        super(name);
        mCache = CurrencyManager.getExchangeRateCache();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mBuilder = new NotificationCompat.Builder(getBaseContext(), NotificationContract.NOTIFICATION_CHANNEL_EXCHANGE_RATE)
                .setSmallIcon(Utils.isAtLeastLollipop() ? R.drawable.ic_notification : R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.notification_title_download_exchange_rates))
                .setProgress(0, 0, true)
                .setCategory(NotificationCompat.CATEGORY_PROGRESS);
        startForeground(NotificationContract.NOTIFICATION_ID_EXCHANGE_RATE_PROGRESS, mBuilder.build());
        Exception exception = null;
        try {
            updateExchangeRates();
            sendBroadcastMessage();
        } catch (Exception e) {
            exception = e;
        } finally {
            stopForeground(true);
        }
        if (exception != null) {
            showError(exception.getMessage());
        }
    }

    protected abstract void updateExchangeRates() throws Exception;

    private void sendBroadcastMessage() {
        PreferenceManager.setLastExchangeRateUpdateTimestamp(System.currentTimeMillis());
        Intent intent = new Intent(LocalAction.ACTION_EXCHANGE_RATES_UPDATED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    protected void storeExchangeRate(CurrencyUnit currency, double rate, long timestamp) {
        mCache.setExchangeRate(currency.getIso(), rate, timestamp);
    }

    protected void setCurrentProgress(String operation, int percentage) {
        mBuilder.setContentText(operation)
                .setProgress(100, percentage, false);
        startForeground(NotificationContract.NOTIFICATION_ID_EXCHANGE_RATE_PROGRESS, mBuilder.build());
    }

    private void showError(String error) {
        mBuilder = new NotificationCompat.Builder(getBaseContext(), NotificationContract.NOTIFICATION_CHANNEL_ERROR)
                .setSmallIcon(Utils.isAtLeastLollipop() ? R.drawable.ic_notification : R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.notification_title_download_exchange_rates))
                .setContentText(getString(R.string.notification_content_error_message, error))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.notification_content_error_message, error)))
                .setCategory(NotificationCompat.CATEGORY_ERROR);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NotificationContract.NOTIFICATION_ID_EXCHANGE_RATE_ERROR, mBuilder.build());
    }

    public class MissingApiKey extends Exception {

        public MissingApiKey(String message) {
            super(message);
        }
    }
}