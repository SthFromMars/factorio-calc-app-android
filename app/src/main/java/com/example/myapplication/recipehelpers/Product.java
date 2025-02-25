package com.example.myapplication.recipehelpers;

public class Product extends RecipeComponent{
    private final float probability; // A value in range [0, 1]. Item is only given with this probability; otherwise no product is produced.
    private final int ignoredByProductivity; // How much of this product is ignored by productivity.

    private final float extraCountFraction; // Probability that a craft will yield one additional product. Also applies to bonus crafts caused by productivity.

    public Product(ComponentType type, String name, float amount, float probability, int ignoredByProductivity, float extraCountFraction) {
        super(type, name, amount);
        this.probability = probability;
        this.ignoredByProductivity = ignoredByProductivity;
        this.extraCountFraction = extraCountFraction;
    }

    public float getProbability() {
        return probability;
    }

    public int getIgnoredByProductivity() {
        return ignoredByProductivity;
    }

    public float getExtraCountFraction() {
        return extraCountFraction;
    }
}

// TODO: support amount_min/max  (no use in vanilla)