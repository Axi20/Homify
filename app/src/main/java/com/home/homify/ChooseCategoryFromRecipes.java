package com.home.homify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.home.homify.databinding.ActivityChooseCategoryFromRecipesBinding;

public class ChooseCategoryFromRecipes extends AppCompatActivity {

    private ActivityChooseCategoryFromRecipesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseCategoryFromRecipesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.ct1Btn.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromRecipes.this, CategoryMainDishes.class);
            startActivity(intent);
        });

        binding.ct2Btn.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromRecipes.this, CategoryDesserts.class);
            startActivity(intent);
        });

        binding.ct3Btn.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromRecipes.this, CategoryRegularFood.class);
            startActivity(intent);
        });

        binding.ct4Btn.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromRecipes.this, CategoryRoastFood.class);
            startActivity(intent);
        });

        binding.ct5Btn.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromRecipes.this, CategorySoup.class);
            startActivity(intent);
        });

        binding.ct6Btn.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromRecipes.this, CategoryChicken.class);
            startActivity(intent);
        });

        binding.ct7Btn.setOnClickListener(v->{
            Intent intent = new Intent(ChooseCategoryFromRecipes.this, CategoryPork.class);
            startActivity(intent);
        });
    }
}
