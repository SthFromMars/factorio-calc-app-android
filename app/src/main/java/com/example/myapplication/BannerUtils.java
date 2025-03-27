package com.example.myapplication;

import android.app.Activity;
import android.widget.TextView;

public class BannerUtils {
    public static void setUpBanner(Activity activity, String title){
        ((TextView) activity.findViewById(R.id.banner_title)).setText(title);
    }
}
