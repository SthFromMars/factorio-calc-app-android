package com.example.myapplication;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.machinehelpers.MachineUtils;
import com.example.myapplication.recipehelpers.RecipeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ChooseMainProductActivity extends AppCompatActivity {

    private static final String TAG = "ChooseMainProductActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_main_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BannerUtils.setUpBanner(this, "Select product to start:");

        HashMap<String, ArrayList<String>> craftables = RecipeUtils.getCraftables();
        List<String> craftableNames = Arrays.asList(craftables.keySet().toArray(new String[0]));
        Collections.sort(craftableNames);

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