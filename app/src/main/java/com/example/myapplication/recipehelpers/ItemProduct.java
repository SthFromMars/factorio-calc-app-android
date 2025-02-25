package com.example.myapplication.recipehelpers;

public class ItemProduct extends Product{
    private final float percentSpoiled;

    public float getPercentSpoiled() {
        return percentSpoiled;
    }

    public ItemProduct(ComponentType type, String name, float amount, float probability, int ignoredByProductivity, float extraCountFraction, float percentSpoiled) {
        super(type, name, amount, probability, ignoredByProductivity, extraCountFraction);
        this.percentSpoiled = percentSpoiled;
    }
}
