package com.example.myapplication.popuputils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.myapplication.R;

public class PopupUtils {
    public static PopupWindow getDefaultPopupWindow(View popupView){
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        return new PopupWindow(popupView, width, height, focusable);
    }
}
