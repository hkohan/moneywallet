package com.oriondev.moneywallet.storage.database;

import android.content.ContentResolver;
import android.content.Context;

import com.oriondev.moneywallet.model.Group;
import com.oriondev.moneywallet.storage.database.legacy.LegacyDatabaseImporter;
import com.oriondev.moneywallet.storage.database.legacy.LegacyUserPreferences;
import com.oriondev.moneywallet.storage.preference.PreferenceManager;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class LegacyEditionImporter {

    public static final String DATABASE_NAME = "MoneyWallet";

    private final static String LEGACY_IMAGE_FOLDER = "images";
    private final static String ATTACHMENT_FOLDER = "attachments";
    
    private final Context mContext;
    private final LegacyDatabaseImporter mDatabaseImporter;

    public LegacyEditionImporter(Context context) throws ImportException {
        mContext = context;
        File databaseFile = context.getDatabasePath(DATABASE_NAME);
        mDatabaseImporter = new LegacyDatabaseImporter(databaseFile);
    }

    public void importDatabase() throws ImportException {
        ContentResolver contentResolver = mContext.getContentResolver();
        mDatabaseImporter.importWallets(contentResolver);
        mDatabaseImporter.importCategories(contentResolver);
        mDatabaseImporter.importEvents(contentResolver);
        mDatabaseImporter.importPlaces(contentResolver);
        mDatabaseImporter.importPeople(contentResolver);
        mDatabaseImporter.importEventPeople(contentResolver);
        mDatabaseImporter.importDebts(contentResolver);
        mDatabaseImporter.importDebtPeople(contentResolver);
        mDatabaseImporter.importBudgets(contentResolver);
        mDatabaseImporter.importBudgetWallets(contentResolver);
        mDatabaseImporter.importSavings(contentResolver);
        mDatabaseImporter.importRecurrentTransactions(contentResolver);
        mDatabaseImporter.importRecurrentTransfers(contentResolver);
        mDatabaseImporter.importTransactions(contentResolver);
        mDatabaseImporter.importTransactionPeople(contentResolver);
        mDatabaseImporter.importTransactionModels(contentResolver);
        mDatabaseImporter.importTransfers(contentResolver);
        mDatabaseImporter.importTransferPeople(contentResolver);
        mDatabaseImporter.importTransferModels(contentResolver);
        mDatabaseImporter.importAttachments(contentResolver);
        mDatabaseImporter.importTransactionAttachments(contentResolver);
        mDatabaseImporter.importTransferAttachments(contentResolver);
        // close the database file in order to delete the file on disk
        mDatabaseImporter.close();
        mContext.deleteDatabase(DATABASE_NAME);
    }

    public void importAttachments() throws ImportException {
        File baseFolder = mContext.getExternalFilesDir(null);
        File imageFolder = new File(baseFolder, LEGACY_IMAGE_FOLDER);
        File attachmentFolder = new File(baseFolder, ATTACHMENT_FOLDER);
        if (imageFolder.exists()) {
            // iterate all the files that are placed inside this folder and,
            // for any given name, checks if the database importer has already
            // generated a unique identifier (in this case the file is a valid
            // attachment to copy)
            File[] children = imageFolder.listFiles();
            if (children != null && children.length > 0) {
                // ensure that the attachment folder exists or create it
                if (!attachmentFolder.exists()) {
                    try {
                        FileUtils.forceMkdir(attachmentFolder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // iterate all the files inside the folder
                ContentResolver contentResolver = mContext.getContentResolver();
                for (File child : children) {
                    String uniqueId = mDatabaseImporter.getAttachmentId(child.getName());
                    if (uniqueId != null) {
                        File attachment = new File(attachmentFolder, uniqueId);
                        try {
                            FileUtils.moveFile(child, attachment);
                        } catch (IOException e) {
                            throw new ImportException(e.getMessage());
                        }
                        mDatabaseImporter.importAttachment(contentResolver, uniqueId, attachment.length());
                    }
                }
            }
            // delete the images folder because it is no more necessary
            FileUtils.deleteQuietly(imageFolder);
        }
    }

    public void importPreferences() throws ImportException {
        // obtain all the current legacy values from the user preferences
        LegacyUserPreferences legacyUserPreferences = new LegacyUserPreferences(mContext);
        int dateFormat = legacyUserPreferences.getDateFormat();
        int colorIncomes = legacyUserPreferences.getColorIn();
        int colorExpenses = legacyUserPreferences.getColorOut();
        Group groupType = legacyUserPreferences.getGroupType();
        int firstDayOfMonth = legacyUserPreferences.getFirstDayOfMonth();
        int firstDayOfWeek = legacyUserPreferences.getFirstDayOfWeek();
        boolean reminderEnabled = legacyUserPreferences.isReminderEnabled();
        int reminderHour = legacyUserPreferences.getReminderHour();
        boolean currencyEnabled = legacyUserPreferences.isCurrencySymbolEnabled();
        boolean groupDigitsEnabled = legacyUserPreferences.isGroupDigitsEnabled();
        boolean roundDecimalsEnabled = legacyUserPreferences.isRoundDecimalsEnabled();
        // destroy the legacy shared preferences file to avoid key collisions
        legacyUserPreferences.destroy();
        // update the current PreferenceManager to restore the legacy values
        PreferenceManager.setDateFormat(dateFormat);
        PreferenceManager.setCurrentIncomeColor(colorIncomes);
        PreferenceManager.setCurrentExpenseColor(colorExpenses);
        PreferenceManager.setCurrentGroupType(groupType);
        PreferenceManager.setCurrentFirstDayOfMonth(firstDayOfMonth);
        PreferenceManager.setCurrentFirstDayOfWeek(firstDayOfWeek);
        if (reminderEnabled) {
            PreferenceManager.setCurrentDailyReminder(mContext, reminderHour);
        } else {
            PreferenceManager.setCurrentDailyReminder(mContext, PreferenceManager.DAILY_REMINDER_DISABLED);
        }
        PreferenceManager.setCurrencyEnabled(currencyEnabled);
        PreferenceManager.setGroupDigitsEnabled(groupDigitsEnabled);
        PreferenceManager.setRoundDecimalsEnabled(roundDecimalsEnabled);
        PreferenceManager.setIsFirstStartDone(true);
    }
}