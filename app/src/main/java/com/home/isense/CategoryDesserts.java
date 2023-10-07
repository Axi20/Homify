package com.home.isense;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.home.isense.databinding.ActivityDessertsBinding;

public class CategoryDesserts extends AppCompatActivity {
    private ActivityDessertsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDessertsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ListView recipeListView = findViewById(R.id.recipeListView);
        DatabaseUtils.loadRecipesByCategory("Desszert", this, recipeListView);
    }
}
