package com.oriondev.moneywallet.utils;

import android.widget.ImageView;

import com.oriondev.moneywallet.model.ColorIcon;
import com.oriondev.moneywallet.model.Icon;
import com.oriondev.moneywallet.model.VectorIcon;

import org.json.JSONException;
import org.json.JSONObject;



public class IconLoader {

    public static final Icon UNKNOWN = new ColorIcon("#FFC107", "?");

    public static Icon parse(String icon) {
        if (icon != null) {
            try {
                JSONObject object = new JSONObject(icon);
                switch (Icon.getType(object)) {
                    case RESOURCE:
                        return new VectorIcon(object);
                    case COLOR:
                        return new ColorIcon(object);
                }
            } catch (JSONException e) {
                // TODO keep track?
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void parseAndLoad(String encoded, ImageView imageView) {
        loadInto(parse(encoded), imageView);
    }

    public static void parseAndLoad(String encoded, Icon fallback, ImageView imageView) {
        loadInto(parse(encoded), fallback, imageView);
    }

    public static void loadInto(Icon icon, ImageView imageView) {
        loadInto(icon, UNKNOWN, imageView);
    }

    public static void loadInto(Icon icon, Icon fallback, ImageView imageView) {
        if (icon == null || !icon.apply(imageView)) {
            if (fallback != null) {
                fallback.apply(imageView);
            }
        }
    }
}