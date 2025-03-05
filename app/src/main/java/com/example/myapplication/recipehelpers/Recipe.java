package com.example.myapplication.recipehelpers;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Recipe {
    private static final String TAG = "Recipe";
    private final String name;
    private final boolean enabled;
    private final String category;
    private final ArrayList<RecipeComponent> ingredients;
    private final ArrayList<Product> products;
    private final boolean hidden;
    private final float energy;
    private final float productivityBonus;

    public Recipe(
            String name,
            boolean enabled,
            String category,
            ArrayList<RecipeComponent> ingredients,
            ArrayList<Product> products,
            boolean hidden,
            float energy,
            float productivityBonus
    ) {
        this.name = name;
        this.enabled = enabled;
        this.category = category;
        this.ingredients = ingredients;
        this.products = products;
        this.hidden = hidden;
        this.energy = energy;
        this.productivityBonus = productivityBonus;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<RecipeComponent> getIngredients() {
        return ingredients;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean isHidden() {
        return hidden;
    }

    public float getEnergy() {
        return energy;
    }

    public float getProductivityBonus() {
        return productivityBonus;
    }

    public void calculateAmounts(HashMap<String, Float> productionAmounts){
        ArrayList<Float> ratios = new ArrayList<>();
        for(Product product: products){
            String productName = product.getName();
            ratios.add(
                productionAmounts.containsKey(productName) ?
                    productionAmounts.get(productName)/product.getAmount() : 0
            );
        }
        // query for min, because productionAmounts of ingredients are negative
        float finalRatio = Collections.min(ratios)*-1;
        adjustAmounts(finalRatio);

        for(RecipeComponent product: products)
            productionAmounts.merge(product.getName(), product.getAmount(), Float::sum);
        for(RecipeComponent ingredient: ingredients)
            productionAmounts.merge(ingredient.getName(), (ingredient.getAmount()*-1), Float::sum);
    }

    private void adjustAmounts(float ratio) {
        for (RecipeComponent product: products)
            product.adjustAmount(ratio);
        for (RecipeComponent ingredient: ingredients)
            ingredient.adjustAmount(ratio);
    }
}

// TODO: support minimum/maximum temperature in ingredients (no use in vanilla)