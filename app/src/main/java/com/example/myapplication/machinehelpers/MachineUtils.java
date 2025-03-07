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
import java.util.HashMap;
import java.util.stream.Collectors;

public class MachineUtils {

    private final static HashMap<String, Machine> machines = new HashMap<>();
    public static HashMap<String, Machine> getMachines(){
        return machines;
    }
    public static void readMachinesFromFile(AssetManager assetManager, String filename) {
        machines.clear();

        JSONArray machinesJson;
        try (InputStream machineStream = assetManager.open(filename)) {
            String machinesJsonString = new BufferedReader(
                    new InputStreamReader(machineStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            machinesJson = new JSONArray(machinesJsonString);
            for(int machineIndex=0; machineIndex < machinesJson.length(); machineIndex++) {
                JSONObject machineJson = machinesJson.getJSONObject(machineIndex);

                ArrayList<String> categories = new ArrayList<>();
                JSONArray categoriesJson = machineJson.getJSONArray("crafting_categories");
                for(int categoryIndex=0; categoryIndex < categoriesJson.length(); categoryIndex++)
                    categories.add(categoriesJson.getString(categoryIndex));

                String machineName = machineJson.getString("name");
                machines.put(
                        machineName, new Machine(
                                machineName,
                                (float) machineJson.getDouble("crafting_speed"),
                                categories,
                                machineJson.getString("order")
                        )
                );
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
