package com.example.NetworkConnection;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class DBContentProvider extends ContentProvider {

    private RouteSQLiteHelper database;
    private static final String AUTHORITY = "com.example.architecturens";
    private static final String ROUTE_PATH = "route";
    private static final String PLACE_PATH = "place";
    public static final Uri CONTENT_URI_ROUTE = Uri.parse("content://" + AUTHORITY + "/" + ROUTE_PATH);
    public static final Uri CONTENT_URI_PLACE = Uri.parse("content://" + AUTHORITY + "/" + PLACE_PATH);
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String CONTENT_ITEM_TYpe = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/routes";
    public static final String CONTENT_ITEM_PLACE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/places";

    private static final int ROUTES = 1;
    private static final int PLACES = 2;

    static {
        sURIMatcher.addURI(AUTHORITY, ROUTE_PATH, ROUTES);
        sURIMatcher.addURI(AUTHORITY, PLACE_PATH, PLACES);
    }

    public DBContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        int rowsDeleted = 0;
        switch (uriType) {
            case ROUTES:
                rowsDeleted = sqlDB.delete(RouteSQLiteHelper.TABLE_ROUTE,
                        selection,
                        selectionArgs);
                break;
                case PLACES:
                    rowsDeleted = sqlDB.delete(RouteSQLiteHelper.TABLE_PLACE_INFO,
                            selection,
                            selectionArgs);
                    break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

        Uri retVal = null;
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case ROUTES:
                id = sqlDB.insert(RouteSQLiteHelper.TABLE_ROUTE, null, values);
                retVal = Uri.parse(ROUTE_PATH + "/" + id);
                break;
            case PLACES:
                id = sqlDB.insert(RouteSQLiteHelper.TABLE_PLACE_INFO, null, values);
                retVal = Uri.parse(PLACE_PATH + "/" + id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return retVal;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        database = new RouteSQLiteHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case ROUTES:
                // Set the table
                queryBuilder.setTables(RouteSQLiteHelper.TABLE_ROUTE);
                break;
            case PLACES:
                // Set the table
                queryBuilder.setTables(RouteSQLiteHelper.TABLE_PLACE_INFO);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        int rowsUpdated = 0;
        switch (uriType) {
            case ROUTES:
                rowsUpdated = sqlDB.update(RouteSQLiteHelper.TABLE_ROUTE,
                        values,
                        selection,
                        selectionArgs);
                break;
            case PLACES:
                rowsUpdated = sqlDB.update(RouteSQLiteHelper.TABLE_PLACE_INFO,
                        values,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
