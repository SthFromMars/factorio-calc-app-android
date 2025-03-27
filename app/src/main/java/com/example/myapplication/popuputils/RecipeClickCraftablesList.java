package com.example.myapplication.popuputils;

import android.content.Context;
import android.content.Intent;

import com.example.myapplication.FactoryActivity;

public class RecipeClickCraftablesList implements RecipeClickFunctions{
    public void onRecipeSelection(Context context, String recipeName, String itemToCraft) {
        Intent intent = new Intent(context, FactoryActivity.class);
        // TODO: use variable for name of extra
        intent.putExtra("recipe_name", recipeName);
        intent.putExtra("item_to_craft", itemToCraft);

        context.startActivity(intent);
    }
}
