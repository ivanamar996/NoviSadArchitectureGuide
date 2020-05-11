package com.example.architecturens;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private List<LatLng> placePositions = new ArrayList<LatLng>();

    private GoogleMap mMap;
    public static final String PLACE_INFO = "com.example.architecturens.PLACE_INFO";
    private RouteInfo mRouteInfo;
    private RelativeLayout relativeLayoutCustomView;
    private TextView textViewPlaceName;
    private PlaceInfo place;

    private Marker customMarker;
    private LatLng markerLatLng;
    private Polyline line;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        mRouteInfo = intent.getParcelableExtra(PLACE_INFO);

        placePositions.add(new LatLng(45.255790, 19.845772));
        placePositions.add(new LatLng(45.256132, 19.845423));
        placePositions.add(new LatLng(45.256283, 19.847117));

        relativeLayoutCustomView = findViewById(R.id.relativeLayoutCustomView);
        textViewPlaceName = findViewById(R.id.placeTitle);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //LatLng noviSad = new LatLng(45.2711884,19.7787146);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(noviSad,15));


        markerLatLng = new LatLng(45.2711884,19.7787146);

        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        TextView numTxt = (TextView) marker.findViewById(R.id.num_txt);
        int count = 0;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placePositions.get(0),16));

        for(final PlaceInfo place : mRouteInfo.getPlaces()){

            count++;
            numTxt.setText(String.valueOf(count));

            customMarker = mMap.addMarker(new MarkerOptions()
                    .position(placePositions.get(count-1))
                    .title(place.getPlaceTitle())
                    .snippet("Snippet")
                    .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker))));

        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for(final PlaceInfo p : mRouteInfo.getPlaces()){
                    if(p.getPlaceTitle().equals(marker.getTitle())){
                        place = p;
                        break;
                    }
                }
                displayCustomInfoWindow(marker, place);
                return true;
            }
        });

        line = mMap.addPolyline(new PolylineOptions()
                .addAll(placePositions)
                .width(5)
                .color(ContextCompat.getColor(getApplicationContext(),R.color.green)));
    }

    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private void displayCustomInfoWindow(Marker marker, PlaceInfo place) {

        if(place!=null) {
            relativeLayoutCustomView.setVisibility(View.VISIBLE);
            textViewPlaceName.setText(marker.getTitle());

            ImageView imageView = findViewById(R.id.placeImage);
            imageView.setImageResource(getResourceID(place.getPictureFileName()));

        }

    }

    private int getResourceID(String fileName){

        Resources res = getResources();

        String[] pathParts = fileName.split("/");
        String imageName = pathParts[1];

        String[] partsImgName = imageName.split("\\.");
        String name = partsImgName[0];

        int resID = res.getIdentifier(name , "drawable", getPackageName());

        return resID;
    }

    public void onDetailsClick(View view){
        Intent intent = new Intent(MapsActivity.this,PlaceInfoActivity.class);
        intent.putExtra(PlaceActivity.PLACE_INFO, place);
        startActivity(intent);
    }

}
