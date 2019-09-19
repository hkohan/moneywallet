package com.oriondev.moneywallet.api;

import android.content.Context;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.api.disk.DiskBackendService;
import com.oriondev.moneywallet.api.disk.DiskBackendServiceAPI;
import com.oriondev.moneywallet.model.BackupService;
import com.oriondev.moneywallet.model.IFile;
import com.oriondev.moneywallet.model.LocalFile;

import java.util.ArrayList;
import java.util.List;


public class BackendServiceFactory {

    public static final String SERVICE_ID_EXTERNAL_MEMORY = "external_memory";

    public static AbstractBackendServiceDelegate getServiceById(String backendId, AbstractBackendServiceDelegate.BackendServiceStatusListener listener) {
        switch (backendId) {
            case SERVICE_ID_EXTERNAL_MEMORY:
                return new DiskBackendService(listener);
        }
        return null;
    }

    public static IBackendServiceAPI getServiceAPIById(Context context, String backendId) throws BackendException {
        switch (backendId) {
            case SERVICE_ID_EXTERNAL_MEMORY:
                return new DiskBackendServiceAPI();
            default:
                throw new BackendException("Invalid backend");
        }
    }

    public static List<BackupService> getBackupServices() {
        List<BackupService> services = new ArrayList<>();
        services.add(new BackupService(SERVICE_ID_EXTERNAL_MEMORY, R.drawable.ic_sd_24dp, R.string.service_backup_external_memory));
        return services;
    }

    public static IFile getFile(String backendId, String encoded) {
        if (encoded != null) {
            switch (backendId) {
                case SERVICE_ID_EXTERNAL_MEMORY:
                    return new LocalFile(encoded);
            }
        }
        return null;
    }
}