package com.oriondev.moneywallet.model;


public class ChangeLog {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    private int type;
    private String versionName;
    private String versionDate;
    private String changeText;
    private int changeType;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionDate() {
        return versionDate;
    }

    public void setVersionDate(String versionDate) {
        this.versionDate = versionDate;
    }

    public String getChangeText() {
        return changeText;
    }

    public void setChangeText(String changeText) {
        this.changeText = changeText;
    }

    public int getChangeType() {
        return changeType;
    }

    public void setChangeType(int changeType) {
        this.changeType = changeType;
    }

    // BUILDER CLASS

    public static class HeaderBuilder {

        private String versionName;
        private String versionDate;

        public HeaderBuilder versionName(String versionName) {
            this.versionName = versionName;
            return this;
        }

        public HeaderBuilder versionDate(String versionDate) {
            this.versionDate = versionDate;
            return this;
        }

        public ChangeLog build() {
            ChangeLog changeLog = new ChangeLog();
            changeLog.setType(TYPE_HEADER);
            changeLog.setVersionName(versionName);
            changeLog.setVersionDate(versionDate);
            return changeLog;
        }
    }

    public static class ItemBuilder {

        private String changeText;

        public ItemBuilder changeText(String changeText) {
            this.changeText = changeText;
            return this;
        }

        public ChangeLog build() {
            ChangeLog changeLog = new ChangeLog();
            changeLog.setType(TYPE_ITEM);
            changeLog.setChangeText(changeText);
            return changeLog;
        }
    }
}