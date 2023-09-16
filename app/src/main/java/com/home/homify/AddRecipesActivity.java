package com.home.homify;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.home.homify.databinding.ActivityAddRecipeBinding;
import com.home.homify.databinding.ActivityRecipesBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddRecipesActivity extends AppCompatActivity {
    private ActivityAddRecipeBinding binding;
    private ActivityResultLauncher<Intent> imageSelectionLauncher;
    private ImageView imageView;
    private String selectedCategory;
    private String itemName;
    private String cookingTime;
    private String servings;
    private String[] ingredients;
    private String method;
    private String image_path;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRecipeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        imageView = binding.imageViewRecipePhoto;

        // Initialize the ActivityResultLauncher
        imageSelectionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                selectedImageUri = result.getData().getData();
                imageView.setImageURI(selectedImageUri);
            }
        });

        Spinner spinner = binding.spinnerCategory;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.itemSaveButton.setOnClickListener(v->{
            // Get the entered data from the form
            itemName = binding.editTextRecipeName.getText().toString();
            cookingTime = binding.editTextCookingTime.getText().toString();
            servings = binding.editTextServingSize.getText().toString();
            selectedCategory = binding.spinnerCategory.getSelectedItem().toString();
            // Split the entered ingredients into an array
            ingredients = binding.editTextIngredients.getText().toString().split(", ");
            method = binding.editTextInstructions.getText().toString();

            // Create a directory within app's internal storage
            File storageDir = new File(getApplicationContext().getFilesDir(), "recipe_images");
            if (!storageDir.exists()) {
                storageDir.mkdirs();
            }

            // Generate a unique filename
            String uniqueFileName = "recipe_image_" + System.currentTimeMillis() + ".jpg";

            // Construct a path relative to the app's private storage
            String relativeImagePath = "recipe_images/" + uniqueFileName;


            // Create a file object with the directory and filename
            File imageFile = new File(storageDir, uniqueFileName);

            binding.imageViewRecipePhoto.setDrawingCacheEnabled(true);
            binding.imageViewRecipePhoto.buildDrawingCache();
            Bitmap uploadedBitmap = binding.imageViewRecipePhoto.getDrawingCache();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            uploadedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] uploadedImage = stream.toByteArray();


            // Save the image to the file
            try (FileOutputStream outStream = new FileOutputStream(imageFile)) {
                outStream.write(uploadedImage);  // Write the image data
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Get the absolute path of the saved image
            //image_path = imageFile.getAbsolutePath();
            image_path = getPathFromUri(selectedImageUri);

            DatabaseUtils.saveRecipesToTheTable(this, itemName, "recipes", cookingTime, servings, selectedCategory, ingredients, method, relativeImagePath);

            Intent intent = new Intent(AddRecipesActivity.this, CategoryRecipesActivity.class);
            startActivity(intent);
        });

        binding.itemCancelButton.setOnClickListener(v->{
            Intent intent = new Intent(AddRecipesActivity.this, CategoryRecipesActivity.class);
            startActivity(intent);
        });
    }
    public void onImageUploadClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageSelectionLauncher.launch(intent);
    }

    // Convert a Bitmap to a byte array
    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private String getPathFromUri(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) {
            return null;
        }
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }
}
