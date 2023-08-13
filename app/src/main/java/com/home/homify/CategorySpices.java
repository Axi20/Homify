package com.home.homify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.home.homify.databinding.ActivitySpicesBinding;

public class CategorySpices extends AppCompatActivity {
    private ActivitySpicesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpicesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        DatabaseUtils.populateTableLayout(this, tableLayout, "food", "Fűszer");

        binding.addNewItemButton.setOnClickListener(v-> {
            Intent intent = new Intent(CategorySpices.this, AddItemActivity.class);
            startActivity(intent);
        });
    }
}
