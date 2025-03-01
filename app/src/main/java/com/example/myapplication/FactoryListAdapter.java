package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.recipehelpers.Product;
import com.example.myapplication.recipehelpers.Recipe;
import com.example.myapplication.recipehelpers.RecipeComponent;

import java.util.ArrayList;
import java.util.List;

public class FactoryListAdapter  extends RecyclerView.Adapter<FactoryListAdapter.ViewHolder> {

    private static final String TAG = "FactoryListAdapter";
    private List<Recipe> recipes;
    private final Context context;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */

    public static class ViewHolder extends RecyclerView.ViewHolder {
    // private final ArrayList<View> productViews = new ArrayList<>();
    // private final ArrayList<View> ingredientViews = new ArrayList<>();
        private final LayoutInflater layoutInflater;
        private final LinearLayout linearLayout;

        public ViewHolder(View view, LayoutInflater layoutInflater) {
            super(view);
            this.layoutInflater = layoutInflater;
            this.linearLayout = (LinearLayout) view;
        }

        public void addProduct(Product product) {
            View view = layoutInflater.inflate(R.layout.product_card, linearLayout, false);
            ((TextView) view.findViewById(R.id.productName)).setText(product.getFactoryString());
            linearLayout.addView(view);
        }

        public void addIngredient(RecipeComponent ingredient) {
            View view = layoutInflater.inflate(R.layout.ingredient_card, linearLayout, false);
            ((TextView) view.findViewById(R.id.ingredientText)).setText(ingredient.getFactoryString());
            linearLayout.addView(view);
        }
    }

    public FactoryListAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public FactoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(
                layoutInflater.inflate(R.layout.recipe_card, viewGroup, false),
                layoutInflater
        );
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull FactoryListAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

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
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

}