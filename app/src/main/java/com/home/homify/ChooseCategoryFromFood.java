package com.home.homify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.home.homify.databinding.ActivityChooseCategoryFromFoodBinding;
import com.home.homify.databinding.ActivityHomeBinding;

public class ChooseCategoryFromFood extends AppCompatActivity {

    private ActivityChooseCategoryFromFoodBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseCategoryFromFoodBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.ct1Btn.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromFood.this, CategoryFoodActivity.class);
            startActivity(intent);
        });

        binding.ct2Btn.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromFood.this, CategorySweets.class);
            startActivity(intent);
        });

        binding.ct3Btn.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromFood.this, CategoryFruitsVegetables.class);
            startActivity(intent);
        });

        binding.ct4Btn.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromFood.this, CategoryCooking.class);
            startActivity(intent);
        });

        binding.ct5Btn.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromFood.this, CategorySpices.class);
            startActivity(intent);
        });
    }
}
