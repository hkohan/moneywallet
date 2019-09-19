package com.oriondev.moneywallet.background;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class JsonResourceLoader extends AbstractGenericLoader<JSONObject> {

    private final String mResourcePath;

    public JsonResourceLoader(Context context, String resource) {
        super(context);
        mResourcePath = "resources/" + resource;
    }

    @Override
    public JSONObject loadInBackground() {
        StringBuilder jsonBuilder = new StringBuilder();
        try {
            InputStream inputStream = getContext().getAssets().open(mResourcePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            return new JSONObject(jsonBuilder.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}