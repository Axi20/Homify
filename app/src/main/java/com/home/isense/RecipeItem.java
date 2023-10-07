package com.home.isense;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.home.isense.databinding.ActivityItemRecipeBinding;
import com.home.isense.databinding.ActivityMainDishesBinding;

public class RecipeItem extends AppCompatActivity {
    private ActivityItemRecipeBinding binding;
    private final String recipeName;
    private final String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemRecipeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
    public RecipeItem(String imagePath, String recipeName) {
        this.imagePath = imagePath;
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getImagePath() {
        return imagePath;
    }
    public void viewRecipe(View v) {

    }

}

