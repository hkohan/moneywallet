package com.oriondev.moneywallet.api.disk;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.api.BackendException;
import com.oriondev.moneywallet.api.AbstractBackendServiceDelegate;
import com.oriondev.moneywallet.api.BackendServiceFactory;
import com.oriondev.moneywallet.model.IFile;
import com.oriondev.moneywallet.model.LocalFile;
import com.oriondev.moneywallet.ui.view.theme.ThemedDialog;

public class DiskBackendService extends AbstractBackendServiceDelegate {

    private static final int REQUEST_PERMISSION = 2364;

    public DiskBackendService(BackendServiceStatusListener listener) {
        super(listener);
    }

    @Override
    public String getId() {
        return BackendServiceFactory.SERVICE_ID_EXTERNAL_MEMORY;
    }

    @Override
    public int getName() {
        return R.string.service_backup_external_memory;
    }

    @Override
    public int getBackupCoverMessage() {
        return R.string.cover_message_backup_external_memory_title;
    }

    @Override
    public int getBackupCoverAction() {
        return R.string.cover_message_backup_external_memory_button;
    }

    @Override
    public boolean isServiceEnabled(Context context) {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int result = ContextCompat.checkSelfPermission(context, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void setup(final Activity activity) throws BackendException {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ThemedDialog.buildMaterialDialog(activity)
                    .title(R.string.title_request_permission)
                    .content(R.string.message_permission_required_external_storage)
                    .positiveText(android.R.string.ok)
                    .negativeText(android.R.string.cancel)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {

                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                        }

                    }).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }
    }

    @Override
    public void teardown(Activity activity) throws BackendException {
        // action not supported: cannot revoke storage permission
    }

    @Override
    public boolean handlePermissionsResult(Context context, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == DiskBackendService.REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setBackendServiceEnabled(true);
            } else {
                setBackendServiceEnabled(false);
                ThemedDialog.buildMaterialDialog(context)
                        .title(R.string.title_warning)
                        .content(R.string.message_permission_required_not_granted)
                        .show();
            }
            return true;
        }
        return false;
    }
}