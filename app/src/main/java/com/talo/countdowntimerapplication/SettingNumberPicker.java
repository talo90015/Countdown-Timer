package com.talo.countdowntimerapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

public class SettingNumberPicker extends NumberPicker {


    public SettingNumberPicker(Context context) {
        super(context);
    }

    public SettingNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    private void updateView(View view) {
        if (view instanceof EditText){
            ((EditText) view).setTextColor(Color.parseColor("#0d16a0"));
            ((EditText) view).setTextSize(20);
        }
    }
}
