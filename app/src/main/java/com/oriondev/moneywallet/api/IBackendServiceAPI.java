package com.oriondev.moneywallet.api;

import com.oriondev.moneywallet.model.IFile;
import com.oriondev.moneywallet.utils.ProgressInputStream;
import com.oriondev.moneywallet.utils.ProgressOutputStream;

import java.io.File;
import java.util.List;

/**
 * Created by andrea on 22/11/18.
 */
public interface IBackendServiceAPI {

    IFile uploadFile(IFile folder, File file, ProgressInputStream.UploadProgressListener listener) throws BackendException;

    File downloadFile(File folder, IFile file, ProgressOutputStream.DownloadProgressListener listener) throws BackendException;

    List<IFile> getFolderContent(IFile folder) throws BackendException;

    IFile createFolder(IFile parent, String name) throws BackendException;
}