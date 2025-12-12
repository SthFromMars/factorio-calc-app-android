package com.example.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.content.SharedPreferences;
public class Configuration {
    private static final String TAG = "Configuration";
    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;
    private static String showHiddenKey;
    private static String showDisabledKey;
    private static Boolean showHiddenDefault;
    private static Boolean showDisabledDefault;


    public static void setUp(Context context){
        Resources resources = context.getResources();
        sharedPref = context.getSharedPreferences(
                resources.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        );
        editor = sharedPref.edit();
        showHiddenKey = resources.getString(R.string.show_hidden_key);
        showDisabledKey = resources.getString(R.string.show_disabled_key);
        showHiddenDefault = resources.getBoolean(R.bool.show_hidden);
        showDisabledDefault = resources.getBoolean(R.bool.show_disabled);
    }
    public static boolean isShowHidden() {
        return sharedPref.getBoolean(showHiddenKey, showHiddenDefault);
    }
    public static boolean isShowDisabled() {
        return sharedPref.getBoolean(showDisabledKey, showDisabledDefault);
    }
    public static void setShowHidden(boolean showHidden) {
        editor.putBoolean(showHiddenKey, showHidden);
        editor.apply();
    }
    public static void setShowDisabled(boolean showDisabled) {
        editor.putBoolean(showDisabledKey, showDisabled);
        editor.apply();
    }
}