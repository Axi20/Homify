package com.home.homify;

public class RecipeItem {
    private String recipeName;
    private String imagePath;

    public RecipeItem(String imagePath, String recipeName) {
        this.imagePath = imagePath;
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getImagePath() {
        return imagePath;
    }

}

