package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.recipepopuputils.RecipeClickCraftablesList;

import java.util.ArrayList;
import java.util.List;

public class CraftablesListAdapter extends RecyclerView.Adapter<CraftablesListAdapter.ViewHolder> {

    private static final String TAG = "CraftablesListAdapter";
    private final List<String> craftablesAll;
    private final List<String> craftablesFiltered;
    private final Activity activity;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final OnProductClick onProductClick;
        public ViewHolder(Activity activity, View view) {
            super(view);
            onProductClick = new OnProductClick(
                    activity,
                    new RecipeClickCraftablesList()
            );
            textView = view.findViewById(R.id.craftableCardText);
            view.setOnClickListener(onProductClick);
        }
        public void setText(String text){
            textView.setText(text);
            onProductClick.setItemToCraft(text);
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public CraftablesListAdapter(Activity activity, List<String> dataSet) {
        this.activity = activity;
        craftablesAll = dataSet;
        craftablesFiltered = new ArrayList<>();
        filter("");
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.craftable_card, viewGroup, false);

        return new ViewHolder(activity, view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.setText(craftablesFiltered.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return craftablesFiltered.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String query) {
        craftablesFiltered.clear();
        if (query.isEmpty()) {
            craftablesFiltered.addAll(craftablesAll);
        } else {
            for (String craftable : craftablesAll) {
                if (craftable.toLowerCase().contains(query.toLowerCase())) {
                    craftablesFiltered.add(craftable);
                }
            }
        }
        notifyDataSetChanged();
    }

}