package com.home.homify;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "home_inventory.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_FOOD = "food";
    private static final String TABLE_HOUSEHOLD = "household";
    private static final String TABLE_CLEANING = "cleaning";
    private static final String TABLE_HYGIENE = "hygiene";
    public static final String TABLE_RECIPES = "recipes";

    private static final String TABLE_SHOPPING = "shopping";

    // Common column names.
    private static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM_NAME = "_item_name";
    public static final String COLUMN_CATEGORY = "_category";
    private static final String COLUMN_QUANTITY = "_quantity";
    private static final String COLUMN_UNIT = "_unit";

    // Recipes table column names
    private static final String COLUMN_COOKING_TIME = "cooking_time";
    private static final String COLUMN_SERVINGS = "servings";
    private static final String COLUMN_INGREDIENTS = "ingredients";
    private static final String COLUMN_METHOD = "method";
    public static final String COLUMN_IMAGE_PATH = "image_path";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void createTable(SQLiteDatabase db, String tableName) {
        String createTableQuery = "CREATE TABLE " + tableName + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_QUANTITY + " TEXT,"
                + COLUMN_UNIT + " TEXT"
                + ")";
        db.execSQL(createTableQuery);
    }

    public void createRecipesTable(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_RECIPES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_COOKING_TIME + " TEXT,"
                + COLUMN_SERVINGS + " TEXT,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_INGREDIENTS + " TEXT,"
                + COLUMN_METHOD + " TEXT,"
                + COLUMN_IMAGE_PATH + " TEXT"
                + ")";
        db.execSQL(createTableQuery);
    }

    public void createShoppingListTable(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_SHOPPING + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_QUANTITY + " TEXT,"
                + COLUMN_UNIT + " TEXT"
                + ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db,TABLE_FOOD);
        createTable(db, TABLE_HOUSEHOLD);
        createTable(db, TABLE_CLEANING);
        createTable(db, TABLE_HYGIENE);
        createRecipesTable(db);
        createShoppingListTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade here
    }
}
