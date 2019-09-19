package com.oriondev.moneywallet.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.oriondev.moneywallet.broadcast.LocalAction;
import com.oriondev.moneywallet.model.Attachment;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.storage.database.DataContentProvider;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class AttachmentHandlerIntentService extends IntentService {

    public static final String ATTACHMENT = "AttachmentHandlerIntentService::Parameters::Attachment";
    public static final String ACTION = "AttachmentHandlerIntentService::Parameters::Action";
    public static final String ERROR = "AttachmentHandlerIntentService::Parameters::Error";

    public static final int ACTION_CREATE = 1;
    public static final int ACTION_DELETE = 2;

    public AttachmentHandlerIntentService() {
        super("AttachmentHandlerIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            int action = intent.getIntExtra(ACTION, 0);
            Attachment attachment = intent.getParcelableExtra(ATTACHMENT);
            notifyOperationStarted(attachment, action);
            try {
                switch (action) {
                    case ACTION_CREATE:
                        Uri uri = createAttachment(intent.getData(), attachment);
                        attachment.setId(ContentUris.parseId(uri));
                        break;
                    case ACTION_DELETE:
                        deleteAttachment(attachment);
                        break;
                }
                notifyOperationFinished(attachment, action);
            } catch (IOException e) {
                notifyOperationFailed(attachment, action, e.getMessage());
            }
        }
    }

    private File getAttachmentFile(Attachment attachment) throws IOException {
        File folder = new File(getExternalFilesDir(null), "attachments");
        FileUtils.forceMkdir(folder);
        File file = new File(folder, attachment.getFile());
        if (!file.exists() && !file.createNewFile()) {
            throw new IOException("Failed to create a new file");
        }
        return file;
    }

    private Uri createAttachment(Uri uri, Attachment attachment) throws IOException {
        ContentResolver contentResolver = getContentResolver();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = contentResolver.openInputStream(uri);
            if (inputStream != null) {
                outputStream = new FileOutputStream(getAttachmentFile(attachment));
                IOUtils.copy(inputStream, outputStream);
            }
        } finally {
            if (inputStream != null) {
                try {inputStream.close();} catch (IOException ignore) {}
            }
            if (outputStream != null) {
                try {outputStream.close();} catch (IOException ignore) {}
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Attachment.FILE, attachment.getFile());
        contentValues.put(Contract.Attachment.NAME, attachment.getName());
        contentValues.put(Contract.Attachment.TYPE, attachment.getType());
        contentValues.put(Contract.Attachment.SIZE, attachment.getSize());
        return contentResolver.insert(DataContentProvider.CONTENT_ATTACHMENTS, contentValues);
    }

    private void deleteAttachment(Attachment attachment) throws IOException {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContentUris.withAppendedId(DataContentProvider.CONTENT_ATTACHMENTS, attachment.getId());
        contentResolver.delete(uri, null, null);
        FileUtils.forceDelete(getAttachmentFile(attachment));
    }

    private void notifyOperationStarted(Attachment attachment, int action) {
        Intent intent = new Intent(LocalAction.ACTION_ATTACHMENT_OP_STARTED);
        intent.putExtra(ATTACHMENT, attachment);
        intent.putExtra(ACTION, action);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void notifyOperationFinished(Attachment attachment, int action) {
        Intent intent = new Intent(LocalAction.ACTION_ATTACHMENT_OP_FINISHED);
        intent.putExtra(ATTACHMENT, attachment);
        intent.putExtra(ACTION, action);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void notifyOperationFailed(Attachment attachment, int action, String error) {
        Intent intent = new Intent(LocalAction.ACTION_ATTACHMENT_OP_FAILED);
        intent.putExtra(ATTACHMENT, attachment);
        intent.putExtra(ACTION, action);
        intent.putExtra(ERROR, error);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}