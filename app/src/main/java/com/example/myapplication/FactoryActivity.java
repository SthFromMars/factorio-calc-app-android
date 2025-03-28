package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.recipehelpers.Product;
import com.example.myapplication.recipehelpers.Recipe;
import com.example.myapplication.recipehelpers.RecipeListItem;
import com.example.myapplication.recipehelpers.RecipeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FactoryActivity extends AppCompatActivity {

    private FactoryListAdapter factoryListAdapter;

    public FactoryListAdapter getFactoryListAdapter() {
        return factoryListAdapter;
    }

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
        String recipeName = getIntent().getStringExtra("recipe_name");
        RecipeListItem recipe = RecipeUtils.getRecipeListItem(recipeName);

        double amount = 0;
        for(Product product: recipe.getProducts()){
            if(product.getName().equals(itemToCraft)){
                amount = product.getAmount();
                break;
            }
        }
        recipe.calculateAmounts(new HashMap<>(Map.of(itemToCraft, amount*-1)));

        ((TextView) findViewById(R.id.factoryProductName)).setText(itemToCraft);
        EditText amountView = findViewById(R.id.factoryProductAmount);
        amountView.setText(String.valueOf(amount));

        ArrayList<RecipeListItem> factoryList = new ArrayList<>();
        factoryList.add(recipe);


        RecyclerView recyclerView = findViewById(R.id.factoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        factoryListAdapter = new FactoryListAdapter(this, factoryList, itemToCraft, amountView);
        recyclerView.setAdapter(factoryListAdapter);

        amountView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    factoryListAdapter.amountChanged(Double.parseDouble(s.toString()));
                } catch (NumberFormatException ignored){}
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}