package com.example.myapplication.factoryutils;

public class FactoryInfo {
    private final String itemToCraft;
    private final String fileName;

    public FactoryInfo(String itemToCraft, String fileName) {
        this.itemToCraft = itemToCraft;
        this.fileName = fileName;
    }

    public String getItemToCraft() {
        return itemToCraft;
    }

    public String getFileName() {
        return fileName;
    }
}
