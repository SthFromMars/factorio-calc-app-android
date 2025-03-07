package com.example.myapplication.machinehelpers;

import java.util.ArrayList;

public class Machine {
    public final String name;
    public final float craftingSpeed;
    public final ArrayList<String> categories;
    public final String orderString;

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
}
