package com.example.myapplication.popuputils;

import android.app.Activity;
import android.content.Intent;

import com.example.myapplication.FactoryActivity;
import com.example.myapplication.factoryutils.FactoryUtils;
import com.example.myapplication.R;
import com.example.myapplication.recipehelpers.RecipeListItem;
import com.example.myapplication.recipehelpers.RecipeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipeClickCraftablesList implements RecipeClickFunctions{
    public void onRecipeSelection(Activity activity, String recipeName, String itemToCraft) {
        Intent intent = new Intent(activity, FactoryActivity.class);
        String factoryName = recipeName;
        FactoryUtils.addEmptyFactory(activity, factoryName, itemToCraft);
        ArrayList<RecipeListItem> factoryList = new ArrayList<>();
        RecipeListItem recipe = RecipeUtils.getRecipeListItem(recipeName);
        recipe.calculateAmounts(new HashMap<>(Map.of(itemToCraft, (double) -1)));
        factoryList.add(recipe);
        FactoryUtils.writeFactory(activity, recipeName, factoryList);

        intent.putExtra(activity.getResources().getString(R.string.factory_name_extra), factoryName);
        activity.startActivity(intent);
        activity.finish();
    }
}
