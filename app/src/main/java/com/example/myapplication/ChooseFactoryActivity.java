package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.factoryutils.FactoryUtils;

public class ChooseFactoryActivity extends AppCompatActivity {

    private static final String TAG = "ChooseFactory";
    private ChooseFactoryAdapter factoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_factory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.newFactoryButton).setOnClickListener(v -> {
            startActivity(new Intent(ChooseFactoryActivity.this, ChooseMainProductActivity.class));
        });
        FactoryUtils.readFactoryList(this);
        RecyclerView factoryListView = findViewById(R.id.factoryList);
        factoryListView.setLayoutManager(new LinearLayoutManager(this));
        factoryAdapter = new ChooseFactoryAdapter(FactoryUtils.getFactoryList());
        factoryListView.setAdapter(factoryAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        factoryAdapter.updateFactories();
    }

}