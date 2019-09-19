package com.oriondev.moneywallet.storage.database.backup;

import com.oriondev.moneywallet.api.BackendServiceFactory;
import com.oriondev.moneywallet.model.BackupService;

import java.util.List;


public class BackupManager {

    public static final String BACKUP_EXTENSION_LEGACY = ".mwb";
    public static final String BACKUP_EXTENSION_STANDARD = ".mwbx";
    public static final String BACKUP_EXTENSION_PROTECTED = ".mwbs";

    /*package-local*/ static final class FileStructure {
        /*package-local*/ static final String ENCODING = "UTF-8";
        /*package-local*/ static final String FILE_DATABASE = "database.json";
        /*package-local*/ static final String FOLDER_DATABASES = "databases/";
        /*package-local*/ static final String FOLDER_ATTACHMENTS = "attachments/";
    }

    public static List<BackupService> getBackupServices() {
        return BackendServiceFactory.getBackupServices();
    }

    public static String getExtension(boolean encrypted) {
        return encrypted ? BACKUP_EXTENSION_PROTECTED : BACKUP_EXTENSION_STANDARD;
    }
}