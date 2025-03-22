package com.example.myapplication.machinehelpers;

import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MachineUtils {

    private final static HashMap<String, Machine> machines = new HashMap<>();
    private final static HashMap<String, ArrayList<Machine>> categories = new HashMap<>();
    public static Machine getMachine(String name){
        return machines.get(name);
    }
    public static void readMachinesFromFile(AssetManager assetManager, String filename) {
        machines.clear();

        try (InputStream machineStream = assetManager.open(filename)) {
            String machinesJsonString = new BufferedReader(
                    new InputStreamReader(machineStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            JSONArray machinesJson = new JSONArray(machinesJsonString);

            for(int machineIndex=0; machineIndex < machinesJson.length(); machineIndex++) {
                JSONObject machineJson = machinesJson.getJSONObject(machineIndex);

                String machineName = machineJson.getString("name");
                ArrayList<String> machineCategories = new ArrayList<>();
                double productivity = machineJson.has("productivity") ?
                        machineJson.getDouble("productivity") : 0;
                Machine machine = new Machine(
                        machineName,
                        machineJson.getDouble("crafting_speed"),
                        machineCategories,
                        machineJson.getString("order"),
                        productivity
                );
                machines.put(machineName, machine);

                JSONArray categoriesJson = machineJson.getJSONArray("crafting_categories");
                for(int categoryIndex=0; categoryIndex < categoriesJson.length(); categoryIndex++) {
                    String categoryName = categoriesJson.getString(categoryIndex);
                    machineCategories.add(categoryName);
                    if(categories.containsKey(categoryName))
                        categories.get(categoryName).add(machine);
                    else
                        categories.put(categoryName, new ArrayList<>(List.of(machine)));
                }
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

        categories.keySet().forEach(key -> Collections.sort(Objects.requireNonNull(categories.get(key))));
    }

    //TODO add option to select and change default machines
    public static Machine getMachineForCategory(String category){
        return categories.get(category).get(0);
    }

    public static ArrayList<Machine> getMachinesForCategory(String category){
        return categories.get(category);
    }
}
