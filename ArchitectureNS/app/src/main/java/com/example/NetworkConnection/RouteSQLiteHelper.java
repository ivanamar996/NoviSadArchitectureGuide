package com.example.NetworkConnection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RouteSQLiteHelper  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ArchitectureNS";
    private static int DATABASE_VERSION = 5;

    public static final String TABLE_ROUTE = "route_info";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ROUTE_TITLE = "title";
    public static final String COLUMN_ROUTE_DURATION = "duration";
    public static final String COLUMN_ROUTE_DESCRIPTION = "description";
    public static final String COLUMN_ROUTE_KILOMETRES = "kilometres";
    public static final String COLUMN_ROUTE_IMAGE = "image";

    public static final String TABLE_PLACE_INFO = "place_info";
    public static final String COLUMN_PLACE_ID = "place_info_id";
    public static final String COLUMN_PLACE_TITLE = "place_title";
    public static final String COLUMN_PLACE_DESCRIPTION = "place_description";
    public static final String COLUMN_PLACE_IMAGE = "place_image";
    public static final String COLUMN_PLACE_GRADE = "place_grade";
    public static final String COLUMN_PLACE_LATITUDE = "place_latitude";
    public static final String COLUMN_PLACE_LONGITUDE = "place_longitude";
    public static final String COLUMN_ROUTE_ID = "route_id";

    public static final String TABLE_RECOMMENDED = "recommended";
    public static final String COLUMN_RECOMMENDED_ID = "recommended_id";
    public static final String COLUMN_RECOMMENDED_TITLE = "recommended_title";
    public static final String COLUMN_RECOMMENDED_DESCRIPTION = "recommended_description";
    public static final String COLUMN_RECOMMENDED_IMAGE = "recommended_image";
    public static final String COLUMN_RECOMMENDED_GRADE = "recommended_grade";
    public static final String COLUMN_RECOMMENDED_LATITUDE = "recommended_latitude";
    public static final String COLUMN_RECOMMENDED_LONGITUDE = "recommended_longitude";
    public static final String COLUMN_RECOMMENDED_ROUTE_ID = "recommended_route_id";


    public RouteSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String DB_CREATE_TABLE_ROUTE = "create table "
            + TABLE_ROUTE + "("
            + COLUMN_ID  + " integer primary key, "
            + COLUMN_ROUTE_DURATION  + " real, "
            + COLUMN_ROUTE_TITLE + " text, "
            + COLUMN_ROUTE_DESCRIPTION + " text, "
            + COLUMN_ROUTE_KILOMETRES + " real, "
            + COLUMN_ROUTE_IMAGE+ " text "
            + ")";

    private static final String DB_CREATE_TABLE_PLACE_INFO = "create table "
            + TABLE_PLACE_INFO + "("
            + COLUMN_PLACE_ID  + " integer primary key, "
            + COLUMN_PLACE_TITLE + " text, "
            + COLUMN_PLACE_DESCRIPTION + " text, "
            + COLUMN_PLACE_IMAGE + " text, "
            + COLUMN_ROUTE_ID + " integer,"
            + COLUMN_PLACE_GRADE + " real,"
            + COLUMN_PLACE_LATITUDE + " real,"
            + COLUMN_PLACE_LONGITUDE + " real"
            + ")";

    private static final String DB_CREATE_TABLE_RECOMMENDED = "create table "
            + TABLE_RECOMMENDED + "("
            + COLUMN_RECOMMENDED_ID  + " integer primary key, "
            + COLUMN_RECOMMENDED_TITLE + " text, "
            + COLUMN_RECOMMENDED_DESCRIPTION + " text, "
            + COLUMN_RECOMMENDED_IMAGE + " text, "
            + COLUMN_RECOMMENDED_ROUTE_ID + " integer,"
            + COLUMN_RECOMMENDED_GRADE + " real,"
            + COLUMN_RECOMMENDED_LATITUDE + " real,"
            + COLUMN_RECOMMENDED_LONGITUDE + " real"
            + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_TABLE_ROUTE);
        db.execSQL(DB_CREATE_TABLE_PLACE_INFO);
        db.execSQL(DB_CREATE_TABLE_RECOMMENDED);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECOMMENDED);
        onCreate(db);

    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public static void setDatabaseVersion(int databaseVersion) {
        DATABASE_VERSION = databaseVersion;
    }
}