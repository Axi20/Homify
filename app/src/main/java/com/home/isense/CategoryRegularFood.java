package com.home.isense;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.home.isense.databinding.ActivityRegularFoodBinding;

public class CategoryRegularFood extends AppCompatActivity {
    private ActivityRegularFoodBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegularFoodBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ListView recipeListView = findViewById(R.id.recipeListView);
        DatabaseUtils.loadRecipesByCategory(R.string.category_regular_food, this, recipeListView);
    }
}
