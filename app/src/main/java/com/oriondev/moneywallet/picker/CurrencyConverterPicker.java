package com.oriondev.moneywallet.picker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.oriondev.moneywallet.model.CurrencyUnit;
import com.oriondev.moneywallet.model.ExchangeRate;
import com.oriondev.moneywallet.ui.fragment.dialog.CurrencyConverterDialog;
import com.oriondev.moneywallet.utils.CurrencyManager;


public class CurrencyConverterPicker extends Fragment implements CurrencyConverterDialog.Callback {

    private static final String SS_CURRENCY_1 = "CurrencyConverterPicker::SavedState::Currency1";
    private static final String SS_CURRENCY_2 = "CurrencyConverterPicker::SavedState::Currency2";
    private static final String SS_RATE = "CurrencyConverterPicker::SavedState::ConversionRate";

    private static final String ARG_DEFAULT_CURRENCY_1 = "CurrencyConverterPicker::Arguments::DefaultCurrency1";
    private static final String ARG_DEFAULT_CURRENCY_2 = "CurrencyConverterPicker::Arguments::DefaultCurrency2";
    private static final String ARG_DEFAULT_RATE = "CurrencyConverterPicker::Arguments::DefaultConversionRate";

    private Controller mController;

    private CurrencyUnit mCurrency1;
    private CurrencyUnit mCurrency2;
    private Double mConversionRate;

    private CurrencyConverterDialog mCurrencyConverterDialog;

    public static CurrencyConverterPicker createPicker(FragmentManager fragmentManager, String tag,
                                                       CurrencyUnit currencyUnit1,
                                                       CurrencyUnit currencyUnit2,
                                                       double defaultRate) {
        CurrencyConverterPicker currencyConverterPicker = (CurrencyConverterPicker) fragmentManager.findFragmentByTag(tag);
        if (currencyConverterPicker == null) {
            currencyConverterPicker = new CurrencyConverterPicker();
            Bundle arguments = new Bundle();
            arguments.putParcelable(ARG_DEFAULT_CURRENCY_1, currencyUnit1);
            arguments.putParcelable(ARG_DEFAULT_CURRENCY_2, currencyUnit2);
            arguments.putDouble(ARG_DEFAULT_RATE, defaultRate);
            currencyConverterPicker.setArguments(arguments);
            fragmentManager.beginTransaction().add(currencyConverterPicker, tag).commit();
        }
        return currencyConverterPicker;
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
            mCurrency1 = savedInstanceState.getParcelable(SS_CURRENCY_1);
            mCurrency2 = savedInstanceState.getParcelable(SS_CURRENCY_2);
            mConversionRate = savedInstanceState.getDouble(SS_RATE);
        } else {
            Bundle arguments = getArguments();
            if (arguments != null) {
                mCurrency1 = arguments.getParcelable(ARG_DEFAULT_CURRENCY_1);
                mCurrency2 = arguments.getParcelable(ARG_DEFAULT_CURRENCY_2);
                mConversionRate = arguments.getDouble(ARG_DEFAULT_RATE);
            } else {
                mCurrency1 = null;
                mCurrency2 = null;
                mConversionRate = null;
            }
        }
        if (mConversionRate != null && mConversionRate == 0d) {
            mConversionRate = null;
        }
        mCurrencyConverterDialog = (CurrencyConverterDialog) getChildFragmentManager().findFragmentByTag(getDialogTag());
        if (mCurrencyConverterDialog == null) {
            mCurrencyConverterDialog = CurrencyConverterDialog.newInstance();
        }
        mCurrencyConverterDialog.setCallback(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadConversionRate(false);
        fireCallbackSafely();
    }

    private void loadConversionRate(boolean isCurrencyChanged) {
        if (mCurrency1 != null && mCurrency2 != null && (mConversionRate == null || isCurrencyChanged)) {
            if (mCurrency1.equals(mCurrency2)) {
                mConversionRate = 1D;
            } else {
                ExchangeRate exchangeRate = CurrencyManager.getExchangeRate(mCurrency1, mCurrency2);
                if (exchangeRate != null) {
                    mConversionRate = exchangeRate.getRate();
                } else {
                    mConversionRate = 0D;
                }
            }
        }
    }

    private void fireCallbackSafely() {
        if (mController != null) {
            mController.onConversionRateChanged(getTag(), mCurrency1, mCurrency2, mConversionRate);
        }
    }

    private String getDialogTag() {
        return getTag() + "::DialogFragment";
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SS_CURRENCY_1, mCurrency1);
        outState.putParcelable(SS_CURRENCY_2, mCurrency2);
        if (mConversionRate != null) {
            outState.putDouble(SS_RATE, mConversionRate);
        }
    }

    public boolean isReady() {
        return mCurrency1 != null && mCurrency2 != null && mConversionRate != null;
    }

    public void setCurrency1(CurrencyUnit currencyUnit) {
        mCurrency1 = currencyUnit;
        loadConversionRate(false);
        fireCallbackSafely();
    }

    public void setCurrency2(CurrencyUnit currencyUnit) {
        boolean isCurrencyChanged = mCurrency2 != null && !mCurrency2.equals(currencyUnit);
        mCurrency2 = currencyUnit;
        loadConversionRate(isCurrencyChanged);
        fireCallbackSafely();
    }

    public void showPicker(long currentMoney) {
        mCurrencyConverterDialog.showPicker(getChildFragmentManager(), getDialogTag(), mCurrency1, mCurrency2, mConversionRate);
    }

    public long convert(long money) {
        double divider1 = Math.pow(10, mCurrency1.getDecimals());
        double divider2 = Math.pow(10, mCurrency2.getDecimals());
        double money1 = money / divider1;
        double money2 = mConversionRate * money1;
        return (long) (money2 * divider2);
    }

    public void notifyMoneyChanged() {
        fireCallbackSafely();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mController = null;
    }

    @Override
    public void onExchangeRateChanged(double exchangeRate) {
        mConversionRate = exchangeRate;
        fireCallbackSafely();
    }

    public interface Controller {

        void onConversionRateChanged(String tag, CurrencyUnit currencyUnit1, CurrencyUnit currencyUnit2, Double conversionRate);
    }
}