package com.oriondev.moneywallet.service.openexchangerates;

import android.text.TextUtils;

import com.oriondev.moneywallet.BuildConfig;
import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.model.CurrencyUnit;
import com.oriondev.moneywallet.service.AbstractCurrencyRateDownloadIntentService;
import com.oriondev.moneywallet.storage.preference.PreferenceManager;
import com.oriondev.moneywallet.utils.CurrencyManager;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;


public class OpenExchangeRatesCurrencyRateDownloadIntentService extends AbstractCurrencyRateDownloadIntentService {

    private static final String INVALID_API_KEY = "INSERT_API_KEY_HERE";

    public OpenExchangeRatesCurrencyRateDownloadIntentService() {
        super("OpenExchangeRatesCurrencyRateDownloadIntentService");
    }

    @Override
    protected void updateExchangeRates() throws Exception {
        setCurrentProgress(getString(R.string.notification_title_download_exchange_rates), 0);
        JSONObject response = downloadLatestExchangeRates();
        if (!response.optBoolean("error", false)) {
            storeResponse(response);
        } else {
            throw new IOException(response.optString("message", "unknown"));
        }
    }

    private String buildUrl() throws MissingApiKey {
        String apiKey = BuildConfig.API_KEY_OPEN_EXCHANGE_RATES;
        if (TextUtils.isEmpty(apiKey) || INVALID_API_KEY.equals(apiKey)) {
            // the developer has not provided a valid api key during the build,
            // we can check if the user has provided an api key in preferences
            apiKey = PreferenceManager.getServiceApiKey(PreferenceManager.SERVICE_OPEN_EXCHANGE_RATE);
        }
        if (!TextUtils.isEmpty(apiKey)) {
            return "https://openexchangerates.org/api/latest.json?app_id=" + apiKey;
        } else {
            throw new MissingApiKey("missing API KEY for OpenExchangeRates.org");
        }
    }

    private JSONObject downloadLatestExchangeRates() throws Exception {
        InputStream inputStream = null;
        try {
            URL url = new URL(buildUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            if (connection.getResponseCode() != 401) {
                inputStream = connection.getInputStream();
                return new JSONObject(IOUtils.toString(inputStream, "UTF-8"));
            } else {
                throw new MissingApiKey("Invalid api-key");
            }
        } catch (IOException e) {
            throw new IOException(getString(R.string.message_error_network_connection));
        } catch (JSONException e) {
            throw new JSONException(getString(R.string.message_error_invalid_response));
        } catch (MissingApiKey e) {
            throw new JSONException(getString(R.string.message_error_missing_api_key));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private void storeResponse(JSONObject response) throws Exception {
        CurrencyUnit base = CurrencyManager.getCurrency(response.getString("base"));
        long timestamp = response.getLong("timestamp");
        JSONObject rates = response.getJSONObject("rates");
        Collection<CurrencyUnit> currencies = CurrencyManager.getCurrencies();
        float progress = 35;
        float offset = (100f - progress) / currencies.size();
        for (CurrencyUnit currency : currencies) {
            progress += offset;
            setCurrentProgress(getString(R.string.notification_content_caching_data), (int) progress);
            if (rates.has(currency.getIso())) {
                double rate = rates.getDouble(currency.getIso());
                storeExchangeRate(currency, rate, timestamp);
            }
        }
    }
}