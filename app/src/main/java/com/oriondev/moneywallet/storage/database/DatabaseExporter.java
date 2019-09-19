package com.oriondev.moneywallet.storage.database;

import android.database.Cursor;



public interface DatabaseExporter {

    void exportHeader() throws ExportException;

    void exportCurrencies(Cursor cursor) throws ExportException;

    void exportWallets(Cursor cursor) throws ExportException;

    void exportCategories(Cursor cursor) throws ExportException;

    void exportEvents(Cursor cursor) throws ExportException;

    void exportPlaces(Cursor cursor) throws ExportException;

    void exportPeople(Cursor cursor) throws ExportException;

    void exportEventPeople(Cursor cursor) throws ExportException;

    void exportDebts(Cursor cursor) throws ExportException;

    void exportDebtPeople(Cursor cursor) throws ExportException;

    void exportBudgets(Cursor cursor) throws ExportException;

    void exportBudgetWallets(Cursor cursor) throws ExportException;

    void exportSavings(Cursor cursor) throws ExportException;

    void exportRecurrentTransactions(Cursor cursor) throws ExportException;

    void exportRecurrentTransfers(Cursor cursor) throws ExportException;

    void exportTransactions(Cursor cursor) throws ExportException;

    void exportTransactionPeople(Cursor cursor) throws ExportException;

    void exportTransactionModels(Cursor cursor) throws ExportException;

    void exportTransfers(Cursor cursor) throws ExportException;

    void exportTransferPeople(Cursor cursor) throws ExportException;

    void exportTransferModels(Cursor cursor) throws ExportException;

    void exportAttachments(Cursor cursor) throws ExportException;

    void exportTransactionAttachments(Cursor cursor) throws ExportException;

    void exportTransferAttachments(Cursor cursor) throws ExportException;

    void close() throws ExportException;
}