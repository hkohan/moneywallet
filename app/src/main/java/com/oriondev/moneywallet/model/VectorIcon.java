package com.oriondev.moneywallet.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import org.json.JSONException;
import org.json.JSONObject;


public class VectorIcon extends Icon {

    private static final String RESOURCE = "resource";

    private final String mResourceName;

    public VectorIcon(JSONObject jsonObject) throws JSONException {
        mResourceName = jsonObject.getString(RESOURCE);
    }

    protected VectorIcon(Parcel source) {
        mResourceName = source.readString();
    }

    @Override
    public Type getType() {
        return Type.RESOURCE;
    }

    @Override
    protected void writeJSON(JSONObject jsonObject) throws JSONException {
        jsonObject.put(RESOURCE, mResourceName);
    }

    @Override
    public Drawable getDrawable(Context context) {
        int drawableId = getDrawableId(context, mResourceName);
        if (drawableId > 0) {
            return ContextCompat.getDrawable(context, drawableId);
        }
        return null;
    }

    @Override
    public boolean apply(ImageView imageView) {
        int resourceId = getResource(imageView.getContext());
        if (resourceId > 0) {
            Glide.with(imageView)
                    .load(resourceId)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(imageView);
            return true;
        } else {
            return false;
        }
    }

    @DrawableRes
    public int getResource(Context context) {
        return getDrawableId(context, mResourceName);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mResourceName);
    }

    public static final Parcelable.Creator<VectorIcon> CREATOR = new Parcelable.Creator<VectorIcon>() {

        @Override
        public VectorIcon createFromParcel(Parcel source) {
            return new VectorIcon(source);
        }

        @Override
        public VectorIcon[] newArray(int size) {
            return new VectorIcon[size];
        }
    };
}