package com.oriondev.moneywallet.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.storage.cache.TypefaceCache;


public class CardButton extends android.support.v7.widget.AppCompatButton {

    public CardButton(Context context) {
        super(context);
        initialize(getResources());
    }

    public CardButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(getResources());
    }

    public CardButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(getResources());
    }

    private void initialize(Resources resources) {
        // setTypeface(TypefaceCache.get(getContext(), TypefaceCache.ROBOTO_MEDIUM));
        setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.material_component_lists_card_button_text_size));
        setMinWidth(resources.getDimensionPixelSize(R.dimen.material_component_lists_card_button_min_width));
        int internalPadding = resources.getDimensionPixelSize(R.dimen.material_component_lists_card_button_internal_padding);
        setPadding(
                internalPadding,
                getPaddingTop(),
                internalPadding,
                getPaddingBottom()
        );
    }
}