package com.home.isense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.home.isense.databinding.ActivityFruitsVegetablesBinding;

public class CategoryFruitsVegetables extends AppCompatActivity {
    private ActivityFruitsVegetablesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFruitsVegetablesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        DatabaseUtils.populateTableLayout(this, tableLayout, "food", "Zöldség/gyümölcs");

        binding.addNewItemButton.setOnClickListener(v-> {
            Intent intent = new Intent(CategoryFruitsVegetables.this, AddItemActivity.class);
            startActivity(intent);
        });
    }
}
