package com.example.myapplication.recipepopuputils;

import android.content.Context;
import android.widget.Toast;

import com.example.myapplication.FactoryActivity;
import com.example.myapplication.recipehelpers.RecipeUtils;

public class RecipeClickFactoryList implements RecipeClickFunctions{
    @Override
    public void onRecipeSelection(Context context, String recipeName, String itemToCraft) {
        ((FactoryActivity) context).getFactoryListAdapter().addRecipe(
                RecipeUtils.getRecipes().get(recipeName)
        );
    }
}
