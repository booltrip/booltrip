package com.booltrip.booltrip.view;

import android.content.Context;
import android.graphics.Typeface;
import android.provider.SyncStateContract;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView {

    Typeface boldTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Comfortaa-Bold.ttf");
    Typeface normalTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Comfortaa-Regular.ttf");

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        super.setTypeface(normalTypeface/*, -1*/);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setTypeface(normalTypeface/*, -1*/);
    }

    public CustomTextView(Context context) {
        super(context);
        super.setTypeface(normalTypeface/*, -1*/);
    }

    public void setTypeface(Typeface tf, int style) {
        if (style == Typeface.BOLD) {
            super.setTypeface(boldTypeface/*, -1*/);
        } else {
            super.setTypeface(normalTypeface/*, -1*/);
        }
    }
}