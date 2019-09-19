package com.oriondev.moneywallet.ui.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.model.Category;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.storage.database.DataContentProvider;
import com.oriondev.moneywallet.ui.activity.base.SinglePanelViewPagerActivity;
import com.oriondev.moneywallet.ui.adapter.pager.CategoryViewPagerAdapter;
import com.oriondev.moneywallet.ui.fragment.primary.CategoryListFragment;
import com.oriondev.moneywallet.utils.IconLoader;


public class CategoryPickerActivity extends SinglePanelViewPagerActivity implements CategoryListFragment.Controller {

    public static final String SHOW_SUB_CATEGORIES = "CategoryPickerActivity::Argument::ShowSubCategories";
    public static final String SHOW_SYSTEM_CATEGORIES = "CategoryPickerActivity::Argument::ShowSystemCategories";
    public static final String RESULT_CATEGORY = "CategoryPickerActivity::Result::Category";

    @NonNull
    @Override
    protected PagerAdapter onCreatePagerAdapter(FragmentManager fragmentManager) {
        boolean showSubCategories = true;
        boolean showSystemCategories = false;
        Intent intent = getIntent();
        if (intent != null) {
            showSubCategories = intent.getBooleanExtra(SHOW_SUB_CATEGORIES, true);
            showSystemCategories = intent.getBooleanExtra(SHOW_SYSTEM_CATEGORIES, false);
        }
        return new CategoryViewPagerAdapter(fragmentManager, this, showSubCategories, showSystemCategories);
    }

    @Override
    protected void onCreatePanelView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreatePanelView(inflater, parent, savedInstanceState);
        if (savedInstanceState == null) {
            setViewPagerPosition(1);
        }
    }

    @Override
    protected int getActivityTitleRes() {
        return R.string.title_activity_category_picker;
    }

    @Override
    protected void onFloatingActionButtonClick() {
        Intent intent = new Intent(this, NewEditCategoryActivity.class);
        intent.putExtra(NewEditItemActivity.MODE, NewEditItemActivity.Mode.NEW_ITEM);
        switch (getViewPagerPosition()) {
            case 0:
            case 2:
                intent.putExtra(NewEditCategoryActivity.TYPE, Contract.CategoryType.INCOME);
                break;
            case 1:
                intent.putExtra(NewEditCategoryActivity.TYPE, Contract.CategoryType.EXPENSE);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(long id) {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContentUris.withAppendedId(DataContentProvider.CONTENT_CATEGORIES, id);
        String[] projection = new String[] {
                Contract.Category.ID,
                Contract.Category.NAME,
                Contract.Category.ICON,
                Contract.Category.TYPE
        };
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        if (cursor != null) {
            Intent intent = new Intent();
            Category category = null;
            if (cursor.moveToFirst()) {
                category = new Category(
                        cursor.getLong(cursor.getColumnIndex(Contract.Category.ID)),
                        cursor.getString(cursor.getColumnIndex(Contract.Category.NAME)),
                        IconLoader.parse(cursor.getString(cursor.getColumnIndex(Contract.Category.ICON))),
                        Contract.CategoryType.fromValue(cursor.getInt(cursor.getColumnIndex(Contract.Category.TYPE)))
                );
            }
            cursor.close();
            intent.putExtra(RESULT_CATEGORY, category);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}