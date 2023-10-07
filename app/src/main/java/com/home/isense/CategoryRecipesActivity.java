package com.home.isense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.home.isense.databinding.ActivityRecipesBinding;

public class CategoryRecipesActivity extends AppCompatActivity {
    private ActivityRecipesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.allRecipes.setOnClickListener(v-> {
            Intent intent = new Intent(CategoryRecipesActivity.this, ChooseCategoryFromRecipes.class);
            startActivity(intent);
        });

        binding.addNewRecipe.setOnClickListener(v-> {
            Intent intent = new Intent(CategoryRecipesActivity.this, AddRecipesActivity.class);
            startActivity(intent);
        });

        binding.addNewBtn.setOnClickListener(v-> {
            Intent intent = new Intent(CategoryRecipesActivity.this, AddItemActivity.class);
            startActivity(intent);
        });
    }
}
