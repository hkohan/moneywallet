package com.oriondev.moneywallet.ui.view.text;


public enum Mode {

    STANDARD(0), FLOATING_LABEL(1);

    /*package-local*/ int mMode;

    Mode(int mode) {
        mMode = mode;
    }

    public Mode getMode() {
        return getMode(mMode);
    }

    public static Mode getMode(int value) {
        switch (value) {
            case 0:
                return STANDARD;
            case 1:
                return FLOATING_LABEL;
        }
        return null;
    }
}