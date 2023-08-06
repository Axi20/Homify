package com.home.homify;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.home.homify.databinding.ActivityHomeBinding;

public class CategoryHomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        DatabaseUtils.populateTableLayout(this, tableLayout, "household");
    }

}
