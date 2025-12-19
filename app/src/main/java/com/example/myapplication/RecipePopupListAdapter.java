package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.popuputils.RecipeClickFunctions;

import java.util.List;

public class RecipePopupListAdapter extends RecyclerView.Adapter<RecipePopupListAdapter.ViewHolder> {

    private static final String TAG = "PopupListAdapter";
    private final List<String> recipeNames;
    private final Activity activity;
    private final String itemToCraft;
    private final RecipeClickFunctions recipeClickFunctions;
    private final PopupWindow popupWindow;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(
                Activity activity,
                View view,
                String itemToCraft,
                RecipeClickFunctions recipeClickFunctions,
                PopupWindow popupWindow) {
            super(view);
            textView = view.findViewById(R.id.popupListItem);
            view.setOnClickListener(v -> {
                        recipeClickFunctions.onRecipeSelection(activity, (String) textView.getText(), itemToCraft);
                        popupWindow.dismiss();
                    }
            );
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
    public RecipePopupListAdapter(
            Activity activity,
            List<String> dataSet,
            String itemToCraft,
            RecipeClickFunctions recipeClickFunctions,
            PopupWindow popupWindow
    ) {
        this.activity = activity;
        recipeNames = dataSet;
        this.itemToCraft = itemToCraft;
        this.recipeClickFunctions = recipeClickFunctions;
        this.popupWindow = popupWindow;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RecipePopupListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.popup_card, viewGroup, false);

        return new RecipePopupListAdapter.ViewHolder(activity, view, itemToCraft, recipeClickFunctions, popupWindow);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecipePopupListAdapter.ViewHolder viewHolder, final int position) {

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