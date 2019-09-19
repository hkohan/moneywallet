package com.oriondev.moneywallet.ui.preference;

import android.content.Context;
import android.support.v7.preference.Preference;
import android.util.AttributeSet;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.ui.view.theme.ThemedDialog;


public class ThemedListPreference extends Preference {

    private String[] mEntries;
    private String[] mEntryValues;

    private int mCurrentValueIndex;

    public ThemedListPreference(Context context) {
        super(context);
        initialize();
    }

    public ThemedListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ThemedListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
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

    public void setEntries(String[] entries) {
        mEntries = entries;
    }

    public void setEntries(int array) {
        mEntries = getContext().getResources().getStringArray(array);
    }

    public void setEntryValues(String[] entryValues) {
        mEntryValues = entryValues;
    }

    public void setValue(int valueIndex) {
        mCurrentValueIndex = valueIndex;
    }

    public String getValue() {
        return mCurrentValueIndex >= 0 ? mEntryValues[mCurrentValueIndex] : null;
    }

    public void setValue(String value) {
        mCurrentValueIndex = indexOf(value);
    }

    private int indexOf(String value) {
        for(int i = 0; i < mEntryValues.length; i++) {
            if (mEntryValues[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    private void showDialog() {
        ThemedDialog.buildMaterialDialog(getContext())
                .title(getTitle())
                .items(mEntries)
                .itemsCallbackSingleChoice(mCurrentValueIndex, new MaterialDialog.ListCallbackSingleChoice() {

                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        if (which != mCurrentValueIndex) {
                            callChangeListener(mEntryValues[which]);
                            mCurrentValueIndex = which;
                        }
                        return false;
                    }

                })
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .show();
    }
}