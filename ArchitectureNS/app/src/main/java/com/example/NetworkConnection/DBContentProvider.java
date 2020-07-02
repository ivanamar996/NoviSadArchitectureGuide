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

import com.example.architecturens.PlaceInfo;
import com.example.architecturens.RouteInfo;

import java.util.ArrayList;
import java.util.List;

public class DBContentProvider extends ContentProvider {

    private static RouteSQLiteHelper database;
    private static final String AUTHORITY = "com.example.architecturens";
    private static final String ROUTE_PATH = "route";
    private static final String PLACE_PATH = "place";
    private static final String RECOMMENDED_PATH = "recommended";
    public static final Uri CONTENT_URI_ROUTE = Uri.parse("content://" + AUTHORITY + "/" + ROUTE_PATH);
    public static final Uri CONTENT_URI_PLACE = Uri.parse("content://" + AUTHORITY + "/" + PLACE_PATH);
    public static final Uri CONTENT_URI_RECOMMENDED = Uri.parse("content://" + AUTHORITY + "/" + RECOMMENDED_PATH);
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String CONTENT_ITEM_TYpe = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/routes";
    public static final String CONTENT_ITEM_PLACE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/places";
    public static final String CONTENT_ITEM_RECOMMENDED = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/recommended";

    private static final int ROUTES = 1;
    private static final int PLACES = 2;
    private static final int RECOMMENDEDS = 3;

    static {
        sURIMatcher.addURI(AUTHORITY, ROUTE_PATH, ROUTES);
        sURIMatcher.addURI(AUTHORITY, PLACE_PATH, PLACES);
        sURIMatcher.addURI(AUTHORITY, RECOMMENDED_PATH, RECOMMENDEDS);
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
            case  RECOMMENDEDS:
                rowsDeleted = sqlDB.delete(RouteSQLiteHelper.TABLE_RECOMMENDED,
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
            case RECOMMENDEDS:
                id = sqlDB.insert(RouteSQLiteHelper.TABLE_RECOMMENDED, null, values);
                retVal = Uri.parse(RECOMMENDED_PATH + "/" + id);
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
            case RECOMMENDEDS:
                // Set the table
                queryBuilder.setTables(RouteSQLiteHelper.TABLE_RECOMMENDED);
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
            case RECOMMENDEDS:
                rowsUpdated = sqlDB.update(RouteSQLiteHelper.TABLE_RECOMMENDED,
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

    public static List<RouteInfo> getRoutesFromSqlite(){

        List<RouteInfo> routeList=new ArrayList<RouteInfo>();
        SQLiteDatabase db = database.getWritableDatabase();

        Cursor cursor = db.query(RouteSQLiteHelper.TABLE_ROUTE,new String[]{RouteSQLiteHelper.COLUMN_ID,RouteSQLiteHelper.COLUMN_ROUTE_TITLE,RouteSQLiteHelper.COLUMN_ROUTE_DESCRIPTION,
                        RouteSQLiteHelper.COLUMN_ROUTE_DURATION,RouteSQLiteHelper.COLUMN_ROUTE_KILOMETRES,RouteSQLiteHelper.COLUMN_ROUTE_IMAGE},
                null,null,null,null,null,null);

        if(cursor!=null &cursor.getCount()>0){
            while(cursor.moveToNext()){
                RouteInfo route = new RouteInfo(cursor.getInt(0),cursor.getString(1),new ArrayList<PlaceInfo>(),cursor.getDouble(3),
                        cursor.getString(2),cursor.getDouble(4),
                        cursor.getBlob(5));
                routeList.add(route);

                Cursor placeCursor = db.query(RouteSQLiteHelper.TABLE_PLACE_INFO,new String[]{RouteSQLiteHelper.COLUMN_PLACE_ID,RouteSQLiteHelper.COLUMN_PLACE_TITLE,
                RouteSQLiteHelper.COLUMN_PLACE_DESCRIPTION,RouteSQLiteHelper.COLUMN_PLACE_LONGITUDE,RouteSQLiteHelper.COLUMN_PLACE_LATITUDE,RouteSQLiteHelper.COLUMN_PLACE_GRADE,
                RouteSQLiteHelper.COLUMN_PLACE_IMAGE,RouteSQLiteHelper.COLUMN_ROUTE_ID},"route_id="+route.getId(),null,null,null,null,null);

                if(placeCursor!=null &placeCursor.getCount()>0){
                    while(placeCursor.moveToNext()){

                        PlaceInfo place = new PlaceInfo(placeCursor.getInt(0),placeCursor.getString(1),placeCursor.getString(2),placeCursor.getBlob(6),
                                placeCursor.getDouble(5),placeCursor.getDouble(4),placeCursor.getDouble(3),route);

                        route.getPlaces().add(place);

                    }
                }
                placeCursor.close();
            }
        }
        cursor.close();
        return routeList;
    }

    public static RouteInfo getRouteFromSqlite(Uri uri) {

        RouteInfo route = new RouteInfo();

        String id = String.valueOf(uri).split("/")[2];
        SQLiteDatabase db = database.getWritableDatabase();

        Cursor cursor = db.query(RouteSQLiteHelper.TABLE_ROUTE,new String[]{RouteSQLiteHelper.COLUMN_ID,RouteSQLiteHelper.COLUMN_ROUTE_TITLE,RouteSQLiteHelper.COLUMN_ROUTE_DESCRIPTION,
                        RouteSQLiteHelper.COLUMN_ROUTE_DURATION,RouteSQLiteHelper.COLUMN_ROUTE_KILOMETRES,RouteSQLiteHelper.COLUMN_ROUTE_IMAGE},
                "_id="+Integer.parseInt(id),null,null,null,null,null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            route = new RouteInfo(cursor.getInt(0),cursor.getString(1),new ArrayList<PlaceInfo>(),cursor.getDouble(3),
                    cursor.getString(2),cursor.getDouble(4),
                    cursor.getBlob(5));

            Cursor placeCursor = db.query(RouteSQLiteHelper.TABLE_PLACE_INFO,new String[]{RouteSQLiteHelper.COLUMN_PLACE_ID,RouteSQLiteHelper.COLUMN_PLACE_TITLE,
                    RouteSQLiteHelper.COLUMN_PLACE_DESCRIPTION,RouteSQLiteHelper.COLUMN_PLACE_LONGITUDE,RouteSQLiteHelper.COLUMN_PLACE_LATITUDE,RouteSQLiteHelper.COLUMN_PLACE_GRADE,
                    RouteSQLiteHelper.COLUMN_PLACE_IMAGE,RouteSQLiteHelper.COLUMN_ROUTE_ID},"route_id="+route.getId(),null,null,null,null,null);

            if(placeCursor!=null &placeCursor.getCount()>0){
                while(placeCursor.moveToNext()){

                    PlaceInfo place = new PlaceInfo(placeCursor.getInt(0),placeCursor.getString(1),placeCursor.getString(2),placeCursor.getBlob(6),
                            placeCursor.getDouble(5),placeCursor.getDouble(4),placeCursor.getDouble(3),route);

                    route.getPlaces().add(place);

                }
            }
            placeCursor.close();


        }

        return route;
    }

    public static PlaceInfo getPlaceFromSqlite(Uri uri){

        String id = String.valueOf(uri).split("/")[2];
        SQLiteDatabase db = database.getWritableDatabase();
        PlaceInfo place = new PlaceInfo();
        RouteInfo route = new RouteInfo();

        Cursor placeCursor = db.query(RouteSQLiteHelper.TABLE_PLACE_INFO,new String[]{RouteSQLiteHelper.COLUMN_PLACE_ID,RouteSQLiteHelper.COLUMN_PLACE_TITLE,
                RouteSQLiteHelper.COLUMN_PLACE_DESCRIPTION,RouteSQLiteHelper.COLUMN_PLACE_LONGITUDE,RouteSQLiteHelper.COLUMN_PLACE_LATITUDE,RouteSQLiteHelper.COLUMN_PLACE_GRADE,
                RouteSQLiteHelper.COLUMN_PLACE_IMAGE,RouteSQLiteHelper.COLUMN_ROUTE_ID},"place_info_id="+Integer.parseInt(id),null,null,null,null,null);

        if(placeCursor!=null &placeCursor.getCount()>0){

            placeCursor.moveToFirst();

            place = new PlaceInfo(placeCursor.getInt(0),placeCursor.getString(1),placeCursor.getString(2),placeCursor.getBlob(6),
                    placeCursor.getDouble(5),placeCursor.getDouble(4),placeCursor.getDouble(3),route);

        }
        placeCursor.close();

        return place;

    }

    public static PlaceInfo getRecommendedPlaceFromSqlite(Uri uri){

        String id = String.valueOf(uri).split("/")[2];
        SQLiteDatabase db = database.getWritableDatabase();
        PlaceInfo place = new PlaceInfo();
        RouteInfo route = new RouteInfo();

        Cursor placeCursor = db.query(RouteSQLiteHelper.TABLE_RECOMMENDED,new String[]{RouteSQLiteHelper.COLUMN_RECOMMENDED_ID,RouteSQLiteHelper.COLUMN_RECOMMENDED_TITLE,
                RouteSQLiteHelper.COLUMN_RECOMMENDED_DESCRIPTION,RouteSQLiteHelper.COLUMN_RECOMMENDED_LONGITUDE,RouteSQLiteHelper.COLUMN_RECOMMENDED_LATITUDE,RouteSQLiteHelper.COLUMN_RECOMMENDED_GRADE,
                RouteSQLiteHelper.COLUMN_RECOMMENDED_IMAGE,RouteSQLiteHelper.COLUMN_RECOMMENDED_ROUTE_ID},"recommended_id="+Integer.parseInt(id),null,null,null,null,null);

        if(placeCursor!=null &placeCursor.getCount()>0){

            placeCursor.moveToFirst();

            place = new PlaceInfo(placeCursor.getInt(0),placeCursor.getString(1),placeCursor.getString(2),placeCursor.getBlob(6),
                    placeCursor.getDouble(5),placeCursor.getDouble(4),placeCursor.getDouble(3),route);

        }
        placeCursor.close();

        return place;

    }



    public static List<PlaceInfo> getRecommendedFromSqlite(){

        List<PlaceInfo> placeList=new ArrayList<PlaceInfo>();
        PlaceInfo place=new PlaceInfo();

        SQLiteDatabase db = database.getWritableDatabase();

        Cursor placeCursor = db.query(RouteSQLiteHelper.TABLE_RECOMMENDED,new String[]{RouteSQLiteHelper.COLUMN_RECOMMENDED_ID,RouteSQLiteHelper.COLUMN_RECOMMENDED_TITLE,
                RouteSQLiteHelper.COLUMN_RECOMMENDED_DESCRIPTION,RouteSQLiteHelper.COLUMN_RECOMMENDED_LONGITUDE,RouteSQLiteHelper.COLUMN_RECOMMENDED_LONGITUDE,RouteSQLiteHelper.COLUMN_RECOMMENDED_GRADE,
                RouteSQLiteHelper.COLUMN_RECOMMENDED_IMAGE,RouteSQLiteHelper.COLUMN_RECOMMENDED_ROUTE_ID},null,null,null,null,null,null);

        if(placeCursor!=null &placeCursor.getCount()>0){
            while(placeCursor.moveToNext()) {

                place = new PlaceInfo(placeCursor.getInt(0), placeCursor.getString(1), placeCursor.getString(2), placeCursor.getBlob(6),
                        placeCursor.getDouble(5), placeCursor.getDouble(4), placeCursor.getDouble(3), null);
                placeList.add(place);

            }

        }
        placeCursor.close();

        return placeList;
    }
}
