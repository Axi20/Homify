package com.home.homify;

import android.content.Intent;
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
    private String itemName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Spinner categoryDropdown = binding.itemCategorySpinner;
        // Create a list of items for the spinner.
        String[] categories = new String[]{"Élelmiszer", "Háztartás", "Takarítószerek", "Higiénia", "Édesség", "Fűszer", "Zöldség/gyümölcs", "Sütés/főzés"};
        // Create an adapter to describe how the items are displayed.
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categoryDropdown.setAdapter(categoryAdapter);

        Spinner unitDropdown = binding.itemUnitSpinner;
        String[] units = new String[]{"kg", "g", "mg", "dkg", "l", "ml", "dl", "db", "csomag", "doboz", "flakon", "korty", "üveg", "vödör", "korty", "zacskó"};
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        unitDropdown.setAdapter(unitAdapter);

        binding.itemCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = binding.itemCategorySpinner.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO: Handle the case when nothing is selected
            }
        });

        binding.itemUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unit = binding.itemUnitSpinner.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO: Handle the case when nothing is selected
            }
        });

        binding.itemSaveButton.setOnClickListener(v-> {

            itemName = binding.itemTitleInput.getText().toString().trim();
            String quantity = binding.itemQuantityInput.getText().toString().trim();
            if (itemName.isEmpty() || quantity.isEmpty()) {
                Toast.makeText(this, "A mezők kitöltése kötelező!", Toast.LENGTH_SHORT).show();
            }
            else {
                switch (category) {
                    case "Élelmiszer":
                        DatabaseUtils.saveDataToTheTable(this, "food", itemName, category, quantity, unit);
                        break;
                    case "Édesség":
                        DatabaseUtils.saveDataToTheTable(this, "food", itemName, category, quantity, unit);
                        break;
                    case "Fűszer":
                        DatabaseUtils.saveDataToTheTable(this, "food", itemName, category, quantity, unit);
                        break;
                    case "Sütés/főzés":
                        DatabaseUtils.saveDataToTheTable(this, "food", itemName, category, quantity, unit);
                        break;
                    case "Zöldség/gyümölcs":
                        DatabaseUtils.saveDataToTheTable(this, "food", itemName, category, quantity, unit);
                        break;
                    case "Háztartás":
                        DatabaseUtils.saveDataToTheTable(this, "household", itemName, category, quantity, unit);
                        break;
                    case "Takarítószerek":
                        DatabaseUtils.saveDataToTheTable(this, "cleaning", itemName, category, quantity, unit);
                        break;
                    case "Higiénia":
                        DatabaseUtils.saveDataToTheTable(this, "hygiene", itemName, category, quantity, unit);
                        break;
                }
                Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.itemCancelButton.setOnClickListener(v-> {
            Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
