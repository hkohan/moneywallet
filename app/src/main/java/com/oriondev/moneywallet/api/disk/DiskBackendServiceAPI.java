package com.oriondev.moneywallet.api.disk;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.oriondev.moneywallet.api.AbstractBackendServiceAPI;
import com.oriondev.moneywallet.api.BackendException;
import com.oriondev.moneywallet.api.IBackendServiceAPI;
import com.oriondev.moneywallet.model.IFile;
import com.oriondev.moneywallet.model.LocalFile;
import com.oriondev.moneywallet.utils.ProgressInputStream;
import com.oriondev.moneywallet.utils.ProgressOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DiskBackendServiceAPI extends AbstractBackendServiceAPI<LocalFile> {

    public DiskBackendServiceAPI() throws BackendException {
        super(LocalFile.class);
    }

    @Override
    public LocalFile upload(LocalFile folder, File file, ProgressInputStream.UploadProgressListener listener) throws BackendException {
        File destination = new File(getFolder(folder), file.getName());
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            if (!destination.exists() && !destination.createNewFile()) {
                throw new IOException("Failed to create the file on disk");
            }
            inputStream = new FileInputStream(file);
            outputStream = new FileOutputStream(destination);
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            throw new BackendException(e.getMessage());
        } finally {
            if (inputStream != null) { try { inputStream.close(); } catch (IOException ignore) {} }
            if (outputStream != null) { try { outputStream.close(); } catch (IOException ignore) {} }
        }
        return new LocalFile(destination);
    }

    @Override
    public File download(File folder, @NonNull LocalFile file, ProgressOutputStream.DownloadProgressListener listener) throws BackendException {
        // file is already on disk, we can avoid to copy it to another place
        return file.getFile();
    }

    @Override
    public List<IFile> list(LocalFile folder) throws BackendException {
        List<IFile> fileList = new ArrayList<>();
        File diskFolder = getFolder(folder);
        if (diskFolder.isDirectory()) {
            File[] files = diskFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    fileList.add(new LocalFile(file));
                }
            }
        }
        return fileList;
    }

    @Override
    public LocalFile newFolder(LocalFile parent, String name) throws BackendException {
        File folder = new File(getFolder(parent), name);
        try {
            FileUtils.forceMkdir(folder);
            return new LocalFile(folder);
        } catch (IOException e) {
            throw new BackendException(e.getMessage());
        }
    }

    private File getFolder(LocalFile folder) {
        if (folder == null) {
            return Environment.getExternalStorageDirectory();
        }
        return folder.getFile();
    }

    public static LocalFile getRootFolder() {
        return new LocalFile(Environment.getExternalStorageDirectory());
    }
}