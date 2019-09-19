package com.oriondev.moneywallet.storage.database.legacy;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.oriondev.moneywallet.model.Group;
import com.oriondev.moneywallet.storage.preference.PreferenceManager;
import com.oriondev.moneywallet.utils.DateFormatter;

import java.util.Calendar;


public class LegacyUserPreferences {

    private final static String DATE_PATTERN = "DateType";
    private final static String COLOR_INFLOW = "ColorInflow";
    private final static String COLOR_OUTFLOW = "ColorOutflow";
    private final static String GROUP_TYPE = "GroupType";
    private final static String FIRST_DAY_OF_MONTH = "FirstDayOfMonth";
    private final static String FIRST_DAY_OF_WEEK = "FirstDayOfWeek";
    private final static String REMINDER_STATUS = "ReminderStatus";
    private final static String REMINDER_HOUR = "ReminderHour";
    private final static String CURRENCY_ENABLED = "CurrencySymbolEnabled";
    private final static String AMOUNT_PATTERN = "AmountDecimalFormatPattern";

    private static final int GROUP_DAILY = 0;
    private static final int GROUP_WEEKLY = 1;
    private static final int GROUP_MONTHLY = 2;
    private static final int GROUP_YEARLY = 3;

    private final static String GROUPED_DIGITS_PATTERN = "###,###,###,##0.00";
    private final static String NOT_GROUPED_DIGITS_PATTERN = "##0.00";
    private final static String GROUPED_DIGITS_PATTERN_ROUNDED = "###,###,###,##0";
    private final static String NOT_GROUPED_DIGITS_PATTERN_ROUNDED = "##0";
    public final static String CALCULATOR_PATTERN = "###,###,###,##0.00";

    private final SharedPreferences mPreferences;

    public LegacyUserPreferences(Context context) {
        mPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getDateFormat() {
        switch (mPreferences.getString(DATE_PATTERN, "EEE dd MMM yyyy")) {
            case DateFormatter.DATE_PATTERN_0:
                return PreferenceManager.DATE_FORMAT_TYPE_0;
            case DateFormatter.DATE_PATTERN_1:
                return PreferenceManager.DATE_FORMAT_TYPE_1;
            case DateFormatter.DATE_PATTERN_2:
                return PreferenceManager.DATE_FORMAT_TYPE_2;
            case DateFormatter.DATE_PATTERN_3:
                return PreferenceManager.DATE_FORMAT_TYPE_3;
            case DateFormatter.DATE_PATTERN_4:
                return PreferenceManager.DATE_FORMAT_TYPE_4;
            case DateFormatter.DATE_PATTERN_5:
                return PreferenceManager.DATE_FORMAT_TYPE_5;
            case DateFormatter.DATE_PATTERN_6:
                return PreferenceManager.DATE_FORMAT_TYPE_6;
            case DateFormatter.DATE_PATTERN_7:
                return PreferenceManager.DATE_FORMAT_TYPE_7;
            case DateFormatter.DATE_PATTERN_8:
                return PreferenceManager.DATE_FORMAT_TYPE_8;
            default:
                return PreferenceManager.DATE_FORMAT_TYPE_0;
        }
    }

    public int getColorIn() {
        return mPreferences.getInt(COLOR_INFLOW, Color.BLUE);
    }

    public int getColorOut() {
        return mPreferences.getInt(COLOR_OUTFLOW, Color.RED);
    }

    public Group getGroupType() {
        switch (mPreferences.getInt(GROUP_TYPE, GROUP_MONTHLY)) {
            case GROUP_DAILY:
                return Group.DAILY;
            case GROUP_WEEKLY:
                return Group.WEEKLY;
            case GROUP_MONTHLY:
                return Group.MONTHLY;
            case GROUP_YEARLY:
                return Group.YEARLY;
        }
        return Group.MONTHLY;
    }

    public int getFirstDayOfMonth() {
        return mPreferences.getInt(FIRST_DAY_OF_MONTH, 1);
    }

    public int getFirstDayOfWeek() {
        return mPreferences.getInt(FIRST_DAY_OF_WEEK, Calendar.MONDAY);
    }

    public boolean isReminderEnabled() {
        return mPreferences.getBoolean(REMINDER_STATUS, false);
    }

    public int getReminderHour() {
        return mPreferences.getInt(REMINDER_HOUR, 20);
    }

    public boolean isCurrencySymbolEnabled() {
        return mPreferences.getBoolean(CURRENCY_ENABLED, true);
    }

    public boolean isGroupDigitsEnabled() {
        String pattern = mPreferences.getString(AMOUNT_PATTERN, GROUPED_DIGITS_PATTERN);
        return GROUPED_DIGITS_PATTERN.equals(pattern) || GROUPED_DIGITS_PATTERN_ROUNDED.equals(pattern);
    }

    public boolean isRoundDecimalsEnabled() {
        String pattern = mPreferences.getString(AMOUNT_PATTERN, GROUPED_DIGITS_PATTERN);
        return GROUPED_DIGITS_PATTERN_ROUNDED.equals(pattern) || NOT_GROUPED_DIGITS_PATTERN_ROUNDED.equals(pattern);
    }

    public void destroy() {
        mPreferences.edit().clear().apply();
    }
}