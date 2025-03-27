package com.example.myapplication;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.popuputils.PopupUtils;
import com.example.myapplication.popuputils.RecipeClickFunctions;
import com.example.myapplication.recipehelpers.RecipeUtils;

import java.util.ArrayList;

public class OnProductClick implements View.OnClickListener {
    private static final String TAG = "OnProductClick";
    private final Activity activity;
    private final RecipeClickFunctions recipeClickFunctions;
    private String itemToCraft;
    public OnProductClick(Activity activity, RecipeClickFunctions recipeClickFunctions) {
        this.activity = activity;
        this.recipeClickFunctions = recipeClickFunctions;
    }

    public OnProductClick(Activity activity, RecipeClickFunctions recipeClickFunctions, String itemToCraft) {
        this.activity = activity;
        this.recipeClickFunctions = recipeClickFunctions;
        this.itemToCraft = itemToCraft;
    }

    public void setItemToCraft(String itemToCraft) {
        this.itemToCraft = itemToCraft;
    }

    @Override
    public void onClick(View v) {
        ArrayList<String> recipeNamesToCraft = RecipeUtils.getCraftables().get(itemToCraft);
        String recipeName;
        if (recipeNamesToCraft == null || recipeNamesToCraft.isEmpty()) {
            Toast.makeText(activity, "No recipes associated with this item.", Toast.LENGTH_SHORT).show();
        } else if (recipeNamesToCraft.size() == 1) {
            recipeName = recipeNamesToCraft.get(0);
            recipeClickFunctions.onRecipeSelection(activity, recipeName, itemToCraft);
        } else {
            View popupView = LayoutInflater.from(activity).inflate(R.layout.popup, null);

            final PopupWindow popupWindow = PopupUtils.getDefaultPopupWindow(popupView);

            RecyclerView recyclerView = popupView.findViewById(R.id.popupList);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
            RecipePopupListAdapter recipePopupListAdapter = new RecipePopupListAdapter(
                    activity,
                    recipeNamesToCraft,
                    itemToCraft,
                    recipeClickFunctions,
                    popupWindow
            );
            recyclerView.setAdapter(recipePopupListAdapter);

            popupWindow.showAtLocation(activity.findViewById(R.id.main), Gravity.CENTER, 0, 0);
        }
    }
}
