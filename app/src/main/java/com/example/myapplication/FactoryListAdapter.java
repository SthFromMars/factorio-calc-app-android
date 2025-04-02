package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.popuputils.PopupUtils;
import com.example.myapplication.recipehelpers.Product;
import com.example.myapplication.recipehelpers.RecipeComponent;
import com.example.myapplication.recipehelpers.RecipeListItem;
import com.example.myapplication.recipehelpers.RecipeUtils;
import com.example.myapplication.popuputils.RecipeClickFactoryList;

import java.util.ArrayList;

public class FactoryListAdapter  extends RecyclerView.Adapter<FactoryListAdapter.ViewHolder> {

    private static final String TAG = "FactoryListAdapter";
    private final ArrayList<RecipeListItem> recipes;
    private final Activity activity;
    private final String mainProductName;
    private final EditText amountView;
    private double amount;
    public FactoryListAdapter(Activity activity, ArrayList<RecipeListItem> recipes, String mainProductName, EditText amountView) {
        this.activity = activity;
        this.recipes = recipes;
        this.mainProductName = mainProductName;
        this.amountView = amountView;
        amount = Double.parseDouble(amountView.getText().toString());
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LayoutInflater layoutInflater;
        private final LinearLayout linearLayout;
        private final Activity activity;
        private final TextView machineStringView;
        private RecipeListItem recipe;

        public ViewHolder(View view, LayoutInflater layoutInflater, Activity activity, FactoryListAdapter factoryListAdapter) {
            super(view);
            this.layoutInflater = layoutInflater;
            this.linearLayout = view.findViewById(R.id.componentList);
            this.activity = activity;
            machineStringView = view.findViewById(R.id.machineString);
            machineStringView.setOnClickListener(v -> {
                View popupView = LayoutInflater.from(activity).inflate(R.layout.popup_list, null);
                final PopupWindow popupWindow = PopupUtils.getDefaultPopupWindow(popupView);

                RecyclerView recyclerView = popupView.findViewById(R.id.popupList);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
                MachinePopupListAdapter machinePopupListAdapter = new MachinePopupListAdapter(
                        activity,
                        recipe,
                        popupWindow,
                        this,
                        factoryListAdapter
                );
                recyclerView.setAdapter(machinePopupListAdapter);

                popupWindow.showAtLocation(activity.findViewById(R.id.main), Gravity.CENTER, 0, 0);
            });
        }

        public void setRecipe(RecipeListItem recipe){
            this.recipe = recipe;
            for(Product product: recipe.getProducts())
                addProduct(product);
            for(RecipeComponent ingredient: recipe.getIngredients())
                addIngredient(ingredient);

            setMachineString(recipe.getMachineFactoryString());
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
    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(
                layoutInflater.inflate(R.layout.recipe_card, viewGroup, false),
                layoutInflater,
                activity,
                this
        );
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        //TODO don't recreate unnecessarily
        viewHolder.empty();
        viewHolder.setRecipe(recipes.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void addRecipe(RecipeListItem recipe) {
        recipes.add(recipe);
        //TODO: don't recalculate all recipes
        RecipeUtils.calculateRecipes(recipes, mainProductName, Double.parseDouble(amountView.getText().toString()));
        notifyItemInserted(getItemCount()-1);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void amountChanged(double amount)
    {
        this.amount = amount;
        RecipeUtils.calculateRecipes(recipes, mainProductName, amount);
        notifyDataSetChanged();
    }

    public void recalculate(){
        RecipeUtils.calculateRecipes(recipes, mainProductName, amount);
        notifyDataSetChanged();
    }

}