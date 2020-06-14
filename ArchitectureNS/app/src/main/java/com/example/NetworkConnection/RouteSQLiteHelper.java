package com.example.NetworkConnection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RouteSQLiteHelper  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ArchitectureNS";
    private static int DATABASE_VERSION = 5;

    public static final String TABLE_ROUTE = "route_info";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_KILOMETRES = "kilometres";
    public static final String COLUMN_PICTURE_FILE_NAME = "picture";

    public static final String TABLE_PLACE_INFO = "place_info";
    public static final String COLUMN_PLACE_TITLE = "place_title";
    public static final String COLUMN_PLACE_DESCRIPTION = "place_description";
    public static final String COLUMN_ROUTE_ID = "route_id";


    public RouteSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String DB_CREATE_TABLE_ROUTE = "create table "
            + TABLE_ROUTE + "("
            + COLUMN_ID  + " text primary key, "
            + COLUMN_DURATION  + " integer, "
            + COLUMN_TITLE + " text, "
            + COLUMN_DESCRIPTION + " text, "
            + COLUMN_KILOMETRES + " double, "
            + COLUMN_PICTURE_FILE_NAME + " text "
            + ")";

    private static final String DB_CREATE_TABLE_PLACE_INFO = "create table "
            + TABLE_PLACE_INFO + "("
            + COLUMN_ID  + " text primary key, "
            + COLUMN_PLACE_TITLE + " text, "
            + COLUMN_PLACE_DESCRIPTION + " real, "
            + COLUMN_PICTURE_FILE_NAME + " real, "
            + COLUMN_ROUTE_ID + " integer "
            + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_TABLE_ROUTE);
        db.execSQL(DB_CREATE_TABLE_PLACE_INFO);

    }

    // kada zelimo da izmeninmo tabele, moramo pozvati drop table za sve tabele koje imamo
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE_INFO);
        onCreate(db);

    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public static void setDatabaseVersion(int databaseVersion) {
        DATABASE_VERSION = databaseVersion;
    }
}