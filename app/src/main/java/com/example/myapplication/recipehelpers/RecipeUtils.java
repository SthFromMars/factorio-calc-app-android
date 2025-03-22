package com.example.myapplication.recipehelpers;


import android.content.res.AssetManager;

import com.example.myapplication.machinehelpers.Machine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.util.stream.Collectors;

public class RecipeUtils {
    private static final String TAG = "RecipeUtils";
    // TODO: don't use static variables for recipes
    private static final HashMap<String, Recipe> recipes = new HashMap<>();
    private static final HashMap<String, ArrayList<String>> craftables = new HashMap<>();
    private static final Gson gson = new Gson();

    public static RecipeListItem getRecipeListItem(String recipeName){
        Recipe recipe = recipes.get(recipeName);
        //TODO do deep copy properly
        return new RecipeListItem(
                recipe.getName(),
                recipe.isEnabled(),
                recipe.getCategory(),
                gson.fromJson(gson.toJson(recipe.getIngredients()), new TypeToken<ArrayList<RecipeComponent>>(){}.getType()),
                gson.fromJson(gson.toJson(recipe.getProducts()), new TypeToken<ArrayList<Product>>(){}.getType()),
                recipe.isHidden(),
                recipe.getEnergy(),
                recipe.getProductivityBonus(),
                recipe.getOrderString()
        );
    }

    public static HashMap<String, ArrayList<String>> getCraftables() {
        return craftables;
    }

    public static void readRecipesFromFile(AssetManager assetManager, String filename) {
        recipes.clear();
        craftables.clear();

        try (InputStream recipeStream = assetManager.open(filename)) {
            String recipesJsonString = new BufferedReader(
                    new InputStreamReader(recipeStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            JSONArray recipesJson = new JSONArray(recipesJsonString);
            for (int i=0; i<recipesJson.length(); i++){
                JSONObject recipeJson = recipesJson.getJSONObject(i);

                Object ingredientsJson = recipeJson.get("ingredients");
                ArrayList<RecipeComponent> ingredients = new ArrayList<>();
                if (ingredientsJson instanceof JSONArray) {
                    JSONArray ingredientsJsonArray = (JSONArray) ingredientsJson;
                    for (int j = 0; j < ingredientsJsonArray.length(); j++) {
                        ingredients.add(parseIngredient(ingredientsJsonArray.getJSONObject(j)));
                    }
                }

                String recipeName = recipeJson.getString("name");
                Object productsJson = recipeJson.get("products");
                ArrayList<Product> products = new ArrayList<>();
                if (productsJson instanceof JSONArray) {
                    JSONArray productsJsonArray = (JSONArray) productsJson;
                    for (int j = 0; j < productsJsonArray.length(); j++) {
                        Product product = parseProduct(productsJsonArray.getJSONObject(j));
                        products.add(product);

                        if (craftables.containsKey(product.getName()))
                            craftables.get(product.getName()).add(recipeName);
                        else {
                            craftables.put(product.getName(), new ArrayList<>());
                            craftables.get(product.getName()).add(recipeName);
                        }
                    }
                }

                Collections.sort(ingredients);
                Collections.sort(products);
                recipes.put(
                        recipeName,
                        new Recipe(
                                recipeJson.getString("name"),
                                recipeJson.getBoolean("enabled"),
                                recipeJson.getString("category"),
                                ingredients,
                                products,
                                recipeJson.getBoolean("hidden"),
                                recipeJson.getDouble("energy"),
                                recipeJson.getDouble("productivity_bonus"),
                                recipeJson.getString("order")
                        ));
            }

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

        for(String craftable: craftables.keySet()){
            // TODO maybe store recipe objects instead of names
            craftables.get(craftable).sort((a, b) ->
                    recipes.get(a).getOrderString().compareToIgnoreCase(recipes.get(b).getOrderString())
            );
        }
    }

    private static RecipeComponent parseIngredient(JSONObject ingredientJson) throws JSONException {
        return new RecipeComponent(
                ingredientJson.getString("type").equals("item") ? ComponentType.ITEM : ComponentType.FLUID,
                ingredientJson.getString("name"),
                ingredientJson.getDouble("amount")
        );
    }

    private static Product parseProduct(JSONObject productJson) throws JSONException {
        if (productJson.getString("type").equals("item"))
            return new ItemProduct(
                    ComponentType.ITEM,
                    productJson.getString("name"),
                    productJson.getDouble("amount"),
                    productJson.getDouble("probability"),
                    productJson.has("ignored_by_productivity") ?
                            productJson.getInt("ignored_by_productivity") : 0,
                    productJson.has("extra_count_fraction") ?
                            productJson.getDouble("extra_count_fraction") : 0,
                    productJson.has("percent_spoiled") ?
                            productJson.getDouble("percent_spoiled") : 0

            );
        else
            return new FluidProduct(
                    ComponentType.FLUID,
                    productJson.getString("name"),
                    productJson.getDouble("amount"),
                    productJson.getDouble("probability"),
                    productJson.has("ignored_by_productivity") ?
                            productJson.getInt("ignored_by_productivity") : 0,
                    productJson.has("extra_count_fraction") ?
                            productJson.getDouble("extra_count_fraction") : 0,
                    productJson.has("temperature") ?
                            productJson.getDouble("temperature") : 0

            );
    }
    public static void calculateRecipes(ArrayList<RecipeListItem> recipes, String mainProduct, double mainAmount){
        HashMap<String, Double> productionAmounts = new HashMap<>();
        productionAmounts.put(mainProduct, mainAmount*(-1));
        for(int i=0; i<recipes.size(); i++){
            //reset to base values
            //TODO: idk about this reset implementation
            String recipeName = recipes.get(i).getName();
            Machine machine = recipes.get(i).getMachine();
            recipes.set(i, getRecipeListItem(recipeName));
            recipes.get(i).setMachine(machine);

            recipes.get(i).calculateAmounts(productionAmounts);
        }
    }
}
