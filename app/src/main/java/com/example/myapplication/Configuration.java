package com.example.myapplication;

import android.util.Log;

public class Configuration {
    private static final String TAG = "Configuration";
    private static boolean showDisabled = true;
    private static boolean showHidden = false;

    public static boolean isShowHidden() {
        return showHidden;
    }

    public static boolean isShowDisabled() {
        return showDisabled;
    }

    public static void setShowDisabled(boolean showDisabled) {
        Configuration.showDisabled = showDisabled;
    }

    public static void setShowHidden(boolean showHidden) {
        Configuration.showHidden = showHidden;
        Log.d(TAG, String.valueOf(showHidden));
    }
}
