package com.example.myapplication.machinehelpers;

import java.util.ArrayList;

public class Machine implements Comparable<Machine>{
    private final String name;
    private final double craftingSpeed;
    private final ArrayList<String> categories;
    private final String orderString;
    private final double productivity;

    public Machine(String name, double craftingSpeed, ArrayList<String> categories, String orderString, double productivity) {
        this.name = name;
        this.craftingSpeed = craftingSpeed;
        this.categories = categories;
        this.orderString = orderString;
        this.productivity = productivity;
    }

    public String getName() {
        return name;
    }

    public double getCraftingSpeed() {
        return craftingSpeed;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public String getOrderString() {
        return orderString;
    }

    public double getProductivity() {
        return productivity;
    }

    @Override
    public int compareTo(Machine o) {
        return this.orderString.compareToIgnoreCase(getOrderString());
    }
}
