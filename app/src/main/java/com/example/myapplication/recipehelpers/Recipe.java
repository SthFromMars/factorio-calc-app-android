package com.example.myapplication.recipehelpers;

public class Recipe {
    public String name;
    public boolean enabled;
    public String category;
    public RecipeComponent[] ingredients;
    public RecipeComponent[] products;
    public boolean hidden;
    public float energy;
    public float productivity_bonus;

}

// TODO: support minimum/maximum temperature in ingredients (no use in vanilla)