package com.example.architecturens;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.NetworkConnection.DBContentProvider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsResponse;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private List<LatLng> placePositions = new ArrayList<LatLng>();

    private GoogleMap mMap;
    public static final String PLACE_INFO = "com.example.architecturens.PLACE_INFO";
    private RouteInfo mRouteInfo;
    private RelativeLayout relativeLayoutCustomView;
    private TextView textViewPlaceName;
    private PlaceInfo place;
    private Uri routeUri;

    private Marker locationMarker;

    private Marker customMarker;
    private LatLng markerLatLng;
    private Polyline line;
    private Button detailsButton;
    SupportMapFragment mapFragment;

    FusedLocationProviderClient client;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        detailsButton = findViewById(R.id.detailsBtn);

        Bundle extras = getIntent().getExtras();
        routeUri = (bundle == null) ? null : (Uri) bundle.getParcelable(DBContentProvider.CONTENT_ITEM_TYpe);
        // Or passed from the other activity

        if (extras != null) {
            routeUri = extras.getParcelable(DBContentProvider.CONTENT_ITEM_TYpe);
            mRouteInfo = DBContentProvider.getRouteFromSqlite(routeUri);
        }



        relativeLayoutCustomView = findViewById(R.id.relativeLayoutCustomView);
        textViewPlaceName = findViewById(R.id.placeTitle);

        client = LocationServices.getFusedLocationProviderClient(this);




    }

    private void getCurrentLocation(){
        Task<Location> task = client.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if(location!=null){

                    LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

                    MarkerOptions options = new MarkerOptions().position(latLng)
                            .title("Current location")
                            .snippet("Current position");

                    //mMap.addMarker(options);


                }
            }
        });
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

        if(ActivityCompat.checkSelfPermission(MapsActivity.this, ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
            getCurrentLocation();
        }else{
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{ACCESS_FINE_LOCATION},44);
        }

        //LatLng noviSad = new LatLng(45.2711884,19.7787146);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(noviSad,15));

        markerLatLng = new LatLng(45.2711884,19.7787146);

        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        TextView numTxt = (TextView) marker.findViewById(R.id.num_txt);
        int count = 0;

        LatLng latLang = new LatLng(mRouteInfo.getPlaces().get(0).getLatitude(),mRouteInfo.getPlaces().get(0).getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLang,16));

        for(final PlaceInfo place : mRouteInfo.getPlaces()){

            count++;
            numTxt.setText(String.valueOf(count));
            latLang = new LatLng(place.getLatitude(),place.getLongitude());
            customMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(place.getLatitude(),place.getLongitude()))
                    .title(place.getTitle())
                    .snippet("Snippet")
                    .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker))));

            placePositions.add(latLang);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for(final PlaceInfo p : mRouteInfo.getPlaces()){
                    if(p.getTitle().equals(marker.getTitle())){
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

    private void displayCustomInfoWindow(Marker marker, PlaceInfo placeInfo) {

        if(placeInfo!=null) {
            relativeLayoutCustomView.setVisibility(View.VISIBLE);
            textViewPlaceName.setText(marker.getTitle());

            ImageView imageView = findViewById(R.id.placeImage);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(place.getImage(),0,place.getImage().length));

            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(MapsActivity.this,PlaceInfoActivity.class);
                    Uri placeUri = Uri.parse(DBContentProvider.CONTENT_ITEM_PLACE +"/"+ place.getId());
                    intent.putExtra(DBContentProvider.CONTENT_ITEM_PLACE, placeUri);
                    startActivity(intent);
                }
            });
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==44){
            if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                mMap.setMyLocationEnabled(true);
                getCurrentLocation();
            }
        }
    }
}
