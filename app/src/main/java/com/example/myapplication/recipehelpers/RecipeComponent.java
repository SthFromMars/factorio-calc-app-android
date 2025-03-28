package com.example.myapplication.recipehelpers;

public class RecipeComponent implements Comparable<RecipeComponent>{
    private final ComponentType type;
    private final String name;
    private double amount;

    public RecipeComponent(ComponentType type, String name, double amount) {
        this.type = type;
        this.name = name;
        this.amount = amount;
    }

    public ComponentType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getFactoryString(){
        return name + ": " + amount;
    }

    public void adjustAmount(double ratio) {
        amount = amount * ratio;
    }

    @Override
    public int compareTo(RecipeComponent o) {
        return name.compareToIgnoreCase(o.getName());
    }
}

