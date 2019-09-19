package com.oriondev.moneywallet.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;


public abstract class Icon implements Parcelable {

    private static final String TYPE = "type";

    public enum Type {
        RESOURCE("resource"),
        COLOR("color");

        private final String mType;

        Type(String type) {
            mType = type;
        }

        @Override
        public String toString() {
            return mType;
        }

        public static Type get(String type) {
            if (type != null) {
                switch (type) {
                    case "resource":
                        return RESOURCE;
                    case "color":
                        return COLOR;
                }
            }
            return null;
        }
    }

    /*package-local*/ Icon() {

    }

    public abstract Type getType();

    protected abstract void writeJSON(JSONObject jsonObject) throws JSONException;

    public abstract Drawable getDrawable(Context context);

    public abstract boolean apply(ImageView imageView);

    public final String toString() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TYPE, getType().toString());
            writeJSON(jsonObject);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Type getType(JSONObject jsonObject) throws JSONException {
        return Type.get(jsonObject.getString(TYPE));
    }

    public static int getDrawableId(Context context, String resource) {
        return context.getResources().getIdentifier(resource, "drawable", context.getPackageName());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}