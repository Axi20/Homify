package com.home.homify;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.home.homify.databinding.ActivityAddItemBinding;

public class AddItemActivity extends AppCompatActivity {
    private ActivityAddItemBinding binding;
    private String category = "";
    private String unit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Spinner categoryDropdown = binding.categorySpinner;
        // Create a list of items for the spinner.
        String[] categories = new String[]{"Élelmiszer", "Háztartás", "Takarítószerek", "Higiénia"};
        // Create an adapter to describe how the items are displayed.
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categoryDropdown.setAdapter(categoryAdapter);

        Spinner unitDropdown = binding.unitSpinner;
        String[] units = new String[]{"kg", "g", "mg", "dkg", "l", "ml", "dl", "db"};
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        unitDropdown.setAdapter(unitAdapter);

        binding.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = binding.categorySpinner.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO: Handle the case when nothing is selected
            }
        });

        binding.unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unit = binding.unitSpinner.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO: Handle the case when nothing is selected
            }
        });

        binding.saveBtn.setOnClickListener(v-> {

            String itemTitle = binding.editItemTitle.getText().toString().trim();
            String quantity = binding.editQuantity.getText().toString().trim();
            if (itemTitle.isEmpty() || quantity.isEmpty()) {
                Toast.makeText(this, "A mezők kitöltése kötelező!", Toast.LENGTH_SHORT).show();
            }
            else {
                switch (category) {
                    case "Élelmiszer":
                        DatabaseUtils.saveDataToTheTable(this, "food", itemTitle, category, quantity, unit);
                        break;
                    case "Háztartás":
                        DatabaseUtils.saveDataToTheTable(this, "household", itemTitle, category, quantity, unit);
                        break;
                    case "Takarítószerek":
                        DatabaseUtils.saveDataToTheTable(this, "cleaning", itemTitle, category, quantity, unit);
                        break;
                    case "Higiénia":
                        DatabaseUtils.saveDataToTheTable(this, "hygiene", itemTitle, category, quantity, unit);
                        break;
                }
            }
        });
    }
}
