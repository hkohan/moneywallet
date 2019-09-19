package com.oriondev.moneywallet.storage.database.backup;

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;

import com.oriondev.moneywallet.storage.database.DatabaseImporter;
import com.oriondev.moneywallet.storage.database.ImportException;
import com.oriondev.moneywallet.storage.database.json.JSONDatabaseImporter;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;

import java.io.File;
import java.io.IOException;


public class DefaultBackupImporter extends AbstractBackupImporter {

    private final String mPassword;

    public DefaultBackupImporter(Context context, File backupFile, String password) {
        super(context, backupFile);
        mPassword = password;
    }
    
    @Override
    public void importDatabase(@NonNull File temporaryFolder) throws ImportException {
        DatabaseImporter importer = null;
        try {
            ZipFile zipFile = new ZipFile(getBackupFile());
            if (!zipFile.isValidZipFile()) {
                throw new ImportException("Invalid backup file: not a zip file");
            }
            String path = BackupManager.FileStructure.FOLDER_DATABASES + BackupManager.FileStructure.FILE_DATABASE;
            FileHeader header = zipFile.getFileHeader(path);
            if (header == null) {
                throw new ImportException("Invalid backup file: database file not found");
            }
            if (header.isEncrypted()) {
                if (mPassword != null) {
                    header.setPassword(mPassword.toCharArray());
                } else {
                    throw new ImportException("Decryption filed: missing user password");
                }
            }
            notifyImportStarted();
            ZipInputStream inputStream = zipFile.getInputStream(header);
            importer = new JSONDatabaseImporter(inputStream);
            ContentResolver contentResolver = getContentResolver();
            importer.importHeader();
            importer.importCurrencies(contentResolver);
            importer.importWallets(contentResolver);
            importer.importCategories(contentResolver);
            importer.importEvents(contentResolver);
            importer.importPlaces(contentResolver);
            importer.importPeople(contentResolver);
            importer.importEventPeople(contentResolver);
            importer.importDebts(contentResolver);
            importer.importDebtPeople(contentResolver);
            importer.importBudgets(contentResolver);
            importer.importBudgetWallets(contentResolver);
            importer.importSavings(contentResolver);
            importer.importRecurrentTransactions(contentResolver);
            importer.importRecurrentTransfers(contentResolver);
            importer.importTransactions(contentResolver);
            importer.importTransactionPeople(contentResolver);
            importer.importTransactionModels(contentResolver);
            importer.importTransfers(contentResolver);
            importer.importTransferPeople(contentResolver);
            importer.importTransferModels(contentResolver);
            importer.importAttachments(contentResolver);
            importer.importTransactionAttachments(contentResolver);
            importer.importTransferAttachments(contentResolver);
        } catch (ZipException e) {
            throw new ImportException(e.getMessage());
        } finally {
            if (importer != null) {
                importer.close();
            }
        }
    }

    @Override
    public void importAttachmentFiles(@NonNull File attachmentFolder) throws IOException, ImportException {
        try {
            ZipFile zipFile = new ZipFile(getBackupFile());
            if (!zipFile.isValidZipFile()) {
                throw new ImportException("Invalid backup file: not a zip file");
            }
            for (Object obj : zipFile.getFileHeaders()) {
                FileHeader header = (FileHeader) obj;
                if (header.isEncrypted()) {
                    if (mPassword != null) {
                        header.setPassword(mPassword.toCharArray());
                    } else {
                        throw new ImportException("Decryption filed: missing user password");
                    }
                }
                String path = header.getFileName();
                if (path.startsWith(BackupManager.FileStructure.FOLDER_ATTACHMENTS)) {
                    String name = path.substring(BackupManager.FileStructure.FOLDER_ATTACHMENTS.length());
                    if (!name.contains("/")) {
                        zipFile.extractFile(header, attachmentFolder.getPath(), null, name);
                    }
                }
            }
        } catch (ZipException e) {
            throw new ImportException(e.getMessage());
        }
    }
}