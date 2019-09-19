package com.oriondev.moneywallet.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oriondev.moneywallet.model.IFile;
import com.oriondev.moneywallet.utils.ProgressInputStream;
import com.oriondev.moneywallet.utils.ProgressOutputStream;

import java.io.File;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractBackendServiceAPI<T extends IFile> implements IBackendServiceAPI {

    private final Class<T> mType;

    public AbstractBackendServiceAPI(Class<T> type) throws BackendException {
        mType = type;
    }

    @Override
    public IFile uploadFile(IFile folder, File file, ProgressInputStream.UploadProgressListener listener) throws BackendException {
        if (folder == null || mType.isInstance(folder)) {
            return upload((T) folder, file, listener);
        } else {
            throw new ClassCastException("Backend cannot upload a file to a folder that is not an instance of " + mType.getName() + ". The provided folder is an instance of: " + folder.getClass().getName());
        }
    }

    protected abstract T upload(@Nullable T folder, File file, ProgressInputStream.UploadProgressListener listener) throws BackendException;

    @Override
    public File downloadFile(File folder, IFile file, ProgressOutputStream.DownloadProgressListener listener) throws BackendException {
        if (mType.isInstance(file)) {
            return download(folder, (T) file, listener);
        } else {
            throw new ClassCastException("Backend cannot download a file that is not an instance of " + mType.getName() + ". The provided file is an instance of: " + folder.getClass().getName());
        }
    }

    protected abstract File download(File folder, @NonNull T file, ProgressOutputStream.DownloadProgressListener listener) throws BackendException;

    @Override
    public List<IFile> getFolderContent(IFile folder) throws BackendException {
        if (folder == null || mType.isInstance(folder)) {
            return list((T) folder);
        } else {
            throw new ClassCastException("Backend cannot list the content of a folder that is not an instance of " + mType.getName() + ". The provided folder is an instance of: " + folder.getClass().getName());
        }
    }

    protected abstract List<IFile> list(@Nullable T folder) throws BackendException;

    @Override
    public IFile createFolder(IFile parent, String name) throws BackendException {
        if (parent == null || mType.isInstance(parent)) {
            return newFolder((T) parent, name);
        } else {
            throw new ClassCastException("Backend cannot create a folder in a folder that is not an instance of " + mType.getName() + ". The provided folder is an instance of: " + parent.getClass().getName());
        }
    }

    protected abstract T newFolder(T parent, String name) throws BackendException;
}