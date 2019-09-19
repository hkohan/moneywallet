package com.oriondev.moneywallet.utils;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProgressInputStream extends InputStream {

    private static final int MIN_NOTIFY_RANGE = 5;

    private final FileInputStream mInputStream;
    private final UploadProgressListener mListener;

    private final long mFileSize;
    private long mProgress;
    private int mLastPercentage;

    public ProgressInputStream(File file, UploadProgressListener listener) throws IOException {
        mInputStream = new FileInputStream(file);
        mListener = listener;
        mFileSize = file.length();
        mProgress = 0L;
        mLastPercentage = 0;
    }

    @Override
    public int read() throws IOException {
        int b = mInputStream.read();
        mProgress += 1;
        notifyProgress();
        return b;
    }

    @Override
    public int read(@NonNull byte b[]) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(@NonNull byte b[], int off, int len) throws IOException {
        int bytes = mInputStream.read(b, off, len);
        mProgress += bytes;
        notifyProgress();
        return bytes;
    }

    @Override
    public long skip(long n) throws IOException {
        long skipped = mInputStream.skip(n);
        mProgress += skipped;
        notifyProgress();
        return skipped;
    }

    @Override
    public int available() throws IOException {
        return mInputStream.available();
    }

    @Override
    public void close() throws IOException {
        mInputStream.close();
    }

    private void notifyProgress() {
        int currentPercentage = (int) ((mProgress * 100) / mFileSize);
        if (currentPercentage - mLastPercentage > MIN_NOTIFY_RANGE) {
            mLastPercentage = Math.min(currentPercentage, 100);
            mListener.onUploadProgressUpdate(mLastPercentage);
        }
    }

    public interface UploadProgressListener {

        void onUploadProgressUpdate(int percentage);
    }
}