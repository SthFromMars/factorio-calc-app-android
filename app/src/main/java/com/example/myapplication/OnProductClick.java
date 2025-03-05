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

import com.example.myapplication.recipepopuputils.RecipeClickFunctions;
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
            View popupView = LayoutInflater.from(activity).inflate(R.layout.recipe_choice_popup, null);

            // create the popup window
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            RecyclerView recyclerView = popupView.findViewById(R.id.recipePopupList);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
            PopupListAdapter popupListAdapter = new PopupListAdapter(
                    activity,
                    recipeNamesToCraft,
                    itemToCraft,
                    recipeClickFunctions,
                    popupWindow
            );
            recyclerView.setAdapter(popupListAdapter);

            popupWindow.showAtLocation(activity.findViewById(R.id.main), Gravity.CENTER, 0, 0);
        }
    }
}
