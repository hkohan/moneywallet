package com.oriondev.moneywallet.model;


public class License {

    private final String mName;
    private final String mAuthor;
    private final String mWebsite;
    private final String mVersion;
    private final String mCopyright;
    private final Type mType;

    public License(String name, String author, String website, String version, String copyright, String type) {
        mName = name;
        mAuthor = author;
        mWebsite = website;
        mVersion = version;
        mCopyright = copyright;
        mType = parse(type);
    }

    public String getName() {
        return mName;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getUrl() {
        return mWebsite;
    }

    public String getVersion() {
        return mVersion;
    }

    public String getCopyright() {
        return mCopyright;
    }

    public Type getType() {
        return mType;
    }

    public String getTypeName() {
        switch (mType) {
            case APACHE:
                return "Apache License";
            case MIT:
                return "MIT License";
            case GPL3:
                return "GNU GPLv3";
            case GPL2:
                return "GNU GPLv2";
            case AGPL:
                return "GNU Affero GPL";
            case CUSTOM:
                return "Custom License";
            default:
                return "Unknown license";
        }
    }

    private static Type parse(String type) {
        if (type != null) {
            switch (type) {
                case "MIT":
                    return Type.MIT;
                case "APACHE":
                    return Type.APACHE;
                case "GPL2":
                    return Type.GPL2;
                case "GPL3":
                    return Type.GPL3;
                case "AGPL":
                    return Type.AGPL;
                case "CUSTOM":
                    return Type.CUSTOM;
            }
        }
        return Type.UNKNOWN;
    }

    public enum Type {
        UNKNOWN,
        CUSTOM,
        APACHE,
        GPL2,
        GPL3,
        AGPL,
        MIT
    }
}