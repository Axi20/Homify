package com.home.isense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RecipeListAdapter extends ArrayAdapter<RecipeItem> {
    public RecipeListAdapter(Context context, ArrayList<RecipeItem> recipeList) {
        super(context, 0, recipeList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_recipe, parent, false);
        }

        RecipeItem currentRecipe = getItem(position);

        // Find the image view and text view in the convertView
        ImageView recipeImage = convertView.findViewById(R.id.recipeImage);
        TextView recipeName = convertView.findViewById(R.id.recipeName);

        // Set the recipe name
        recipeName.setText(currentRecipe.getRecipeName());


        // Load and set the image using the image path
        String imagePath = currentRecipe.getImagePath();
        String absoluteImagePath = getContext().getFilesDir() + "/" + currentRecipe.getImagePath();
        Bitmap bitmap = BitmapFactory.decodeFile(absoluteImagePath);

        //Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        recipeImage.setImageBitmap(bitmap);

        return convertView;
    }
}
