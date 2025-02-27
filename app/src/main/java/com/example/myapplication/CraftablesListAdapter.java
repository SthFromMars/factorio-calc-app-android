package com.example.myapplication;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CraftablesListAdapter extends RecyclerView.Adapter<CraftablesListAdapter.ViewHolder> {

    private final List<String> craftablesAll;
    private List<String> craftablesFiltered;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = view.findViewById(R.id.craftableCard);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public CraftablesListAdapter(List<String> dataSet) {
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

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(craftablesFiltered.get(position));
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