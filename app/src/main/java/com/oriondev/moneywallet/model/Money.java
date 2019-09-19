package com.oriondev.moneywallet.model;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class Money {

    private Map<String, Long> mCurrencies;

    public static Money empty() {
        return new Money();
    }

    public Money() {
        mCurrencies = new HashMap<>();
    }

    public Money(String currency, long money) {
        mCurrencies = new HashMap<>();
        mCurrencies.put(currency, money);
    }

    public void addMoney(String currency, long money) {
        long total = money;
        if (mCurrencies.containsKey(currency)) {
            total += mCurrencies.get(currency);
        }
        mCurrencies.put(currency, total);
    }

    public void addMoney(Money money) {
        if (money != null) {
            for (Map.Entry<String, Long> currency : money.mCurrencies.entrySet()) {
                addMoney(currency.getKey(), currency.getValue());
            }
        }
    }

    public void removeMoney(String currency, long money) {
        long total = -money;
        if (mCurrencies.containsKey(currency)) {
            total += mCurrencies.get(currency);
        }
        mCurrencies.put(currency, total);
    }

    public void removeMoney(Money money) {
        if (money != null) {
            for (Map.Entry<String, Long> currency : money.mCurrencies.entrySet()) {
                removeMoney(currency.getKey(), currency.getValue());
            }
        }
    }

    public long getMoney(String currency) {
        Long money = mCurrencies.get(currency);
        return money != null ? money : 0;
    }

    public Map<String, Long> getCurrencyMoneys() {
        return mCurrencies;
    }

    public Set<String> getCurrencies() {
        return mCurrencies.keySet();
    }

    public int getNumberOfCurrencies() {
        return mCurrencies.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Long> entry : mCurrencies.entrySet()) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(String.format(Locale.ENGLISH, "%s %d", entry.getKey(), entry.getValue()));
        }
        return builder.toString();
    }

    public static Money parse(String string) {
        Money money = new Money();
        if (!TextUtils.isEmpty(string)) {
            String[] currencies = string.split(",");
            for (String currency : currencies) {
                String[] parts = currency.split(" ");
                money.addMoney(parts[0], Long.parseLong(parts[1]));
            }
        }
        return money;
    }
}