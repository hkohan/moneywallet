package com.oriondev.moneywallet.ui.view.text;

import android.support.annotation.NonNull;


public interface Validator {

    @NonNull
    String getErrorMessage();

    boolean isValid(@NonNull CharSequence charSequence);

    boolean autoValidate();
}