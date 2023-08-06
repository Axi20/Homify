package com.home.homify;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.home.homify.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.ct1Btn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, CategoryFoodActivity.class);
            startActivity(intent);
        });

        binding.ct2Btn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, CategoryHomeActivity.class);
            startActivity(intent);
        });

        binding.ct3Btn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, CategoryCleaningActivity.class);
            startActivity(intent);
        });

        binding.ct4Btn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, CategoryHygieneActivity.class);
            startActivity(intent);
        });

        binding.recipesBtn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, CategoryRecipesActivity.class);
            startActivity(intent);
        });

        binding.settingsBtn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        binding.shoppingListBtn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
            startActivity(intent);
        });

        binding.addNewItemBtn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            startActivity(intent);
        });
    }
}