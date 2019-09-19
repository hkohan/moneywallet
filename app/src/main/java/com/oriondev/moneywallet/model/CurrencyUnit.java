package com.oriondev.moneywallet.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import com.oriondev.moneywallet.ui.drawable.TextDrawable;
import com.oriondev.moneywallet.utils.Utils;

import java.util.Locale;


public class CurrencyUnit implements Parcelable {

    private final String mIso;
    private final String mName;
    private final String mSymbol;
    private final int mDecimals;

    public CurrencyUnit(String iso, String name, String symbol, int decimals) {
        mIso = iso;
        mName = name;
        mSymbol = symbol;
        mDecimals = decimals;
    }

    @SuppressWarnings("WeakerAccess")
    protected CurrencyUnit(Parcel in) {
        mIso = in.readString();
        mName = in.readString();
        mSymbol = in.readString();
        mDecimals = in.readInt();
    }

    public static final Creator<CurrencyUnit> CREATOR = new Creator<CurrencyUnit>() {

        @Override
        public CurrencyUnit createFromParcel(Parcel in) {
            return new CurrencyUnit(in);
        }

        @Override
        public CurrencyUnit[] newArray(int size) {
            return new CurrencyUnit[size];
        }

    };

    public String getIso() {
        return mIso;
    }

    public String getName() {
        return mName;
    }

    public String getSymbol() {
        return mSymbol;
    }

    public int getDecimals() {
        return mDecimals;
    }

    public boolean hasDecimals() {
        return mDecimals > 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mIso);
        parcel.writeString(mName);
        parcel.writeString(mSymbol);
        parcel.writeInt(mDecimals);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof CurrencyUnit && (obj == this || mIso.equals(((CurrencyUnit) obj).mIso));
    }

    @DrawableRes
    public static int getCurrencyFlag(Context context, String iso) {
        String resourceName = "ic_flag_" + iso.toLowerCase(Locale.ENGLISH);
        return getIconResourceId(context, resourceName);
    }

    public static Drawable getCurrencyDrawable(String iso) {
        String text = TextUtils.isEmpty(iso) ? "?" : iso.substring(0, 1);
        int color = Utils.getRandomMDColor(iso.hashCode());
        return TextDrawable.builder()
                .beginConfig()
                    .width(60)
                    .height(60)
                    .textColor(Utils.getBestColor(color))
                .endConfig()
                .buildRect(text, color);
    }

    private static int getIconResourceId(Context context, String resource) {
        String packageName = context.getPackageName();
        return context.getResources().getIdentifier(resource, "drawable", packageName);
    }
}