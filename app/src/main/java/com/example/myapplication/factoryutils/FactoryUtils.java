package com.example.myapplication.factoryutils;

import android.content.Context;

import com.example.myapplication.R;
import com.example.myapplication.recipehelpers.RecipeListItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class FactoryUtils {

    private static HashMap<String, FactoryInfo> factoryMap; //factoryName, filename
    public static List<String> getFactoryList() {
        return new ArrayList<>(factoryMap.keySet());
    }
    public static void addEmptyFactory(Context context, String name, String itemToCraft){
        factoryMap.put(name, new FactoryInfo(itemToCraft, UUID.randomUUID().toString()));
        writeFactoryList(context);
    }
    private static void writeFactoryList(Context context){
        try (FileOutputStream fileOutputStream = context.openFileOutput(
                context.getResources().getString(R.string.factory_list_filename), Context.MODE_PRIVATE))
        {
            fileOutputStream.write(new Gson().toJson(factoryMap).getBytes());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public static void readFactoryList(Context context) {
        try (FileInputStream fileInputStream = context.openFileInput(
                context.getResources().getString(R.string.factory_list_filename)))
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            Type factoryListType = new TypeToken<HashMap<String, FactoryInfo>>() {}.getType();
            factoryMap = new Gson().fromJson(reader, factoryListType);

        } catch (IOException | NullPointerException e) {
            factoryMap = new HashMap<>();
        }
    }
    public static FactoryInfo getFactoryInfo(String name){
        return factoryMap.get(name);
    }

    public static void writeFactory(Context context, String name, ArrayList<RecipeListItem> factory){
        String filename = factoryMap.get(name).getFileName();
        try (FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE))
        {
            // TODO: check for duplicates
            fileOutputStream.write(new Gson().toJson(factory).getBytes());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<RecipeListItem> readFactory(Context context, String name){
        String filename = factoryMap.get(name).getFileName();
        ArrayList<RecipeListItem> factory;
        try (FileInputStream fileInputStream = context.openFileInput(filename))
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            Type factoryType = new TypeToken<ArrayList<RecipeListItem>>() {}.getType();
            factory = new Gson().fromJson(reader, factoryType);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return factory;
    }
}
