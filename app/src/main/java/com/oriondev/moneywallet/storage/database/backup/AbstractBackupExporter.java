package com.oriondev.moneywallet.storage.database.backup;

import android.content.ContentResolver;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.oriondev.moneywallet.storage.database.ExportException;
import com.oriondev.moneywallet.storage.database.SQLDatabaseExporter;
import com.oriondev.moneywallet.storage.database.model.Attachment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractBackupExporter {

    private final ContentResolver mContentResolver;
    private final File mBackupFile;

    /*package-local*/ AbstractBackupExporter(ContentResolver contentResolver, File backupFile) {
        mContentResolver = contentResolver;
        mBackupFile = backupFile;
    }

    public abstract void exportDatabase(@NonNull File tempFolder) throws ExportException;

    public void exportAttachments(@NonNull File attachmentFolder) throws ExportException {
        Cursor cursor = SQLDatabaseExporter.getAllAttachments(mContentResolver);
        if (cursor != null) {
            List<File> fileList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Attachment attachment = SQLDatabaseExporter.getAttachment(cursor);
                File file = new File(attachmentFolder, attachment.mFile);
                if (file.exists()) {
                    fileList.add(file);
                }
            }
            cursor.close();
            exportAttachmentFiles(fileList);
        }
    }

    protected abstract void exportAttachmentFiles(@NonNull List<File> fileList) throws ExportException;

    /*package-local*/ File getBackupFile() {
        return mBackupFile;
    }

    protected ContentResolver getContentResolver() {
        return mContentResolver;
    }
}