package com.example.myapplication;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.myapplication.popuputils.PopupUtils;
import com.example.myapplication.recipehelpers.RecipeUtils;

import java.util.concurrent.atomic.AtomicBoolean;

public class BannerUtils {
    private static final String TAG = "BannerUtils";

    public static void setUpBanner(Activity activity, String title){
        ((TextView) activity.findViewById(R.id.banner_title)).setText(title);
        activity.findViewById(R.id.banner_button).setOnClickListener(v -> {
            View popupView = LayoutInflater.from(activity).inflate(R.layout.popup_config, null);
            PopupWindow popupWindow = PopupUtils.getDefaultPopupWindow(popupView);
            popupWindow.showAtLocation(activity.findViewById(R.id.main), Gravity.CENTER, 0, 0);

            SwitchCompat disabledSwitch = popupView.findViewById(R.id.disabled_switch);
            SwitchCompat hiddenSwitch = popupView.findViewById(R.id.hidden_switch);

            disabledSwitch.setChecked(Configuration.isShowDisabled());
            hiddenSwitch.setChecked(Configuration.isShowHidden());
            AtomicBoolean stateChanged = new AtomicBoolean(false);
            hiddenSwitch.setOnCheckedChangeListener((switchView, isChecked) -> {
                Configuration.setShowHidden(isChecked);
                switchView.setChecked(Configuration.isShowHidden());
                stateChanged.set(true);
            });
            disabledSwitch.setOnCheckedChangeListener((switchView, isChecked) -> {
                Configuration.setShowDisabled(isChecked);
                switchView.setChecked(Configuration.isShowDisabled());
                stateChanged.set(true);
            });

            popupWindow.setOnDismissListener(() -> {
                Log.d(TAG, "test");
                if(stateChanged.get())
                    RecipeUtils.makeCraftables();
            });
        });
    }
}
