package com.oriondev.moneywallet.picker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.oriondev.moneywallet.model.Event;
import com.oriondev.moneywallet.ui.fragment.dialog.EventPickerDialog;

import java.util.Date;


public class EventPicker extends Fragment implements EventPickerDialog.Callback {

    private static final String SS_CURRENT_EVENT = "EventPicker::SavedState::CurrentEvent";

    private static final String ARG_DEFAULT_EVENT = "EventPicker::Arguments::DefaultEvent";

    private Controller mController;

    private Event mCurrentEvent;

    private EventPickerDialog mEventPickerDialog;

    public static EventPicker createPicker(FragmentManager fragmentManager, String tag, Event defaultEvent) {
        EventPicker eventPicker = (EventPicker) fragmentManager.findFragmentByTag(tag);
        if (eventPicker == null) {
            eventPicker = new EventPicker();
            Bundle arguments = new Bundle();
            arguments.putParcelable(ARG_DEFAULT_EVENT, defaultEvent);
            eventPicker.setArguments(arguments);
            fragmentManager.beginTransaction().add(eventPicker, tag).commit();
        }
        return eventPicker;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Controller) {
            mController = (Controller) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentEvent = savedInstanceState.getParcelable(SS_CURRENT_EVENT);
        } else {
            Bundle arguments = getArguments();
            if (arguments != null) {
                mCurrentEvent = arguments.getParcelable(ARG_DEFAULT_EVENT);
            }
        }
        mEventPickerDialog = (EventPickerDialog) getChildFragmentManager().findFragmentByTag(getDialogTag());
        if (mEventPickerDialog == null) {
            mEventPickerDialog = EventPickerDialog.newInstance();
        }
        mEventPickerDialog.setCallback(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fireCallbackSafely();
    }

    private void fireCallbackSafely() {
        if (mController != null) {
            mController.onEventChanged(getTag(), mCurrentEvent);
        }
    }

    private String getDialogTag() {
        return getTag() + "::DialogFragment";
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SS_CURRENT_EVENT, mCurrentEvent);
    }

    public boolean isSelected() {
        return mCurrentEvent != null;
    }

    public void setCurrentEvent(Event event) {
        mCurrentEvent = event;
        fireCallbackSafely();
    }

    public Event getCurrentEvent() {
        return mCurrentEvent;
    }

    public void showPicker(Date filterDate) {
        mEventPickerDialog.showPicker(getChildFragmentManager(), getDialogTag(), mCurrentEvent, filterDate);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mController = null;
    }

    @Override
    public void onEventSelected(Event event) {
        mCurrentEvent = event;
        fireCallbackSafely();
    }

    public interface Controller {

        void onEventChanged(String tag, Event event);
    }
}