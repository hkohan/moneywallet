package com.oriondev.moneywallet.background;

import android.content.Context;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.model.ChangeLog;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChangeLogLoader extends AbstractGenericLoader<List<ChangeLog>> {

    private static final String VERSION = "version";
    private static final String CHANGE_TEXT = "change";
    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_DATE = "versionDate";

    public ChangeLogLoader(Context context) {
        super(context);
    }

    @Override
    public List<ChangeLog> loadInBackground() {
        List<ChangeLog> changeLogs = new ArrayList<>();
        XmlPullParser parser = getContext().getResources().getXml(R.xml.changelog);
        try {
            int eventType = parser.getEventType();
            String buffer = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equals(VERSION)) {
                            ChangeLog changeLog = new ChangeLog.HeaderBuilder()
                                    .versionName(parser.getAttributeValue(null, VERSION_NAME))
                                    .versionDate(parser.getAttributeValue(null, VERSION_DATE))
                                    .build();
                            changeLogs.add(changeLog);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        buffer = parser.getText();
                        if (buffer != null) {
                            buffer = buffer.replaceAll("\\[", "<").replaceAll("\\]", ">");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equals(CHANGE_TEXT)) {
                            ChangeLog changeLog = new ChangeLog.ItemBuilder()
                                    .changeText(buffer)
                                    .build();
                            changeLogs.add(changeLog);
                            buffer = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            // Log.e("ChangeLogLoader", "Exception while parsing change log xml file", e);
        } catch (IOException e) {
            // Log.e("ChangeLogLoader", "I/O Exception while parsing change log xml file", e);
        }
        return changeLogs;
    }
}