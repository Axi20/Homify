package com.home.isense;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.home.isense.databinding.ActivityItemRecipeBinding;
import com.home.isense.databinding.ActivityMainDishesBinding;

public class RecipeItem {
    private int recipeId;
    private String recipeName;
    private String imagePath;

    public RecipeItem(int recipeId, String imagePath, String recipeName) {
        this.recipeId = recipeId;
        this.imagePath = imagePath;
        this.recipeName = recipeName;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getImagePath() {
        return imagePath;
    }

}




