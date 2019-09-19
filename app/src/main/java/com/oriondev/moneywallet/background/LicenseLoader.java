package com.oriondev.moneywallet.background;

import android.content.Context;

import com.oriondev.moneywallet.model.License;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LicenseLoader extends AbstractGenericLoader<List<License>> {

    public LicenseLoader(Context context) {
        super(context);
    }

    @Override
    public List<License> loadInBackground() {
        List<License> licenses = new ArrayList<>();
        licenses.addAll(loadLicenseFile("libraries_base.json"));
        licenses.addAll(loadLicenseFile("libraries_version.json"));
        licenses.addAll(loadLicenseFile("libraries_map.json"));
        return licenses;
    }

    private List<License> loadLicenseFile(String resource) {
        List<License> licenses = new ArrayList<>();
        StringBuilder jsonBuilder = new StringBuilder();
        try {
            InputStream inputStream = getContext().getAssets().open("resources/" + resource);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            JSONArray array = new JSONArray(jsonBuilder.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                licenses.add(new License(
                        item.getString("name"),
                        item.optString("author"),
                        item.optString("website"),
                        item.optString("version"),
                        item.optString("copyright"),
                        item.optString("license")
                ));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return licenses;
    }
}