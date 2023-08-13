package com.home.homify;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "home_inventory.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_FOOD = "food";
    private static final String TABLE_HOUSEHOLD = "household";
    private static final String TABLE_CLEANING = "cleaning";
    private static final String TABLE_HYGIENE = "hygiene";

    // Common column names.
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ITEM_NAME = "_item_name";
    private static final String COLUMN_CATEGORY = "_category";
    private static final String COLUMN_QUANTITY = "_quantity";
    private static final String COLUMN_UNIT = "_unit";

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

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db,TABLE_FOOD);
        createTable(db, TABLE_HOUSEHOLD);
        createTable(db, TABLE_CLEANING);
        createTable(db, TABLE_HYGIENE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade here
    }
}
