package com.oriondev.moneywallet.utils;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ProgressOutputStream extends OutputStream {

    private static final int MIN_NOTIFY_RANGE = 5;

    private final FileOutputStream mOutputStream;
    private final DownloadProgressListener mListener;

    private final long mFileSize;
    private long mProgress;
    private int mLastPercentage;

    public ProgressOutputStream(File file, long size, DownloadProgressListener listener) throws FileNotFoundException {
        mOutputStream = new FileOutputStream(file);
        mListener = listener;
        mFileSize = size;
        mProgress = 0L;
        mLastPercentage = 0;
    }

    @Override
    public void write(int b) throws IOException {
        mOutputStream.write(b);
        mProgress += 1;
        notifyProgress();
    }

    @Override
    public void write(@NonNull byte b[]) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(@NonNull byte b[], int off, int len) throws IOException {
        mOutputStream.write(b, off, len);
        mProgress += len;
        notifyProgress();
    }

    @Override
    public void flush() throws IOException {
        mOutputStream.flush();
    }

    @Override
    public void close() throws IOException {
        mOutputStream.close();
    }

    private void notifyProgress() {
        int currentPercentage = (int) ((mProgress * 100) / mFileSize);
        if (currentPercentage - mLastPercentage > MIN_NOTIFY_RANGE) {
            mLastPercentage = Math.min(currentPercentage, 100);
            mListener.onDownloadProgressUpdate(mLastPercentage);
        }
    }

    public interface DownloadProgressListener {

        void onDownloadProgressUpdate(int percentage);
    }
}