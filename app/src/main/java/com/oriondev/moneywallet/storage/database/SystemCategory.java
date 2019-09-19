package com.oriondev.moneywallet.storage.database;

import android.content.Context;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.model.ColorIcon;
import com.oriondev.moneywallet.picker.IconPicker;
import com.oriondev.moneywallet.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/*package-local*/ class SystemCategory {

    /*package-local*/ static final List<SystemCategory> mSystemCategories;

    static {
        mSystemCategories = new ArrayList<>();
        mSystemCategories.add(new SystemCategory(R.string.system_category_transfer, Schema.CategoryTag.TRANSFER, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_transfer_tax, Schema.CategoryTag.TRANSFER_TAX, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_debt, Schema.CategoryTag.DEBT, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_credit, Schema.CategoryTag.CREDIT, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_debt_paid, Schema.CategoryTag.PAID_DEBT, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_credit_paid, Schema.CategoryTag.PAID_CREDIT, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_generic_tax, Schema.CategoryTag.TAX, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_saving_in, Schema.CategoryTag.SAVING_DEPOSIT, Utils.getRandomMDColor()));
        mSystemCategories.add(new SystemCategory(R.string.system_category_saving_out, Schema.CategoryTag.SAVING_WITHDRAW, Utils.getRandomMDColor()));
    }

    private final int mName;
    private final String mTag;
    private final String mColor;

    private SystemCategory(int name, String tag, int color) {
        mName = name;
        mTag = tag;
        mColor = Utils.getHexColor(color);
    }

    /*package-local*/ String getName(Context context) {
        return context.getString(mName);
    }

    /*package-local*/ String getTag() {
        return mTag;
    }

    /*package-local*/ String getIcon(Context context) {
        String source = IconPicker.getColorIconString(context.getString(mName));
        return new ColorIcon(mColor, source).toString();
    }

    /*package-local*/ String getUUID() {
        return "system-uuid-" + mTag;
    }
}