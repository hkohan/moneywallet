package com.oriondev.moneywallet.ui.view.theme;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class ThemedLayoutInflater implements LayoutInflater.Factory2 {

    private final LayoutInflater.Factory2 mBaseFactory;

    public ThemedLayoutInflater(LayoutInflater.Factory2 baseFactory) {
        mBaseFactory = baseFactory;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        if (name.startsWith("com.oriondev.moneywallet.ui.view.theme.Themed")) {
            View view = inflateThemeView(name, context, attrs);
            ThemeEngine.applyTheme(view, false);
            return view;
        } else {
            return mBaseFactory.onCreateView(name, context, attrs);
        }
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        if (name.startsWith("com.oriondev.moneywallet.ui.view.theme.Themed")) {
            View view = inflateThemeView(name, context, attrs);
            ThemeEngine.applyTheme(view, false);
            return view;
        } else {
            return mBaseFactory.onCreateView(parent, name, context, attrs);
        }
    }

    private View inflateThemeView(String name, Context context, AttributeSet attrs) {
        try {
            Class<?> clazz = Class.forName(name);
            Constructor<?> constructor = clazz.getConstructor(Context.class, AttributeSet.class);
            return (View) constructor.newInstance(context, attrs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}