package com.home.isense;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import com.home.isense.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.close();

        binding.inventoryButton.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, ChooseFromInventory.class);
            startActivity(intent);
        });

        binding.recipesButton.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, CategoryRecipesActivity.class);
            startActivity(intent);
        });

        binding.shoppingListButton.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
            startActivity(intent);
        });

        binding.settingsButton.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}