package com.home.homify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.home.homify.databinding.ActivitySweetsBinding;

public class CategorySweets extends AppCompatActivity {
    private ActivitySweetsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySweetsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        DatabaseUtils.populateTableLayout(this, tableLayout, "food", "Édesség");

        binding.addNewItemButton.setOnClickListener(v-> {
            Intent intent = new Intent(CategorySweets.this, AddItemActivity.class);
            startActivity(intent);
        });
    }
}
