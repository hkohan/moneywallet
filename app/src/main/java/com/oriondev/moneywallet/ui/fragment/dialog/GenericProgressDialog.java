package com.oriondev.moneywallet.ui.fragment.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.service.BackupHandlerIntentService;
import com.oriondev.moneywallet.ui.view.theme.ThemedDialog;


public class GenericProgressDialog extends DialogFragment {

    private static final String ARG_TITLE_RES = "GenericProgressDialog::Arguments::TitleRes";
    private static final String ARG_CONTENT_RES = "GenericProgressDialog::Arguments::ContentRes";
    private static final String ARG_INDETERMINATE = "GenericProgressDialog::Arguments::Indeterminate";

    public static GenericProgressDialog newInstance(int title, int content, boolean indeterminate) {
        GenericProgressDialog dialog = new GenericProgressDialog();
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_TITLE_RES, title);
        arguments.putInt(ARG_CONTENT_RES, content);
        arguments.putBoolean(ARG_INDETERMINATE, indeterminate);
        dialog.setArguments(arguments);
        dialog.setCancelable(false);
        return dialog;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        if (activity == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        Bundle arguments = getArguments();
        int titleRes = arguments != null ? arguments.getInt(ARG_TITLE_RES) : 0;
        int contentRes = arguments != null ? arguments.getInt(ARG_CONTENT_RES) : 0;
        boolean indeterminate = arguments != null && arguments.getBoolean(ARG_INDETERMINATE);
        MaterialDialog.Builder builder = ThemedDialog.buildMaterialDialog(activity);
                if (titleRes != 0) {
                    builder.title(titleRes);
                }
                if (contentRes != 0) {
                    builder.content(contentRes);
                }
        return builder.progress(indeterminate, 100)
                .cancelable(false)
                .build();
    }

    public void updateProgress(int contentRes, int progress) {
        MaterialDialog dialog = (MaterialDialog) getDialog();
        if (dialog != null) {
            dialog.setCancelable(false);
            if (contentRes != 0) {
                dialog.setContent(contentRes);
            } else {
                dialog.setContent(null);
            }
            dialog.setProgress(progress);
        }
    }
}