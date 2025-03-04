package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.recipehelpers.Product;
import com.example.myapplication.recipehelpers.Recipe;
import com.example.myapplication.recipehelpers.RecipeComponent;
import com.example.myapplication.recipepopuputils.RecipeClickFactoryList;

import java.util.List;

public class FactoryListAdapter  extends RecyclerView.Adapter<FactoryListAdapter.ViewHolder> {

    private static final String TAG = "FactoryListAdapter";
    private final List<Recipe> recipes;
    private final Activity activity;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LayoutInflater layoutInflater;
        private final LinearLayout linearLayout;
        private final Activity activity;

        public ViewHolder(View view, LayoutInflater layoutInflater, Activity activity) {
            super(view);
            this.layoutInflater = layoutInflater;
            this.linearLayout = (LinearLayout) view;
            this.activity = activity;
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
    }

    public FactoryListAdapter(Activity activity, List<Recipe> recipes) {
        this.activity = activity;
        this.recipes = recipes;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public FactoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(
                layoutInflater.inflate(R.layout.recipe_card, viewGroup, false),
                layoutInflater,
                activity
        );
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull FactoryListAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.empty();
        Recipe recipe = recipes.get(position);
        for(Product product: recipe.getProducts())
            viewHolder.addProduct(product);
        for(RecipeComponent ingredient: recipe.getIngredients())
            viewHolder.addIngredient(ingredient);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        Toast.makeText(activity, String.valueOf(recipes.size()), Toast.LENGTH_LONG).show();
        notifyDataSetChanged();
    }

}