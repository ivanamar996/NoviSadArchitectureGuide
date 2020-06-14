package com.example.NetworkConnection;

import android.content.ContentProvider;
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
    public static final Uri CONTENT_URI_ROUTE = Uri.parse("content://" + AUTHORITY + "/" + ROUTE_PATH);
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int ALL_ROUTES = 1;
    private static final int ONE_ROUTE = 2;

    static {
        sURIMatcher.addURI(AUTHORITY, ROUTE_PATH, ALL_ROUTES);
        sURIMatcher.addURI(AUTHORITY, ROUTE_PATH + "/#", ONE_ROUTE);
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
            case ALL_ROUTES:
                rowsDeleted = sqlDB.delete(RouteSQLiteHelper.TABLE_ROUTE,
                        selection,
                        selectionArgs);
                break;
            case ONE_ROUTE:
                String idRoute = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(RouteSQLiteHelper.TABLE_ROUTE,
                            RouteSQLiteHelper.COLUMN_ID + "=" + idRoute,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(RouteSQLiteHelper.TABLE_ROUTE,
                            RouteSQLiteHelper.COLUMN_ID + "=" + idRoute
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (sURIMatcher.match(uri)) {
            case ALL_ROUTES:
                return "vnd.android.cursor.dir/vnd.com.as400samplecode.contentprovider.route";
            case ONE_ROUTE:
                return "vnd.android.cursor.item/vnd.com.as400samplecode.contentprovider.route";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

        Uri retVal = null;
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case ALL_ROUTES:
                id = sqlDB.insert(RouteSQLiteHelper.TABLE_ROUTE, null, values);
                retVal = Uri.parse(ROUTE_PATH + "/" + id);
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
            case ONE_ROUTE:
                // Adding the ID to the original query
                queryBuilder.appendWhere(RouteSQLiteHelper.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                //$FALL-THROUGH$
            case ALL_ROUTES:
                // Set the table
                queryBuilder.setTables(RouteSQLiteHelper.TABLE_ROUTE);
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
            case ALL_ROUTES:
                rowsUpdated = sqlDB.update(RouteSQLiteHelper.TABLE_ROUTE,
                        values,
                        selection,
                        selectionArgs);
                break;
            case ONE_ROUTE:
                String idRoute = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(RouteSQLiteHelper.TABLE_ROUTE,
                            values,
                            RouteSQLiteHelper.COLUMN_ID + "=" + idRoute,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(RouteSQLiteHelper.TABLE_ROUTE,
                            values,
                            RouteSQLiteHelper.COLUMN_ID + "=" + idRoute
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
