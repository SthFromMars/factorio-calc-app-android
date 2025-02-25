package com.example.myapplication.recipehelpers;

public class RecipeComponent {
    private final ProductType type;
    private final String name;
    private final float amount;

    public RecipeComponent(ProductType type, String name, float amount) {
        this.type = type;
        this.name = name;
        this.amount = amount;
    }

    public ProductType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public float getAmount() {
        return amount;
    }
}

