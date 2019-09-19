package com.oriondev.moneywallet.storage.database.json;

import android.util.JsonWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;



/*package-local*/ class JSONDataStreamWriter implements Closeable {

    private final JsonWriter mWriter;

    /*package-local*/ JSONDataStreamWriter(OutputStream outputStream) throws IOException {
        mWriter = new JsonWriter(new OutputStreamWriter(outputStream));
        mWriter.beginObject();
    }

    /*package-local*/ void writeName(String name) throws IOException {
        mWriter.name(name);
    }

    /*package-local*/ void beginArray() throws IOException {
        mWriter.beginArray();
    }

    /*package-local*/ void endArray() throws IOException {
        mWriter.endArray();
    }

    /*package-local*/ void writeJSONObject(JSONObject object) throws IOException, JSONException {
        mWriter.beginObject();
        for (Iterator<String> it = object.keys(); it.hasNext(); ) {
            String key = it.next();
            mWriter.name(key);
            Object value = object.get(key);
            if (value instanceof String) {
                mWriter.value((String) value);
            } else if (value instanceof Number) {
                mWriter.value((Number) value);
            } else if (value instanceof Boolean) {
                mWriter.value((Boolean) value);
            } else {
                mWriter.nullValue();
            }
        }
        mWriter.endObject();
    }

    @Override
    public void close() throws IOException {
        mWriter.endObject();
        mWriter.close();
    }
}