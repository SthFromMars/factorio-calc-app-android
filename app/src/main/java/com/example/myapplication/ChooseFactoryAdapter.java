package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.factoryutils.FactoryUtils;

import java.util.List;

public class ChooseFactoryAdapter extends RecyclerView.Adapter<ChooseFactoryAdapter.ViewHolder> {

    private List<String> factories;
    private final Activity activity;

    public ChooseFactoryAdapter(List<String> factories, Activity activity) {
        this.factories = factories;
        this.activity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView factoryNameView;

        ViewHolder(View itemView) {
            super(itemView);
            factoryNameView = itemView.findViewById(R.id.factoryName);
        }
    }

    @NonNull
    @Override
    public ChooseFactoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.factory_card, parent, false);
        return new ChooseFactoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String factoryName = factories.get(position);
        holder.factoryNameView.setText(factoryName);
        holder.factoryNameView.setOnClickListener((view) -> {
            Intent intent = new Intent(activity, FactoryActivity.class);
            intent.putExtra(activity.getResources().getString(R.string.factory_name_extra), factoryName);
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return factories.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateFactories(){
        factories = FactoryUtils.getFactoryList();
        notifyDataSetChanged();
    }
}
