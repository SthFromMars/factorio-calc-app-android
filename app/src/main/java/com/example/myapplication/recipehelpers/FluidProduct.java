package com.example.myapplication.recipehelpers;

public class FluidProduct extends Product{
    private final float temperature;

    public FluidProduct(ComponentType type, String name, float amount, float probability, int ignoredByProductivity, float extraCountFraction, float temperature) {
        super(type, name, amount, probability, ignoredByProductivity, extraCountFraction);
        this.temperature = temperature;
    }

    public float getTemperature() {
        return temperature;
    }
}
