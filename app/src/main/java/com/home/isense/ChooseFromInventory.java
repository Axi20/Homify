package com.home.isense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.home.isense.databinding.ActivityChooseFromInventoryBinding;

public class ChooseFromInventory extends AppCompatActivity {

    private ActivityChooseFromInventoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseFromInventoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.foodCategory.setOnClickListener(v->{
            Intent intent = new Intent(ChooseFromInventory.this, ChooseCategoryFromFood.class);
            startActivity(intent);
        });

        binding.categoryHousehold.setOnClickListener(v->{
            Intent intent = new Intent(ChooseFromInventory.this, CategoryHomeActivity.class);
            startActivity(intent);
        });

        binding.categoryCleaning.setOnClickListener(v->{
            Intent intent = new Intent(ChooseFromInventory.this, CategoryCleaningActivity.class);
            startActivity(intent);
        });

        binding.categoryHygiene.setOnClickListener(v->{
            Intent intent = new Intent(ChooseFromInventory.this, CategoryHygieneActivity.class);
            startActivity(intent);
        });

        binding.addNewItemButton.setOnClickListener(v->{
            Intent intent = new Intent(ChooseFromInventory.this, AddItemActivity.class);
            startActivity(intent);
        });
    }
}
