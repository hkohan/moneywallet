package com.oriondev.moneywallet.storage.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.test.mock.MockContext;

import java.io.File;



public class DelegatedMockContext extends MockContext {

    private Context mDelegatedContext;

    public DelegatedMockContext(Context context) {
        mDelegatedContext = context;
    }

    @Override
    public AssetManager getAssets() {
        return mDelegatedContext.getAssets();
    }

    @Override
    public Resources getResources() {
        return mDelegatedContext.getResources();
    }

    @Override
    public File getDir(String name, int mode) {
        // name the directory so the directory will be separated from
        // one created through the regular Context
        return mDelegatedContext.getDir("mock_context_" + name, mode);
    }

    @Override
    public Context getApplicationContext() {
        return DelegatedMockContext.this;
    }
}