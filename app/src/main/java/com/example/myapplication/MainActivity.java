package com.example.myapplication;

import android.os.Bundle;
import android.util.Pair;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.recipehelpers.Recipe;
import com.example.myapplication.recipehelpers.RecipeExtractor;

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


        Pair<HashMap<String, Recipe>, HashMap<String, ArrayList<String>>> recipePair;
        try (InputStream recipeStream = this.getAssets().open("recipes.json")) {
            String recipeJsonString = new BufferedReader(
                    new InputStreamReader(recipeStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            recipePair = RecipeExtractor.parseFromJsonString(recipeJsonString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HashMap<String, ArrayList<String>> craftables = recipePair.second;
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
}