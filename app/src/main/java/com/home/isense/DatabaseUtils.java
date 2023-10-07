package com.home.isense;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;

public class DatabaseUtils {

    /**
     * Saves data to the specified table in the database, after checking if the item already exists.
     *
     * @param context  The application or activity context.
     * @param table    The name of the table in which data will be saved.
     * @param itemName The name of the item to be saved.
     * @param category The category of the item.
     * @param quantity The quantity of the item.
     * @param unit     The unit of measurement for the item's quantity.
     * @see #checkIfItemExists(SQLiteDatabase, String, String)
     */
    public static void saveDataToTheTable(Context context, String table, String itemName, String category, String quantity, String unit) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean itemExists = checkIfItemExists(db, table, itemName);

        if (itemExists) {
            Toast.makeText(context, R.string.already_exist, Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("_item_name", itemName);
        values.put("_category", category);
        values.put("_quantity", quantity);
        values.put("_unit", unit);
        long rowId = db.insert(table, null, values);

        if (rowId != -1) {
            Toast.makeText(context, R.string.item_added, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    /**
     * Deletes an item from the given table in the database.
     * The function performs a database query to retrieve the ID of the item with the given name.
     * Then it constructs a delete operation to remove the corresponding item from the given table.
     * After the deletion is completed, a Toast message is shown to indicate whether the deletion
     * was successful or not.
     *
     * @param context   The application or activity context.
     * @param table     The name of the table from which data will be deleted.
     * @param selection The selection criteria for the row(s) to be deleted.
     * @param itemName  The name of the item to be used as part of the selection criteria.
     */
    public static void deleteData(Context context, String table, String selection, String itemName) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] columns = {"_id"};
        String[] selectionArgs = {itemName};
        Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int itemId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String deleteSelection = "_id = ?";
                String[] deleteSelectionArgs = new String[]{String.valueOf(itemId)};
                int rowsDeleted = db.delete(table, deleteSelection, deleteSelectionArgs);

                Toast.makeText(context, (rowsDeleted > 0) ? R.string.item_deleted :
                        R.string.error, Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            Activity activity = (Activity) context;
            activity.recreate();
        }
        db.close();
    }

    /**
     * Check whether the item is already existing in the database table, if yes
     * then it will return true, if not, it will return to false.
     *
     * @param db       The database.
     * @param table    The name of the table.
     * @param itemName The name of the item.
     * @return boolean
     */
    private static boolean checkIfItemExists(@NonNull SQLiteDatabase db, String table, String itemName) {
        String[] projection = {"_item_name"};
        String selection = "_item_name = ?";
        String[] selectionArgs = {itemName};

        Cursor cursor = db.query(table, projection, selection, selectionArgs, null, null, null);
        boolean itemExists = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }

        return itemExists;
    }

    /**
     * A function that creates a custom popup window for editing item details from the given
     * table in the database. It contains EditText views for modifying the item's name, quantity,
     * and unit. It also contains a button to add the item to the shopping list.
     * When the user clicks "Mentés" (Save), the edited data is retrieved, and an update
     * operation is performed in the given table to save the changes. A Toast message indicates
     * the success or failure of the update. Clicking "Mégsem" (Cancel) dismisses the popup
     * without any changes.
     *
     * @param context  The application or activity context.
     * @param itemName The name of the item to be edited.
     * @param table    The name of the table from which data will be deleted.
     */
    public static void showEditPopup(Context context, String itemName, String table) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create and configure the popup window
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.last_edit);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);

        EditText editItemName = new EditText(context);
        editItemName.setHint(R.string.title);
        layout.addView(editItemName);

        EditText editQuantity = new EditText(context);
        editQuantity.setHint(R.string.quantity);
        layout.addView(editQuantity);

        EditText editUnit = new EditText(context);
        editUnit.setHint(R.string.unit);
        layout.addView(editUnit);

        // Create a CheckBox
        CheckBox checkBox = new CheckBox(context);
        checkBox.setText(R.string.add_to_the_shopping_list);
        layout.addView(checkBox);

        //Button addToList = new Button(context);
        //addToList.setId(R.id.shopping_list_btn);
        //addToList.setBackgroundResource(R.drawable.baseline_add_shopping_cart_24);

        int desiredWidthInPixels = 100;
        int desiredHeightInPixels = 100;

        // Create new LayoutParams for the button and set the width, height and margin
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                desiredWidthInPixels,
                desiredHeightInPixels
        );
        params.setMargins(0, 50, 0, 0);
        //addToList.setLayoutParams(params);
        //layout.addView(addToList);

        String[] columns = {"_id", "_item_name", "_quantity", "_unit"};
        String selection = "_item_name = ?";
        String[] selectionArgs = {itemName};
        Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // Retrieve data from the cursor
            String originalItemName = cursor.getString(cursor.getColumnIndexOrThrow("_item_name"));
            String originalQuantity = cursor.getString(cursor.getColumnIndexOrThrow("_quantity"));
            String originalUnit = cursor.getString(cursor.getColumnIndexOrThrow("_unit"));

            // Set the original data to the respective EditText views
            editItemName.setText(originalItemName);
            editQuantity.setText(originalQuantity);
            editUnit.setText(originalUnit);

            ContentValues values = new ContentValues();
            values.put("_item_name", editItemName.getText().toString());
            values.put("_quantity", editQuantity.getText().toString());
            values.put("_unit", editUnit.getText().toString());
        }
        builder.setView(layout);



        builder.setPositiveButton(R.string.save, (dialog, which) -> {
            ContentValues values = new ContentValues();
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            values.put("_item_name", editItemName.getText().toString());
            values.put("_quantity", editQuantity.getText().toString());
            values.put("_unit", editUnit.getText().toString());

            int rowsUpdated = db.update(table, values, "_id = ?", new String[]{String.valueOf(itemId)});

            Toast.makeText(context, (rowsUpdated > 0) ? R.string.item_updated:
                    R.string.error, Toast.LENGTH_SHORT).show();

            if (checkBox.isChecked()){
                addToShoppingList(context, editItemName.getText().toString(), editQuantity.getText().toString(), editUnit.getText().toString(), "shopping");
            }

            cursor.close();
            db.close();
            Activity activity = (Activity) context;
            activity.recreate();
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Populates the TableLayout with data retrieved from a specified database table.
     *
     * @param context     The application or activity context.
     * @param tableLayout The TableLayout to populate with data.
     * @param table       The name of the database table to retrieve data from.
     * @see #showEditPopup(Context, String, String)
     * @see #deleteData(Context, String, String, String)
     */
    public static void populateTableLayout(Context context, TableLayout tableLayout, String table, String category) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "_category = ?";
        String[] selectionArgs = {category};
        Cursor cursor = db.query(table, null, selection, selectionArgs, null, null, "_item_name");

        if (cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(cursor.getColumnIndexOrThrow("_item_name"));
                String quantity = cursor.getString(cursor.getColumnIndexOrThrow("_quantity"));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow("_unit"));

                TableRow tableRow = new TableRow(context);

                TextView itemNameTextView = new TextView(context);
                itemNameTextView.setText(itemName);
                itemNameTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));

                TextView quantityTextView = new TextView(context);
                quantityTextView.setText(quantity);
                quantityTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

                TextView unitTextView = new TextView(context);
                unitTextView.setText(unit);
                unitTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

                Button editButton = new Button(context);
                //editButton.setText("Edit");
                editButton.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                editButton.setTag(itemName);
                int color = ContextCompat.getColor(context, R.color.pink_light);
                editButton.setBackgroundTintList(ColorStateList.valueOf(color));
                editButton.setBackgroundResource(R.drawable.outline_edit_24);
                editButton.setWidth(30);
                editButton.setHeight(30);


                editButton.setOnClickListener(v -> {
                    String itemName1 = (String) v.getTag();
                    DatabaseUtils.showEditPopup(context, itemName1, table);
                });

                Button deleteButton = new Button(context);
                //deleteButton.setText("Del");
                deleteButton.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                deleteButton.setTag(itemName);
                color = ContextCompat.getColor(context, R.color.black);
                deleteButton.setBackgroundTintList(ColorStateList.valueOf(color));
                deleteButton.setBackgroundResource(R.drawable.outline_delete_24);
                deleteButton.setHeight(30);
                deleteButton.setWidth(30);

                deleteButton.setOnClickListener(v -> {
                    String itemName1 = (String) v.getTag();
                    DatabaseUtils.deleteData(context, table, "_item_name = ?", itemName1);
                });

                tableRow.addView(itemNameTextView);
                tableRow.addView(quantityTextView);
                tableRow.addView(unitTextView);
                tableRow.addView(editButton);
                tableRow.addView(deleteButton);
                tableLayout.addView(tableRow);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    /**
     * Saves a new recipe to the specified table in the database.
     *
     * @param context      The application context.
     * @param itemName     The name of the recipe.
     * @param table        The name of the database table to insert the recipe into.
     * @param cooking_time The cooking time for the recipe.
     * @param servings     The number of servings the recipe yields.
     * @param category     The category of the recipe.
     * @param ingredients  An array of ingredients for the recipe.
     * @param method       The cooking method or instructions for the recipe.
     * @param image_path   The file path to the recipe's image.
     */
    public static void saveRecipesToTheTable(Context context, String itemName, String table, String cooking_time, String servings, String category, String[] ingredients, String method, String image_path) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean itemExists = checkIfItemExists(db, table, itemName);

        if (itemExists) {
            Toast.makeText(context, R.string.already_exist, Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("_item_name", itemName);
        values.put("cooking_time", cooking_time);
        values.put("servings", servings);
        values.put("_category", category);


        // Convert ingredients array to a comma-separated string
        String ingredientsString = TextUtils.join(", ", ingredients);
        values.put("ingredients", ingredientsString);

        values.put("method", method);
        values.put("image_path", image_path);

        long rowId = db.insert(table, null, values);

        if (rowId != -1) {
            Toast.makeText(context, R.string.item_added, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    /**
     * Loads recipes of a specific category from the database and populates them into a ListView.
     *
     * @param categoryResourceId  The category resource id of recipes to load.
     * @param context        The application context.
     * @param recipeListView The ListView where the recipes will be displayed.
     */
    public static void loadRecipesByCategory(int categoryResourceId, Context context, ListView recipeListView) {
        ArrayList<RecipeItem> recipeList;
        RecipeListAdapter adapter;
        recipeList = new ArrayList<>();

        adapter = new RecipeListAdapter(context, recipeList);
        recipeListView.setAdapter(adapter);

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the category string value from the resource identifier
        String category = context.getString(categoryResourceId);

        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_IMAGE_PATH,
                DatabaseHelper.COLUMN_ITEM_NAME
        };

        String selection = DatabaseHelper.COLUMN_CATEGORY + " = ?";
        String[] selectionArgs = {category};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_RECIPES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int recipeId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE_PATH));
            String recipeName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ITEM_NAME));
            recipeList.add(new RecipeItem(recipeId,imagePath, recipeName));
        }

        cursor.close();
        db.close();


        adapter.notifyDataSetChanged();
        Log.d("RecipesListActivity", "Category: " + category);
        Log.d("RecipesListActivity", "Number of recipes: " + recipeList.size());

    }

    public static void showAddToShoppingListPopup(Context context, String table) {
        // Create and configure the popup window
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.add_new_item);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);

        EditText editItemName = new EditText(context);
        editItemName.setHint(R.string.title);
        layout.addView(editItemName);

        EditText editQuantity = new EditText(context);
        editQuantity.setHint(R.string.quantity);
        layout.addView(editQuantity);

        EditText editUnit = new EditText(context);
        editUnit.setHint(R.string.unit);
        layout.addView(editUnit);

        builder.setView(layout);

        builder.setPositiveButton(R.string.add_item, (dialog, which) -> {
            String itemName = editItemName.getText().toString();
            String quantity = editQuantity.getText().toString();
            String unit = editUnit.getText().toString();

            if (!itemName.isEmpty() && !quantity.isEmpty() && !unit.isEmpty()) {
                // Add the data to the shopping table
                addToShoppingList(context, itemName, quantity, unit, table);

                // Refresh the activity
                Activity activity = (Activity) context;
                activity.recreate();
            } else {
                Toast.makeText(context, R.string.required, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private static void addToShoppingList(Context context, String itemName, String quantity, String unit, String table) {
        long rowId;
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("_item_name", itemName);
        values.put("_quantity", quantity);
        values.put("_unit", unit);

        boolean itemExists = checkIfItemExists(db, table, itemName);

        if (itemExists) {
            Toast.makeText(context, R.string.already_exist, Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            rowId = db.insert(table, null, values);
        }

        if (rowId != -1) {
            Toast.makeText(context, R.string.item_added, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    /**
     * Populates the TableLayout with data retrieved from a specified database table.
     *
     * @param context     The application or activity context.
     * @param tableLayout The TableLayout to populate with data.
     * @param table       The name of the database table to retrieve data from.
     * @see #showEditPopup(Context, String, String)
     * @see #deleteData(Context, String, String, String)
     */
    public static void populateShoppingPageLayout(Context context, TableLayout tableLayout, String table) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(table, null, null, null, null, null, "_item_name");

        if (cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(cursor.getColumnIndexOrThrow("_item_name"));
                String quantity = cursor.getString(cursor.getColumnIndexOrThrow("_quantity"));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow("_unit"));

                TableRow tableRow = new TableRow(context);

                TextView itemNameTextView = new TextView(context);
                itemNameTextView.setText(itemName);
                itemNameTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));

                TextView quantityTextView = new TextView(context);
                quantityTextView.setText(quantity);
                quantityTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

                TextView unitTextView = new TextView(context);
                unitTextView.setText(unit);
                unitTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

                Button editButton = new Button(context);
                //editButton.setText("Edit");
                editButton.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                editButton.setTag(itemName);
                int color = ContextCompat.getColor(context, R.color.pink_light);
                editButton.setBackgroundTintList(ColorStateList.valueOf(color));
                editButton.setBackgroundResource(R.drawable.outline_edit_24);
                editButton.setWidth(30);
                editButton.setHeight(30);


                editButton.setOnClickListener(v -> {
                    String itemName1 = (String) v.getTag();
                    DatabaseUtils.showEditPopup(context, itemName1, table);
                });

                Button deleteButton = new Button(context);
                //deleteButton.setText("Del");
                deleteButton.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                deleteButton.setTag(itemName);
                color = ContextCompat.getColor(context, R.color.black);
                deleteButton.setBackgroundTintList(ColorStateList.valueOf(color));
                deleteButton.setBackgroundResource(R.drawable.outline_delete_24);
                deleteButton.setHeight(30);
                deleteButton.setWidth(30);

                deleteButton.setOnClickListener(v -> {
                    String itemName1 = (String) v.getTag();
                    DatabaseUtils.deleteData(context, table, "_item_name = ?", itemName1);
                });

                tableRow.addView(itemNameTextView);
                tableRow.addView(quantityTextView);
                tableRow.addView(unitTextView);
                tableRow.addView(editButton);
                tableRow.addView(deleteButton);
                tableLayout.addView(tableRow);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    public static int getRecipeIdFromDatabase(String clickedRecipe, Context context) {
        // Default value indicating no match found
        int recipeId = -1;

        // Open a readable database
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {DatabaseHelper.COLUMN_ID};

        String selection = DatabaseHelper.COLUMN_ITEM_NAME + " = ?";
        String[] selectionArgs = {clickedRecipe};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_RECIPES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Check if a matching record was found
        if (cursor.moveToFirst()) {
            recipeId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
        }

        cursor.close();
        db.close();

        return recipeId;
    }

    public static void viewRecipe(String clickedRecipe, Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Retrieve recipe data from the database using recipeId
        int recipeId = getRecipeIdFromDatabase(clickedRecipe, context);

        String recipeName = "";
        String imagePath = "";
        String cookingTime = "";
        String servings = "";
        String category = "";
        String ingredients = "";
        String cookingMethod = "";

        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(recipeId)};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_RECIPES,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            imagePath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE_PATH));
            recipeName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ITEM_NAME));
            cookingTime = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COOKING_TIME));
            servings = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SERVINGS));
            category = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY));
            ingredients = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INGREDIENTS));
            cookingMethod = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_METHOD));
        }

        // Display the generated layout
        Intent intent = new Intent(context, RecipeDetailsActivity.class);

        // Pass the recipe ID and other data as an extra to the activity
        intent.putExtra("recipeId", recipeId);
        intent.putExtra("recipeName", recipeName);
        intent.putExtra("imagePath", imagePath);
        intent.putExtra("cookingTime", cookingTime);
        intent.putExtra("servings", servings);
        intent.putExtra("category", category);
        intent.putExtra("ingredients", ingredients);
        intent.putExtra("cookingMethod", cookingMethod);

        // Start the RecipeDetailActivity
        context.startActivity(intent);
    }
}