package com.oriondev.moneywallet.storage.database;

import android.content.ContentResolver;



public interface DatabaseImporter {

    void importHeader() throws ImportException;

    void importCurrencies(ContentResolver contentResolver) throws ImportException;

    void importWallets(ContentResolver contentResolver) throws ImportException;

    void importCategories(ContentResolver contentResolver) throws ImportException;

    void importEvents(ContentResolver contentResolver) throws ImportException;

    void importPlaces(ContentResolver contentResolver) throws ImportException;

    void importPeople(ContentResolver contentResolver) throws ImportException;

    void importEventPeople(ContentResolver contentResolver) throws ImportException;

    void importDebts(ContentResolver contentResolver) throws ImportException;

    void importDebtPeople(ContentResolver contentResolver) throws ImportException;

    void importBudgets(ContentResolver contentResolver) throws ImportException;

    void importBudgetWallets(ContentResolver contentResolver) throws ImportException;

    void importSavings(ContentResolver contentResolver) throws ImportException;

    void importRecurrentTransactions(ContentResolver contentResolver) throws ImportException;

    void importRecurrentTransfers(ContentResolver contentResolver) throws ImportException;

    void importTransactions(ContentResolver contentResolver) throws ImportException;

    void importTransactionPeople(ContentResolver contentResolver) throws ImportException;

    void importTransactionModels(ContentResolver contentResolver) throws ImportException;

    void importTransfers(ContentResolver contentResolver) throws ImportException;

    void importTransferPeople(ContentResolver contentResolver) throws ImportException;

    void importTransferModels(ContentResolver contentResolver) throws ImportException;

    void importAttachments(ContentResolver contentResolver) throws ImportException;

    void importTransactionAttachments(ContentResolver contentResolver) throws ImportException;

    void importTransferAttachments(ContentResolver contentResolver) throws ImportException;

    void close() throws ImportException;
}