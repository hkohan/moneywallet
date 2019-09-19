package com.oriondev.moneywallet.storage.cache;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


public class TypefaceCache {

    public static final String ROBOTO_MEDIUM = "Roboto-Medium";
    public static final String ROBOTO_REGULAR = "Roboto-Regular";

    private static final Map<String, Typeface> mCache = new HashMap<>();

    public static Typeface get(Context context, String name) {
        synchronized(mCache){
            if(!mCache.containsKey(name)) {
                String path = String.format("fonts/%s.ttf", name);
                Typeface typeface = null;
                try {
                    typeface = Typeface.createFromAsset(context.getAssets(), path);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                if (typeface != null) {
                    mCache.put(name, typeface);
                }
                return typeface;
            }
            return mCache.get(name);
        }
    }

    public static void apply(TextView textView, String name) {
        Typeface typeface = get(textView.getContext(), name);
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
    }
}