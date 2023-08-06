package com.home.homify;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.home.homify.databinding.ActivityFoodBinding;

public class CategoryFoodActivity extends AppCompatActivity {
    private ActivityFoodBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        DatabaseUtils.populateTableLayout(this, tableLayout, "food");
    }
}
