package com.oriondev.moneywallet.ui.view.text;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;



public class NonEmptyTextValidator implements Validator {

    private final String mMessage;
    private final boolean mAuto;

    public NonEmptyTextValidator(String message) {
        this(message, false);
    }

    public NonEmptyTextValidator(String message, boolean auto) {
        mMessage = message;
        mAuto = auto;
    }

    public NonEmptyTextValidator(Context context, @StringRes int resId) {
        this(context, resId, false);
    }

    public NonEmptyTextValidator(Context context, @StringRes int resId, boolean auto) {
        mMessage = context.getString(resId);
        mAuto = auto;
    }

    @NonNull
    @Override
    public String getErrorMessage() {
        return mMessage;
    }

    @Override
    public boolean isValid(@NonNull CharSequence charSequence) {
        return !charSequence.toString().trim().isEmpty();
    }

    @Override
    public boolean autoValidate() {
        return mAuto;
    }
}
