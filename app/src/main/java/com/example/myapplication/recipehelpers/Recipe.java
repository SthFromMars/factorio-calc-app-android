package com.example.myapplication.recipehelpers;

import java.util.ArrayList;

public class Recipe {
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
}

// TODO: support minimum/maximum temperature in ingredients (no use in vanilla)