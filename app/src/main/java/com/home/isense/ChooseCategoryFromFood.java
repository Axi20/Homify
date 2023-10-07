package com.home.isense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.home.isense.databinding.ActivityChooseCategoryFromFoodBinding;
import com.home.isense.databinding.ActivityHomeBinding;

public class ChooseCategoryFromFood extends AppCompatActivity {

    private ActivityChooseCategoryFromFoodBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseCategoryFromFoodBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.foodCategory.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromFood.this, CategoryFoodActivity.class);
            startActivity(intent);
        });

        binding.categorySweets.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromFood.this, CategorySweets.class);
            startActivity(intent);
        });

        binding.categoryFruits.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromFood.this, CategoryFruitsVegetables.class);
            startActivity(intent);
        });

        binding.categoryCooking.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromFood.this, CategoryCooking.class);
            startActivity(intent);
        });

        binding.categorySpices.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromFood.this, CategorySpices.class);
            startActivity(intent);
        });

        binding.addNewItemButton.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromFood.this, AddItemActivity.class);
            startActivity(intent);
        });
    }
}
