package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.recipehelpers.Recipe;
import com.example.myapplication.recipehelpers.RecipeUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class FactoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_factory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String itemToCraft = getIntent().getStringExtra("item_to_craft");
        HashMap<String, Recipe> recipes = RecipeUtils.getRecipes();
        ArrayList<Recipe> factoryList = new ArrayList<>();
        factoryList.add(
                recipes.get(RecipeUtils.getCraftables().get(itemToCraft).get(0))
        );

        RecyclerView recyclerView = findViewById(R.id.factoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FactoryListAdapter factoryListAdapter = new FactoryListAdapter(this, factoryList);
        recyclerView.setAdapter(factoryListAdapter);


    }
}