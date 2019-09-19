package com.oriondev.moneywallet.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.oriondev.moneywallet.utils.IconLoader;
import com.oriondev.moneywallet.utils.MoneyFormatter;


public class WalletAccount extends ProfileDrawerItem {

    private MoneyFormatter mMoneyFormatter = MoneyFormatter.getInstance();

    private long mId;
    private Money mMoney;

    @Override
    public WalletAccount withIdentifier(long identifier) {
        super.withIdentifier(identifier);
        return this;
    }

    @Override
    public WalletAccount withName(String name) {
        super.withName(name);
        return this;
    }

    @Override
    public WalletAccount withEmail(String email) {
        throw new IllegalStateException("Email field is not supported in WalletAccount.");
    }

    @Override
    public StringHolder getEmail() {
        return new StringHolder(mMoneyFormatter.getNotTintedString(mMoney));
    }

    public WalletAccount withIcon(Context context, Icon icon) {
        Icon safeIcon = icon != null ? icon : IconLoader.UNKNOWN;
        if (safeIcon instanceof VectorIcon) {
            super.withIcon(((VectorIcon) safeIcon).getResource(context));
        } else if (safeIcon instanceof ColorIcon) {
            super.withIcon(((ColorIcon) safeIcon).getDrawable());
        }
        return this;
    }

    @Override
    public WalletAccount withIcon(Drawable icon) {
        super.withIcon(icon);
        return this;
    }

    @Override
    public WalletAccount withIcon(@DrawableRes int iconRes) {
        super.withIcon(iconRes);
        return this;
    }

    @Override
    public WalletAccount withIcon(Bitmap iconBitmap) {
        super.withIcon(iconBitmap);
        return this;
    }

    @Override
    public WalletAccount withIcon(IIcon icon) {
        super.withIcon(icon);
        return this;
    }

    @Override
    public WalletAccount withIcon(String url) {
        super.withIcon(url);
        return this;
    }

    @Override
    public WalletAccount withIcon(Uri uri) {
        super.withIcon(uri);
        return this;
    }

    public WalletAccount withId(long id) {
        mId = id;
        return this;
    }

    public long getId() {
        return mId;
    }

    public WalletAccount withMoney(String currency, long money) {
        mMoney = new Money(currency, money);
        return this;
    }

    public WalletAccount withMoney(Money money) {
        mMoney = money;
        return this;
    }
}