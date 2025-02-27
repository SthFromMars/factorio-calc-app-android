package com.example.myapplication.recipehelpers;

import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeExtractor {
    private static final String TAG = "RecipeExtractor";
    public static Pair<
                HashMap<String, Recipe>,
                HashMap<String, ArrayList<String>>
            > parseFromJsonString(String recipesJsonString) {

        HashMap<String, Recipe> recipes= new HashMap<>();
        HashMap<String, ArrayList<String>> craftables = new HashMap<>();

        JSONObject recipesJson;
        try {
            recipesJson = new JSONObject(recipesJsonString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        final JSONObject finalRecipesJson = recipesJson;
        recipesJson.keys().forEachRemaining(recipeNameKey -> {
            try {
                JSONObject recipeJson = finalRecipesJson.getJSONObject(recipeNameKey);

                Object ingredientsJson = recipeJson.get("ingredients");
                ArrayList<RecipeComponent> ingredients = new ArrayList<>();
                if (ingredientsJson instanceof JSONArray) {
                    JSONArray ingredientsJsonArray = (JSONArray) ingredientsJson;
                    for (int i=0; i<ingredientsJsonArray.length(); i++){
                        ingredients.add(parseIngredient(ingredientsJsonArray.getJSONObject(i)));
                    }
                }

                String recipeName = recipeJson.getString("name");
                Object productsJson = recipeJson.get("products");
                ArrayList<Product> products = new ArrayList<>();
                if (productsJson instanceof JSONArray) {
                    JSONArray productsJsonArray = (JSONArray) productsJson;
                    for (int i = 0; i < productsJsonArray.length(); i++) {
                        Product product = parseProduct(productsJsonArray.getJSONObject(i));
                        products.add(product);

                        if (craftables.containsKey(product.getName()))
                            craftables.get(product.getName()).add(recipeName);
                        else {
                            craftables.put(product.getName(), new ArrayList<>());
                            craftables.get(product.getName()).add(recipeName);
                        }
                    }
                }

                recipes.put(
                        recipeName,
                        new Recipe(
                            recipeJson.getString("name"),
                            recipeJson.getBoolean("enabled"),
                            recipeJson.getString("category"),
                            ingredients,
                            products,
                            recipeJson.getBoolean("hidden"),
                            (float) recipeJson.getDouble("energy"),
                            (float) recipeJson.getDouble("productivity_bonus")
                ));

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        return new Pair<>(recipes, craftables);
    }

    private static RecipeComponent parseIngredient(JSONObject ingredientJson) throws JSONException {
        return new RecipeComponent(
                ingredientJson.getString("type").equals("item") ? ComponentType.ITEM : ComponentType.FLUID,
                ingredientJson.getString("name"),
                (float) ingredientJson.getDouble("amount")
        );
    }

    private static Product parseProduct(JSONObject productJson) throws JSONException {
        if (productJson.getString("type").equals("item"))
            return new ItemProduct(
                    ComponentType.ITEM,
                    productJson.getString("name"),
                    (float) productJson.getDouble("amount"),
                    (float) productJson.getDouble("probability"),
                    productJson.has("ignored_by_productivity") ?
                            productJson.getInt("ignored_by_productivity") : 0,
                    productJson.has("extra_count_fraction") ?
                            (float) productJson.getDouble("extra_count_fraction") : 0,
                    productJson.has("percent_spoiled") ?
                            (float) productJson.getDouble("percent_spoiled") : 0

            );
        else
            return new FluidProduct(
                    ComponentType.FLUID,
                    productJson.getString("name"),
                    (float) productJson.getDouble("amount"),
                    (float) productJson.getDouble("probability"),
                    productJson.has("ignored_by_productivity") ?
                            productJson.getInt("ignored_by_productivity") : 0,
                    productJson.has("extra_count_fraction") ?
                            (float) productJson.getDouble("extra_count_fraction") : 0,
                    productJson.has("temperature") ?
                            (float) productJson.getDouble("temperature") : 0

            );
    }
}
