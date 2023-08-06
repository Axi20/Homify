package com.home.homify;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "home_inventory.db";
    private static final int DATABASE_VERSION = 1;

    // Table names.
    private static final String TABLE_FOOD = "food";
    private static final String TABLE_HOUSEHOLD = "household";
    private static final String TABLE_CLEANING = "cleaning";
    private static final String TABLE_HYGIENE = "hygiene";
    //private static final String TABLE_RECIPES = "recipes";
    private static final String TABLE_SHOPPING_LIST = "shopping_list";
    //private static final String TABLE_SETTINGS = "settings";

    // Common column names.
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ITEM_NAME = "_item_name";
    private static final String COLUMN_CATEGORY = "_category";
    private static final String COLUMN_QUANTITY = "_quantity";
    private static final String COLUMN_UNIT = "_unit";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createFoodTable = "CREATE TABLE " + TABLE_FOOD + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_QUANTITY + " TEXT,"
                + COLUMN_UNIT + " TEXT"
                + ")";
        db.execSQL(createFoodTable);

        String createHouseholdTable = "CREATE TABLE " + TABLE_HOUSEHOLD + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_QUANTITY + " TEXT,"
                + COLUMN_UNIT + " TEXT"
                + ")";
        db.execSQL(createHouseholdTable);

        String createCleaningTable = "CREATE TABLE " + TABLE_CLEANING + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_QUANTITY + " TEXT,"
                + COLUMN_UNIT + " TEXT"
                + ")";
        db.execSQL(createCleaningTable);

        String createHygieneTable = "CREATE TABLE " + TABLE_HYGIENE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_QUANTITY + " TEXT,"
                + COLUMN_UNIT + " TEXT"
                + ")";
        db.execSQL(createHygieneTable);

        String createShoppingListTable = "CREATE TABLE " + TABLE_SHOPPING_LIST + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_QUANTITY + " TEXT,"
                + COLUMN_UNIT + " TEXT"
                + ")";
        db.execSQL(createShoppingListTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade here
    }
}
