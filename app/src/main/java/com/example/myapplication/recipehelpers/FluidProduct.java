package com.example.myapplication.recipehelpers;

public class FluidProduct extends Product{
    private final double temperature;

    public FluidProduct(ComponentType type, String name, double amount, double probability, int ignoredByProductivity, double extraCountFraction, double temperature) {
        super(type, name, amount, probability, ignoredByProductivity, extraCountFraction);
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }
}
