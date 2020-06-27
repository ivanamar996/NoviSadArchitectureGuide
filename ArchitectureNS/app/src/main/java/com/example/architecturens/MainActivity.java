package com.example.architecturens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.NetworkConnection.DBContentProvider;
import com.example.NetworkConnection.DbConnection;
import com.example.NetworkConnection.RouteSQLiteHelper;
import com.example.service.GetDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Uri routeUri;
    private Uri placeUri;
    private List<RouteInfo> routes = new ArrayList<RouteInfo>();

    @Override
    protected void onResume(){
        super.onResume();

        final GetDataService service = DbConnection.getRetrofitInstance().create(GetDataService.class);
        RouteSQLiteHelper dbHelper = new RouteSQLiteHelper(MainActivity.this);

        Call<String> call = service.getAllRoutes();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                ObjectMapper objectMapper = new ObjectMapper();

                try {
                    RouteInfo[] routes = objectMapper.readValue(response.body(), RouteInfo[].class);
                    List<RouteInfo> routeList = Arrays.asList(routes);

                    saveToDatabase(routeList);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveToDatabase(List<RouteInfo> routeList){

        ContentValues values =  new ContentValues();
        ContentValues placeValues =  new ContentValues();

        for(RouteInfo route : routeList){

            values.put(RouteSQLiteHelper.COLUMN_ID, route.getId());
            values.put(RouteSQLiteHelper.COLUMN_ROUTE_DURATION, route.getDuration());
            values.put(RouteSQLiteHelper.COLUMN_ROUTE_TITLE, route.getTitle());
            values.put(RouteSQLiteHelper.COLUMN_ROUTE_DESCRIPTION, route.getDescription());
            values.put(RouteSQLiteHelper.COLUMN_ROUTE_KILOMETRES, route.getKilometres());
            values.put(RouteSQLiteHelper.COLUMN_ROUTE_IMAGE, route.getImage());

            if(routes.contains(route)){
                getContentResolver().update(DBContentProvider.CONTENT_URI_ROUTE, values, "_id="+route.getId(), null);
            }else {
                routeUri = getContentResolver().insert(DBContentProvider.CONTENT_URI_ROUTE, values);
            }


            for(PlaceInfo place : route.getPlaces()){

                placeValues.put(RouteSQLiteHelper.COLUMN_PLACE_ID,place.getId());
                placeValues.put(RouteSQLiteHelper.COLUMN_PLACE_TITLE,place.getTitle());
                placeValues.put(RouteSQLiteHelper.COLUMN_PLACE_DESCRIPTION,place.getDescription());
                placeValues.put(RouteSQLiteHelper.COLUMN_PLACE_IMAGE,place.getImage());
                placeValues.put(RouteSQLiteHelper.COLUMN_ROUTE_ID,route.getId());
                placeValues.put(RouteSQLiteHelper.COLUMN_PLACE_GRADE,place.getGrade());
                placeValues.put(RouteSQLiteHelper.COLUMN_PLACE_LATITUDE,place.getLatitude());
                placeValues.put(RouteSQLiteHelper.COLUMN_PLACE_LONGITUDE,place.getLongitude());

                placeUri = getContentResolver().insert(DBContentProvider.CONTENT_URI_PLACE, placeValues);
            }
        }


    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_routes);

        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view) ;
        mToggle.syncState();
        routeUri = (bundle == null) ? null : (Uri) bundle.getParcelable(DBContentProvider.CONTENT_ITEM_TYpe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetupDrawerContent(navigationView);

        routes = DBContentProvider.getRoutesFromSqlite();

        initializeDisplayContent();
    }

    public void selectItemDrawer(MenuItem menuItem){
        Fragment myFragment = null;
        Class fragmentClass;
        LinearLayout scroll = (LinearLayout) findViewById(R.id.scrolllinear);
        scroll.setVisibility(LinearLayout.GONE);
        switch (menuItem.getItemId()) {
            case R.id.language:
                fragmentClass = Language.class;
                break;
            case R.id.location:
                fragmentClass = Location.class;
                break;
            case R.id.rate:
                fragmentClass = RateApp.class;
                break;
            case R.id.about:
                fragmentClass = AboutApp.class;
                break;
            default:
                fragmentClass = Language.class;
        }

        try{
            myFragment= (Fragment) fragmentClass.newInstance();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.flayoutcontent,myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();

    }

    private void SetupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            //getSupportFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            LinearLayout scroll = (LinearLayout) findViewById(R.id.scrolllinear);
            scroll.setVisibility(LinearLayout.VISIBLE);
            setTitle("ArchitecureNS");
            super.onBackPressed();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initializeDisplayContent() {

        LinearLayout mainLinearLayout = findViewById(R.id.linear_layout);

        for (final RouteInfo route : routes) {

            RelativeLayout relativeLayout = new RelativeLayout(this);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeLayout.setLayoutParams(layoutParams);

            ImageView imageView = new ImageView(this);
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, convertToDp(300));
            imageView.setLayoutParams(layoutParams);
            imageView.setAdjustViewBounds(true);
            imageView.setPadding(3, 3, 3, 3);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(route.getImage(),0,route.getImage().length));

            imageView.setOnClickListener(new View.OnClickListener() {
                                          @Override public void onClick(View v) {
                                              Intent intent = new Intent(MainActivity.this,PlaceActivity.class);
                                              intent.putExtra(PlaceActivity.PLACE_INFO, route);
                                              startActivity(intent);
                                          }
                                      });

            relativeLayout.addView(imageView);

            TextView textViewRouteName = new TextView(this);
            layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, convertToDp(230), 0, 0);
            textViewRouteName.setLayoutParams(layoutParams);
            textViewRouteName.setAlpha((float) 0.8);
            textViewRouteName.setBackgroundResource(R.color.white);
            textViewRouteName.setPadding(2, 2, 2, 2);
            textViewRouteName.setText(route.getTitle());
            textViewRouteName.setTextColor(Color.BLACK);
            textViewRouteName.setTextSize(16);

            relativeLayout.addView(textViewRouteName);

            ImageView imageViewHumanWalking = new ImageView(this);
            layoutParams = new RelativeLayout.LayoutParams(convertToDp(30), convertToDp(30));
            RelativeLayout.MarginLayoutParams marginLayoutParams = new RelativeLayout.LayoutParams(layoutParams);
            marginLayoutParams.setMargins(0, convertToDp(260), 0, 0);
            layoutParams = new RelativeLayout.LayoutParams(marginLayoutParams);
            imageViewHumanWalking.setLayoutParams(layoutParams);
            imageViewHumanWalking.setAdjustViewBounds(true);
            imageViewHumanWalking.setAlpha((float) 0.8);
            imageViewHumanWalking.setBackgroundResource(R.color.white);
            imageViewHumanWalking.setPadding(3, 3, 3, 3);
            imageViewHumanWalking.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewHumanWalking.setImageResource(R.drawable.human_walking);

            relativeLayout.addView(imageViewHumanWalking);

            TextView textViewKm = new TextView(this);
            textViewKm.setId(View.generateViewId());
            layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(convertToDp(30), convertToDp(260), 0, 0);
            textViewKm.setLayoutParams(layoutParams);
            textViewKm.setAlpha((float) 0.8);
            textViewKm.setBackgroundResource(R.color.white);
            textViewKm.setPadding(2, 2, 2, 2);
            textViewKm.setText(route.getKilometres() + " km");
            textViewKm.setTextColor(Color.BLACK);
            textViewKm.setTextSize(20);

            relativeLayout.addView(textViewKm);

            ImageView imageViewClock = new ImageView(this);
            imageViewClock.setId(View.generateViewId());
            layoutParams = new RelativeLayout.LayoutParams(convertToDp(30), convertToDp(30));
            marginLayoutParams = new RelativeLayout.LayoutParams(layoutParams);
            marginLayoutParams.setMargins(0, convertToDp(260), 0, 0);
            layoutParams = new RelativeLayout.LayoutParams(marginLayoutParams);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, textViewKm.getId());
            imageViewClock.setLayoutParams(layoutParams);
            imageViewClock.setAdjustViewBounds(true);
            imageViewClock.setAlpha((float) 0.8);
            imageViewClock.setBackgroundResource(R.color.white);
            imageViewClock.setPadding(3, 3, 3, 3);
            imageViewClock.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewClock.setImageResource(R.drawable.clock);

            relativeLayout.addView(imageViewClock);

            TextView textViewH = new TextView(this);
            layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, convertToDp(260), 0, 0);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, imageViewClock.getId());
            textViewH.setLayoutParams(layoutParams);
            textViewH.setAlpha((float) 0.8);
            textViewH.setBackgroundResource(R.color.white);
            textViewH.setPadding(2, 2, 2, 2);
            textViewH.setText(route.getDuration() + " h");
            textViewH.setTextColor(Color.BLACK);
            textViewH.setTextSize(20);

            relativeLayout.addView(textViewH);

            mainLinearLayout.addView(relativeLayout);
        }

        //recommended places

        LinearLayout recommededPlaces = findViewById(R.id.linear_layout_recommended);

        PlaceInfo place1 = routes.get(0).getPlaces().get(0);
        PlaceInfo place2 = routes.get(0).getPlaces().get(1);

        RelativeLayout relativeLayout = new RelativeLayout(this);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayout.setLayoutParams(layoutParams);

        ImageView imageViewPlace = new ImageView(this);
        layoutParams = new RelativeLayout.LayoutParams(convertToDp(180), convertToDp(180));
        imageViewPlace.setLayoutParams(layoutParams);
        imageViewPlace.setAdjustViewBounds(true);
        imageViewPlace.setPadding(3, 3, 3, 3);
        imageViewPlace.setScaleType(ImageView.ScaleType.FIT_XY);
        imageViewPlace.setImageBitmap(BitmapFactory.decodeByteArray(place1.getImage(),0,place1.getImage().length));

        imageViewPlace.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // intent za place info activity
            }
        });

        relativeLayout.addView(imageViewPlace);

        TextView textViewPlaceName = new TextView(this);
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(convertToDp(10), convertToDp(150), 0, 0);
        textViewPlaceName.setLayoutParams(layoutParams);
        textViewPlaceName.setAlpha((float) 0.8);
        textViewPlaceName.setBackgroundResource(R.color.white);
        textViewPlaceName.setPadding(2, 2, 2, 2);
        textViewPlaceName.setText(place1.getTitle());
        textViewPlaceName.setTextColor(Color.BLACK);
        textViewPlaceName.setTextSize(16);


        relativeLayout.addView(textViewPlaceName);

        recommededPlaces.addView(relativeLayout);

        RelativeLayout relativeLayout1 = new RelativeLayout(this);

        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayout1.setLayoutParams(layoutParams1);

        ImageView imageViewPlace1 = new ImageView(this);
        layoutParams1 = new RelativeLayout.LayoutParams(convertToDp(180), convertToDp(180));
        imageViewPlace1.setLayoutParams(layoutParams1);
        imageViewPlace1.setAdjustViewBounds(true);
        imageViewPlace1.setPadding(3, 3, 3, 3);
        imageViewPlace1.setScaleType(ImageView.ScaleType.FIT_XY);
        imageViewPlace1.setImageBitmap(BitmapFactory.decodeByteArray(place2.getImage(),0,place2.getImage().length));

        imageViewPlace1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // intent za place info activity
            }
        });

        relativeLayout1.addView(imageViewPlace1);

        TextView textViewPlaceName1 = new TextView(this);
        layoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams1.setMargins(convertToDp(10), convertToDp(150), 0, 0);
        textViewPlaceName1.setLayoutParams(layoutParams1);
        textViewPlaceName1.setAlpha((float) 0.8);
        textViewPlaceName1.setBackgroundResource(R.color.white);
        textViewPlaceName1.setPadding(2, 2, 2, 2);
        textViewPlaceName1.setText(place2.getTitle());
        textViewPlaceName1.setTextColor(Color.BLACK);
        textViewPlaceName1.setTextSize(16);


        relativeLayout1.addView(textViewPlaceName1);

        recommededPlaces.addView(relativeLayout1);


    }

   private int convertToDp(int value){
       return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
   }

   private int getReourceID(String fileName){

       Resources res = getResources();

       String[] pathParts = fileName.split("/");
       String imageName = pathParts[1];

       String[] partsImgName = imageName.split("\\.");
       String name = partsImgName[0];

       int resID = res.getIdentifier(name , "drawable", getPackageName());

       return resID;
   }
}
