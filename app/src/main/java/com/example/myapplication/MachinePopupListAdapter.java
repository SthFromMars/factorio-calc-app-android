package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.machinehelpers.Machine;
import com.example.myapplication.machinehelpers.MachineUtils;
import com.example.myapplication.recipehelpers.Recipe;
import com.example.myapplication.recipehelpers.RecipeListItem;

import java.util.List;

// TODO: refactor MachinePopupListAdapter and RecipePopupListAdapter to use inheritance
public class MachinePopupListAdapter extends RecyclerView.Adapter<MachinePopupListAdapter.ViewHolder> {

    private static final String TAG = "MachinePopupListAdapter";
    private final List<Machine> machines;
    private final Context context;
    private final RecipeListItem recipe;
    private final PopupWindow popupWindow;
    private final FactoryListAdapter.ViewHolder viewHolder;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private String machineName;
        private final RecipeListItem recipe;
        private final FactoryListAdapter.ViewHolder viewHolder;
        public ViewHolder(
                Context context,
                View view,
                PopupWindow popupWindow,
                RecipeListItem recipe,
                FactoryListAdapter.ViewHolder viewHolder) {
            super(view);
            textView = view.findViewById(R.id.popupListItem);
            this.recipe = recipe;
            this.viewHolder = viewHolder;
            view.setOnClickListener(v -> {
                recipe.setMachine(MachineUtils.getMachine(machineName));
                viewHolder.setMachineString(recipe.getMachineFactoryString());
                popupWindow.dismiss();
            });
        }

        public void setMachineName(String name) {
            machineName = name;
            textView.setText(name);
        }
    }

    public MachinePopupListAdapter(
            Context context,
            RecipeListItem recipe,
            PopupWindow popupWindow, FactoryListAdapter.ViewHolder viewHolder
    ) {
        this.context = context;
        machines = MachineUtils.getMachinesForCategory(recipe.getCategory());
        this.recipe = recipe;
        this.popupWindow = popupWindow;
        this.viewHolder = viewHolder;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public MachinePopupListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.popup_card, viewGroup, false);

        return new MachinePopupListAdapter.ViewHolder(context, view, popupWindow, recipe, viewHolder);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MachinePopupListAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.setMachineName(machines.get(position).getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return machines.size();
    }
}