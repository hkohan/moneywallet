package com.oriondev.moneywallet.ui.preference;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.afollestad.materialdialogs.MaterialDialog;
import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.view.theme.ThemedDialog;



public class ThemedInputPreference extends Preference {

    private String mContent;

    private String mHint;
    private boolean mAllowEmptyInput;
    private int mInputType;

    private String mValue;

    public ThemedInputPreference(Context context) {
        super(context);
        initialize();
    }

    public ThemedInputPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ThemedInputPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        setLayoutResource(R.layout.layout_preference_material_design);
        setOnPreferenceClickListener(new OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                showDialog();
                return false;
            }

        });
    }

    public void setContent(String content) {
        mContent = content;
    }

    public void setContent(int content) {
        mContent = getContext().getString(content);
    }

    public void setInput(String hint, boolean allowEmptyInput, int inputType) {
        mHint = hint;
        mAllowEmptyInput = allowEmptyInput;
        mInputType = inputType;
    }

    public void setCurrentValue(String value) {
        mValue = value;
    }

    public void setInput(int hint, boolean allowEmptyInput, int inputType) {
        mHint = getContext().getString(hint);
        mAllowEmptyInput = allowEmptyInput;
        mInputType = inputType;
    }

    private void showDialog() {
        ThemedDialog.buildMaterialDialog(getContext())
                .title(getTitle())
                .content(mContent)
                .inputType(mInputType)
                .input(mHint, mValue, mAllowEmptyInput, new MaterialDialog.InputCallback() {

                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (!TextUtils.equals(input, mValue)) {
                            mValue = input.toString();
                            callChangeListener(mValue);
                        }
                    }

                })
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .show();
    }
}