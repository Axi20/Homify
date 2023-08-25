package com.home.homify;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.home.homify.databinding.ActivityRecipesBinding;

public class CategoryRecipesActivity extends AppCompatActivity {
    private ActivityRecipesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.allRecipes.setOnClickListener(v-> {

        });

        binding.addNewRecipe.setOnClickListener(v-> {

        });

        binding.addNewBtn.setOnClickListener(v-> {

        });
    }
}
