package com.oriondev.moneywallet.storage.database.json;

import android.util.JsonReader;
import android.util.JsonToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/*package-local*/ class JSONDataStreamReader implements Closeable {

    private final JsonReader mReader;

    /*package-local*/ JSONDataStreamReader(InputStream inputStream) throws IOException {
        mReader = new JsonReader(new InputStreamReader(inputStream));
        mReader.beginObject();
    }

    /*package-local*/ String readName() throws IOException {
        return mReader.nextName();
    }

    /*package-local*/ void beginArray() throws IOException {
        mReader.beginArray();
    }

    /*package-local*/ void endArray() throws IOException {
        mReader.endArray();
    }

    /*package-local*/ boolean hasArrayAnotherObject() throws IOException {
        return mReader.peek() != JsonToken.END_ARRAY;
    }

    /*package-local*/ JSONObject readObject() throws IOException, JSONException {
        mReader.beginObject();
        JSONObject object = new JSONObject();
        while (mReader.peek() != JsonToken.END_OBJECT) {
            String name = mReader.nextName();
            switch (mReader.peek()) {
                case STRING:
                    object.put(name, mReader.nextString());
                    break;
                case NUMBER:
                    String value = mReader.nextString();
                    try {object.put(name, Long.parseLong(value));} catch (NumberFormatException ignore) {}
                    try {object.put(name, Double.parseDouble(value));} catch (NumberFormatException ignore) {}
                    break;
                case BOOLEAN:
                    object.put(name, mReader.nextBoolean());
                    break;
            }
        }
        mReader.endObject();
        return object;
    }

    @Override
    public void close() throws IOException {
        mReader.close();
    }
}