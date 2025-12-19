package com.example.myapplication;

import android.annotation.SuppressLint;
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

    public ChooseFactoryAdapter(List<String> factories) {
        this.factories = factories;
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
        holder.factoryNameView.setText(factories.get(position));
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
