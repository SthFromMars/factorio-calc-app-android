package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PopupListAdapter extends RecyclerView.Adapter<PopupListAdapter.ViewHolder> {

    private static final String TAG = "PopupListAdapter";
    private final List<String> recipeNames;
    private final Context context;
    private final String itemToCraft;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final String itemToCraft;

        public ViewHolder(Context context, View view, String itemToCraft) {
            super(view);
            textView = view.findViewById(R.id.popupRecipeName);
            this.itemToCraft = itemToCraft;
            view.setOnClickListener(new MainActivity.OnRecipeClick(context, textView, itemToCraft));
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
    public PopupListAdapter(Context context, List<String> dataSet, String itemToCraft) {
        this.context = context;
        recipeNames = dataSet;
        this.itemToCraft = itemToCraft;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public PopupListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_popup_card, viewGroup, false);

        return new PopupListAdapter.ViewHolder(context, view, itemToCraft);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PopupListAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(recipeNames.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recipeNames.size();
    }
}