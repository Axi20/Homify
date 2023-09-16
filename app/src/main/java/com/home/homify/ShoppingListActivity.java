package com.home.homify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;
import com.home.homify.databinding.ActivityShoppingBinding;

public class ShoppingListActivity extends AppCompatActivity {
    private ActivityShoppingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShoppingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        DatabaseUtils.populateShoppingPageLayout(this, tableLayout, "shopping");

        binding.addNewBtn.setOnClickListener(v-> {
          DatabaseUtils.showAddToShoppingListPopup(this, "shopping");
        });
    }
}
