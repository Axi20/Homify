package com.home.isense;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.home.isense.databinding.ActivityRecipeDetailsBinding;

public class RecipeDetailsActivity extends AppCompatActivity {
    private ActivityRecipeDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // Retrieve the recipe details from the intent's extras
        int recipeId = getIntent().getIntExtra("recipeId", -1);
        String recipeName = getIntent().getStringExtra("recipeName");
        String imagePath = getIntent().getStringExtra("imagePath");
        String cookingTime = getIntent().getStringExtra("cookingTime");
        String servings = getIntent().getStringExtra("servings");
        String category = getIntent().getStringExtra("category");
        String ingredients = getIntent().getStringExtra("ingredients");
        String cookingMethod = getIntent().getStringExtra("cookingMethod");

        // Find the layout elements by their IDs
        ImageView recipeImageView = findViewById(R.id.recipe_image);
        TextView recipeNameTextView = findViewById(R.id.recipe_title);
        TextView cookingTimeTextView = findViewById(R.id.cooking_time);
        TextView servingsTextView = findViewById(R.id.servings);
        TextView categoryTextView = findViewById(R.id.category);
        TextView ingredientsTextView = findViewById(R.id.recipe_ingredients);
        TextView cookingMethodTextView = findViewById(R.id.recipe_cooking_method);

        // Populate the layout elements with the retrieved details
        String absoluteImagePath = getFilesDir() + "/" + imagePath;
        Bitmap bitmap = BitmapFactory.decodeFile(absoluteImagePath);
        recipeImageView.setImageBitmap(bitmap);
        recipeNameTextView.setText(recipeName);
        cookingTimeTextView.setText(cookingTime);
        servingsTextView.setText(servings);
        categoryTextView.setText(category);
        ingredientsTextView.setText(ingredients);
        cookingMethodTextView.setText(cookingMethod);
    }
}
