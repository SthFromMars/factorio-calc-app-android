package com.example.myapplication.recipepopuputils;

import android.content.Context;

import com.example.myapplication.FactoryActivity;
import com.example.myapplication.recipehelpers.RecipeUtils;

public class RecipeClickFactoryList implements RecipeClickFunctions{
    @Override
    public void onRecipeSelection(Context context, String recipeName, String itemToCraft) {
        //TODO: this just seems plain bad
        ((FactoryActivity) context).getFactoryListAdapter().addRecipe(
                RecipeUtils.getRecipeListItem(recipeName)
        );
    }
}
