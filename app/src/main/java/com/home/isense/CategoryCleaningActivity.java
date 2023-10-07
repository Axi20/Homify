package com.home.isense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.home.isense.databinding.ActivityCleaningBinding;

public class CategoryCleaningActivity extends AppCompatActivity {
    private ActivityCleaningBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCleaningBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        DatabaseUtils.populateTableLayout(this, tableLayout, "cleaning", "Takarítószerek");

        binding.addNewItemButton.setOnClickListener(v-> {
            Intent intent = new Intent(CategoryCleaningActivity.this, AddItemActivity.class);
            startActivity(intent);
        });
    }
}
