package com.example.myapplication.machinehelpers;

import java.util.ArrayList;

public class Machine implements Comparable<Machine>{
    private final String name;
    private final float craftingSpeed;
    private final ArrayList<String> categories;
    private final String orderString;

    public Machine(String name, float craftingSpeed, ArrayList<String> categories, String orderString) {
        this.name = name;
        this.craftingSpeed = craftingSpeed;
        this.categories = categories;
        this.orderString = orderString;
    }

    public String getName() {
        return name;
    }

    public float getCraftingSpeed() {
        return craftingSpeed;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public String getOrderString() {
        return orderString;
    }

    @Override
    public int compareTo(Machine o) {
        return this.orderString.compareToIgnoreCase(getOrderString());
    }
}
