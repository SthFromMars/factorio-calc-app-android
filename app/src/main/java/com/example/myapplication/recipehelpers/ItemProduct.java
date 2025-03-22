package com.example.myapplication.recipehelpers;

public class ItemProduct extends Product{
    private final double percentSpoiled;

    public double getPercentSpoiled() {
        return percentSpoiled;
    }

    public ItemProduct(ComponentType type, String name, double amount, double probability, int ignoredByProductivity, double extraCountFraction, double percentSpoiled) {
        super(type, name, amount, probability, ignoredByProductivity, extraCountFraction);
        this.percentSpoiled = percentSpoiled;
    }
}
