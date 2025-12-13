package com.example.myapplication.popuputils;

import android.content.Context;
import android.content.Intent;

import com.example.myapplication.FactoryActivity;
import com.example.myapplication.FactoryUtils;
import com.example.myapplication.recipehelpers.RecipeListItem;
import com.example.myapplication.recipehelpers.RecipeUtils;

import java.util.ArrayList;

public class RecipeClickCraftablesList implements RecipeClickFunctions{
    public void onRecipeSelection(Context context, String recipeName, String itemToCraft) {
        Intent intent = new Intent(context, FactoryActivity.class);
        // TODO: use variable for name of extra
        intent.putExtra("recipe_name", recipeName);
        intent.putExtra("item_to_craft", itemToCraft);

        FactoryUtils.addEmptyFactory(context, recipeName);
        ArrayList<RecipeListItem> factoryList = new ArrayList<>();
        factoryList.add(RecipeUtils.getRecipeListItem(recipeName));
        FactoryUtils.writeFactory(context, recipeName, factoryList);

        context.startActivity(intent);
    }
}
