package com.oriondev.moneywallet.ui.view.theme;


public interface ITheme {

    int getColorPrimary();

    int getColorPrimaryDark();

    int getColorAccent();

    ThemeEngine.Mode getMode();

    boolean isDark();

    int getTextColorPrimary();

    int getTextColorSecondary();

    int getTextColorPrimaryInverse();

    int getTextColorSecondaryInverse();

    int getColorCardBackground();

    int getColorWindowForeground();

    int getColorWindowBackground();

    int getColorRipple();

    int getIconColor();

    int getHintTextColor();

    int getErrorColor();

    int getDialogBackgroundColor();

    int getDrawerBackgroundColor();

    int getDrawerIconColor();

    int getDrawerSelectedIconColor();

    int getDrawerTextColor();

    int getDrawerSelectedTextColor();

    int getDrawerSelectedItemColor();

    int getBestColor(int background);

    int getBestTextColor(int background);

    int getBestHintColor(int background);

    int getBestIconColor(int background);
}