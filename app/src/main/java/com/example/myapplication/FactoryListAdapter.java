package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.recipehelpers.Product;
import com.example.myapplication.recipehelpers.Recipe;
import com.example.myapplication.recipehelpers.RecipeComponent;
import com.example.myapplication.recipehelpers.RecipeListItem;
import com.example.myapplication.recipehelpers.RecipeUtils;
import com.example.myapplication.recipepopuputils.RecipeClickFactoryList;

import java.util.ArrayList;

public class FactoryListAdapter  extends RecyclerView.Adapter<FactoryListAdapter.ViewHolder> {

    private static final String TAG = "FactoryListAdapter";
    private final ArrayList<RecipeListItem> recipes;
    private final Activity activity;
    private final String mainProductName;
    private final EditText amountView;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LayoutInflater layoutInflater;
        private final LinearLayout linearLayout;
        private final Activity activity;
        private final TextView machineStringView;

        public ViewHolder(View view, LayoutInflater layoutInflater, Activity activity) {
            super(view);
            this.layoutInflater = layoutInflater;
            this.linearLayout = view.findViewById(R.id.componentList);
            this.activity = activity;
            machineStringView = view.findViewById(R.id.machineString);
        }

        public void addProduct(Product product) {
            View view = layoutInflater.inflate(R.layout.product_card, linearLayout, false);
            ((TextView) view.findViewById(R.id.productName)).setText(product.getFactoryString());
            linearLayout.addView(view);
        }

        public void addIngredient(RecipeComponent ingredient) {
            View view = layoutInflater.inflate(R.layout.ingredient_card, linearLayout, false);
            TextView ingredientTextView = view.findViewById(R.id.ingredientText);
            ingredientTextView.setText(ingredient.getFactoryString());
            ingredientTextView.setOnClickListener(new OnProductClick(
                    activity,
                    new RecipeClickFactoryList(),
                    ingredient.getName()
            ));
            linearLayout.addView(view);
        }
        public void empty(){
            linearLayout.removeAllViews();
        }
        public void setMachineString(String name){
            machineStringView.setText(name);
        }
    }

    public FactoryListAdapter(Activity activity, ArrayList<RecipeListItem> recipes, String mainProductName, EditText amountView) {
        this.activity = activity;
        this.recipes = recipes;
        this.mainProductName = mainProductName;
        this.amountView = amountView;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(
                layoutInflater.inflate(R.layout.recipe_card, viewGroup, false),
                layoutInflater,
                activity
        );
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        //TODO don't recreate unnecessarily
        viewHolder.empty();
        Recipe recipe = recipes.get(position);
        for(Product product: recipe.getProducts())
            viewHolder.addProduct(product);
        for(RecipeComponent ingredient: recipe.getIngredients())
            viewHolder.addIngredient(ingredient);

        viewHolder.setMachineString(recipes.get(position).getMachineFactoryString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void addRecipe(RecipeListItem recipe) {
        recipes.add(recipe);
        //TODO: don't recalculate all recipes
        RecipeUtils.calculateRecipes(recipes, mainProductName, Float.parseFloat(amountView.getText().toString()));
        notifyItemInserted(getItemCount()-1);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void amountChanged(float amount)
    {
        RecipeUtils.calculateRecipes(recipes, mainProductName, amount);
        notifyDataSetChanged();
    }

}