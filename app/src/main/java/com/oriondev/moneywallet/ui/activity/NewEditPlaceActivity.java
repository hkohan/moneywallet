package com.oriondev.moneywallet.ui.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.model.Coordinates;
import com.oriondev.moneywallet.model.Icon;
import com.oriondev.moneywallet.model.Place;
import com.oriondev.moneywallet.picker.IconPicker;
import com.oriondev.moneywallet.picker.MapPlacePicker;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.storage.database.DataContentProvider;
import com.oriondev.moneywallet.ui.view.text.MaterialEditText;
import com.oriondev.moneywallet.ui.view.text.NonEmptyTextValidator;
import com.oriondev.moneywallet.utils.IconLoader;
import com.oriondev.moneywallet.view.MapViewWrapper;


public class NewEditPlaceActivity extends NewEditItemActivity implements IconPicker.Controller, MapViewWrapper.OnMapLoadedCallback, MapPlacePicker.Controller {

    private static final String TAG_ICON_PICKER = "NewEditPlaceActivity::Tag::IconPicker";
    private static final String TAG_PLACE_PICKER = "NewEditPlaceActivity::Tag::PlacePicker";

    private ImageView mIconView;
    private MaterialEditText mNameEditText;
    private MaterialEditText mAddressEditText;
    private CardView mMapCardView;
    private MapViewWrapper mMapView;

    private IconPicker mIconPicker;
    private MapPlacePicker mPlacePicker;

    @Override
    protected void onCreateHeaderView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_header_new_edit_icon_name_item, parent, true);
        mIconView = view.findViewById(R.id.icon_image_view);
        mNameEditText = view.findViewById(R.id.name_edit_text);
        mNameEditText.addValidator(new NonEmptyTextValidator(this, R.string.error_input_name_not_valid));
        // attach a listener to the views
        mIconView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mIconPicker.showPicker();
            }

        });
    }

    @Override
    protected void onCreatePanelView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_panel_new_edit_place, parent, true);
        mAddressEditText = view.findViewById(R.id.address_edit_text);
        mMapCardView = view.findViewById(R.id.map_card_view);
        mMapView = new MapViewWrapper(view.findViewById(R.id.map_view));
        view.findViewById(R.id.place_picker_image_view).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPlacePicker.showPicker();
            }

        });
        view.findViewById(R.id.remove_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPlacePicker.setCurrentPlace(null);
            }

        });
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        Icon icon = null;
        Place place = null;
        if (savedInstanceState == null) {
            if (getMode() == Mode.EDIT_ITEM) {
                ContentResolver contentResolver = getContentResolver();
                Uri uri = ContentUris.withAppendedId(DataContentProvider.CONTENT_PLACES, getItemId());
                String[] projection = new String[] {
                        Contract.Place.ID,
                        Contract.Place.NAME,
                        Contract.Place.ICON,
                        Contract.Place.ADDRESS,
                        Contract.Place.LATITUDE,
                        Contract.Place.LONGITUDE
                };
                Cursor cursor = contentResolver.query(uri, projection, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        long id = cursor.getLong(cursor.getColumnIndex(Contract.Place.ID));
                        String name = cursor.getString(cursor.getColumnIndex(Contract.Place.NAME));
                        icon = IconLoader.parse(cursor.getString(cursor.getColumnIndex(Contract.Place.ICON)));
                        String address = cursor.getString(cursor.getColumnIndex(Contract.Place.ADDRESS));
                        Double latitude = null;
                        Double longitude = null;
                        if (!cursor.isNull(cursor.getColumnIndex(Contract.Place.LATITUDE)) && !cursor.isNull(cursor.getColumnIndex(Contract.Place.LONGITUDE))) {
                            latitude = cursor.getDouble(cursor.getColumnIndex(Contract.Place.LATITUDE));
                            longitude = cursor.getDouble(cursor.getColumnIndex(Contract.Place.LONGITUDE));
                        }
                        place = new Place(id, name, icon, address, latitude, longitude);
                    }
                    cursor.close();
                }
            }
        }
        // now we can create pickers with default values or existing item parameters
        // and update all the views according to the data
        FragmentManager fragmentManager = getSupportFragmentManager();
        mIconPicker = IconPicker.createPicker(fragmentManager, TAG_ICON_PICKER, icon);
        mPlacePicker = MapPlacePicker.createPicker(fragmentManager, TAG_PLACE_PICKER, place);
        // configure pickers
        mIconPicker.listenOn(mNameEditText);
        mMapView.loadMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    protected int getActivityTileRes(Mode mode) {
        switch (mode) {
            case NEW_ITEM:
                return R.string.title_activity_new_place;
            case EDIT_ITEM:
                return R.string.title_activity_edit_place;
            default:
                return -1;
        }
    }

    @Override
    protected void onSaveChanges(Mode mode) {
        if (mNameEditText.validate()) {
            ContentResolver contentResolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Contract.Place.NAME, mNameEditText.getTextAsString());
            contentValues.put(Contract.Place.ICON, mIconPicker.getCurrentIcon().toString());
            contentValues.put(Contract.Place.ADDRESS, mAddressEditText.getTextAsString());
            if (mPlacePicker.isSelected() && mPlacePicker.getCurrentPlace().hasCoordinates()) {
                Coordinates coordinates = mPlacePicker.getCurrentPlace().getCoordinates();
                contentValues.put(Contract.Place.LATITUDE, coordinates.getLatitude());
                contentValues.put(Contract.Place.LONGITUDE, coordinates.getLongitude());
            } else {
                contentValues.putNull(Contract.Place.LATITUDE);
                contentValues.putNull(Contract.Place.LONGITUDE);
            }
            switch (mode) {
                case NEW_ITEM:
                    contentResolver.insert(DataContentProvider.CONTENT_PLACES, contentValues);
                    break;
                case EDIT_ITEM:
                    Uri uri = ContentUris.withAppendedId(DataContentProvider.CONTENT_PLACES, getItemId());
                    contentResolver.update(uri, contentValues, null, null);
                    break;
            }
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onIconChanged(String tag, Icon icon) {
        IconLoader.loadInto(icon, mIconView);
    }

    @Override
    public void onMapReady() {
        mMapView.disableMapInteractions();
        mPlacePicker.fireCallbackSafely();
    }

    @Override
    public void onMapPlaceChanged(String tag, Place place) {
        if (place != null && place.getName() != null) {
            mNameEditText.setText(place.getName());
            mAddressEditText.setText(place.getAddress());
        }
        if (place != null && place.hasCoordinates()) {
            mMapView.addCoordinates(place.getCoordinates());
            mMapCardView.setVisibility(View.VISIBLE);
        } else {
            if (mMapView.isMapReady()) {
                mMapView.clear();
            }
            mMapCardView.setVisibility(View.GONE);
        }
    }
}