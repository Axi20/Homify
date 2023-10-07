package com.home.isense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.home.isense.databinding.ActivityHygieneBinding;

public class CategoryHygieneActivity extends AppCompatActivity {
    private ActivityHygieneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHygieneBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        DatabaseUtils.populateTableLayout(this, tableLayout, "hygiene", "Higiénia");

        binding.addNewItemButton.setOnClickListener(v-> {
            Intent intent = new Intent(CategoryHygieneActivity.this, AddItemActivity.class);
            startActivity(intent);
        });
    }
}
