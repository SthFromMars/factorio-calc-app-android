package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.recipehelpers.Recipe;
import com.example.myapplication.recipehelpers.RecipeUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        HashMap<String, ArrayList<String>> craftables;

        Pair<HashMap<String, Recipe>, HashMap<String, ArrayList<String>>> recipePair;
        try (InputStream recipeStream = this.getAssets().open("recipes.json")) {
            String recipeJsonString = new BufferedReader(
                    new InputStreamReader(recipeStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            RecipeUtils.parseFromJsonString(recipeJsonString);
            craftables = RecipeUtils.getCraftables();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<String> craftableNames = Arrays.asList(craftables.keySet().toArray(new String[0]));

        RecyclerView recyclerView = findViewById(R.id.craftablesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CraftablesListAdapter craftablesListAdapter = new CraftablesListAdapter(this, craftableNames);
        recyclerView.setAdapter(craftablesListAdapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                craftablesListAdapter.filter(newText);
                return false;
            }
        });
    }

    public static class OnProductClick implements View.OnClickListener {
        private final Activity activity;
        private final TextView textView;
        public OnProductClick(Activity activity, TextView textView){
            super();
            this.activity = activity;
            this.textView = textView;
        }
        @Override
        public void onClick(View v) {
            String itemToCraft = (String) textView.getText();
            ArrayList<String> recipeNamesToCraft = RecipeUtils.getCraftables().get(itemToCraft);
            String recipeName;
            if(recipeNamesToCraft.isEmpty()) {
                Toast.makeText(activity, "No recipes associated with this item.", Toast.LENGTH_SHORT).show();
            }
            else if(recipeNamesToCraft.size() == 1) {
                recipeName = recipeNamesToCraft.get(0);
                Intent intent = new Intent(activity, FactoryActivity.class);
                // TODO: use variable for name of extra
                intent.putExtra("recipe_name", recipeName);
                intent.putExtra("item_to_craft", itemToCraft);
                activity.startActivity(intent);
            }
            else {
                Log.d(TAG, "popup");

                View popupView = LayoutInflater.from(activity).inflate(R.layout.recipe_choice_popup, null);

                // create the popup window
                int width = ViewGroup.LayoutParams.WRAP_CONTENT;
                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                RecyclerView recyclerView = popupView.findViewById(R.id.recipePopupList);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
                PopupListAdapter popupListAdapter = new PopupListAdapter(activity, recipeNamesToCraft, itemToCraft);
                recyclerView.setAdapter(popupListAdapter);

                popupWindow.showAtLocation(activity.findViewById(R.id.main), Gravity.CENTER, 0, 0);
            }
        }
    }

    public static class OnRecipeClick implements View.OnClickListener {
        private final Context context;
        private final TextView textView;
        private final String itemToCraft;

        public OnRecipeClick(Context context, TextView textView, String itemToCraft) {
            this.context = context;
            this.textView = textView;
            this.itemToCraft = itemToCraft;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, FactoryActivity.class);
            intent.putExtra("recipe_name", textView.getText());
            intent.putExtra("item_to_craft", itemToCraft);
            context.startActivity(intent);
        }
    }
}