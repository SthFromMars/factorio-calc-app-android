package com.example.myapplication.recipehelpers;

import java.util.ArrayList;

public class Recipe {
    private static final String TAG = "Recipe";
    private final String name;
    private final boolean enabled;
    private final String category;
    private final ArrayList<RecipeComponent> ingredients;
    private final ArrayList<Product> products;
    private final boolean hidden;
    private double energy;
    private final double productivityBonus;
    private final String orderString;

    public Recipe(
            String name,
            boolean enabled,
            String category,
            ArrayList<RecipeComponent> ingredients,
            ArrayList<Product> products,
            boolean hidden,
            double energy,
            double productivityBonus,
            String orderString
    ) {
        this.name = name;
        this.enabled = enabled;
        this.category = category;
        this.ingredients = ingredients;
        this.products = products;
        this.hidden = hidden;
        this.energy = energy;
        this.productivityBonus = productivityBonus;
        this.orderString = orderString;
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

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getProductivityBonus() {
        return productivityBonus;
    }

    public String getOrderString() {
        return orderString;
    }
}

// TODO: support minimum/maximum temperature in ingredients (no use in vanilla)