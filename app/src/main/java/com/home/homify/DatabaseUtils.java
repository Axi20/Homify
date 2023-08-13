package com.home.homify;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

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
            Toast.makeText(context, "Az elem már létezik az adatbázisban!", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("_item_name", itemName);
        values.put("_category", category);
        values.put("_quantity", quantity);
        values.put("_unit", unit);
        long rowId = db.insert(table, null, values);

        if (rowId != -1) {
            Toast.makeText(context, "Sikeres hozzáadás!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Hiba történt!", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(context, (rowsDeleted > 0) ? "Sikeresen törölve!" :
                        "Hiba történt!", Toast.LENGTH_SHORT).show();
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
        builder.setTitle("Szerkesztés");

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);

        EditText editItemName = new EditText(context);
        editItemName.setHint("Név");
        layout.addView(editItemName);

        EditText editQuantity = new EditText(context);
        editQuantity.setHint("Mennyiség");
        layout.addView(editQuantity);

        EditText editUnit = new EditText(context);
        editUnit.setHint("Mértékegység");
        layout.addView(editUnit);

        Button addToList = new Button(context);
        addToList.setId(R.id.shopping_list_btn);
        addToList.setBackgroundResource(R.drawable.baseline_add_shopping_cart_24);

        int desiredWidthInPixels = 100;
        int desiredHeightInPixels = 100;

        // Create new LayoutParams for the button and set the width, height and margin
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                desiredWidthInPixels,
                desiredHeightInPixels
        );
        params.setMargins(0, 50, 0, 0);
        addToList.setLayoutParams(params);
        layout.addView(addToList);

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

        builder.setPositiveButton("Mentés", (dialog, which) -> {
            ContentValues values = new ContentValues();
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            values.put("_item_name", editItemName.getText().toString());
            values.put("_quantity", editQuantity.getText().toString());
            values.put("_unit", editUnit.getText().toString());

            int rowsUpdated = db.update(table, values, "_id = ?", new String[]{String.valueOf(itemId)});

            Toast.makeText(context, (rowsUpdated > 0) ? "Elem frissítve!" :
                    "Hiba történt!", Toast.LENGTH_SHORT).show();

            cursor.close();
            db.close();
            Activity activity = (Activity) context;
            activity.recreate();
        });

        builder.setNegativeButton("Mégsem", (dialog, which) -> {
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

}