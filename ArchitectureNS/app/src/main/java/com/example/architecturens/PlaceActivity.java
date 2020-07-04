package com.example.architecturens;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.NetworkConnection.DBContentProvider;
import com.example.NetworkConnection.RouteSQLiteHelper;

import java.util.ArrayList;

public class PlaceActivity extends AppCompatActivity {

    public static final String ROUTE_INFO = "com.example.architecturens.ROUTE_INFO";
    private RouteInfo mRouteInfo;
    private Uri routeUri;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_place);

        Bundle extras = getIntent().getExtras();
        routeUri = (bundle == null) ? null : (Uri) bundle.getParcelable(DBContentProvider.CONTENT_ITEM_TYpe);
        // Or passed from the other activity

        if(extras != null) {
            routeUri = extras.getParcelable(DBContentProvider.CONTENT_ITEM_TYpe);
            getIntentAndDisplayValues(routeUri);
        }

    }

    private void getIntentAndDisplayValues(Uri uri) {

        mRouteInfo = DBContentProvider.getRouteFromSqlite(uri);


        ImageView imageView = findViewById(R.id.routeImageView);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(mRouteInfo.getImage(),0,mRouteInfo.getImage().length));

        TextView textViewName = findViewById(R.id.textViewName);
        textViewName.setText(mRouteInfo.getTitle());

        TextView textViewKm = findViewById(R.id.textViewKm);
        textViewKm.setText(String.valueOf(mRouteInfo.getKilometres()) + " km");

        TextView textViewH = findViewById(R.id.textViewH);
        textViewH.setText(String.valueOf(mRouteInfo.getDuration()) + " h");

        TextView textViewSight = findViewById(R.id.textViewSight);
        textViewSight.setText(String.valueOf(mRouteInfo.getPlaces().size()));

        TextView textViewDescription = findViewById(R.id.textViewDescription);
        textViewDescription.setText(mRouteInfo.getDescription());

        LinearLayout linearLayoutHorizontal = findViewById(R.id.linear_layout_horizontal);

        for(final PlaceInfo place : mRouteInfo.getPlaces()) {

            RelativeLayout relativeLayout = new RelativeLayout(this);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeLayout.setLayoutParams(layoutParams);

            ImageView imageViewPlace = new ImageView(this);
            layoutParams = new RelativeLayout.LayoutParams(convertToDp(180), convertToDp(180));
            imageViewPlace.setLayoutParams(layoutParams);
            imageViewPlace.setAdjustViewBounds(true);
            imageViewPlace.setPadding(3, 3, 3, 3);
            imageViewPlace.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewPlace.setImageBitmap(BitmapFactory.decodeByteArray(place.getImage(), 0, place.getImage().length));

            imageViewPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PlaceActivity.this,PlaceInfoActivity.class);
                    Uri placeUri = Uri.parse(DBContentProvider.CONTENT_ITEM_PLACE +"/"+ place.getId());
                    intent.putExtra(DBContentProvider.CONTENT_ITEM_PLACE, placeUri);
                    startActivity(intent);
                }
            });

            relativeLayout.addView(imageViewPlace);

            TextView textViewPlaceName = new TextView(this);
            layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(convertToDp(10), convertToDp(150), 0, 0);
            textViewPlaceName.setLayoutParams(layoutParams);
            textViewPlaceName.setAlpha((float) 0.8);
            textViewPlaceName.setBackgroundColor(getResources().getColor(R.color.gray2));
            textViewPlaceName.setPadding(2, 2, 2, 2);
            textViewPlaceName.setText(place.getTitle());
            textViewPlaceName.setTextColor(Color.BLACK);
            textViewPlaceName.setTextSize(14);


            relativeLayout.addView(textViewPlaceName);

            linearLayoutHorizontal.addView(relativeLayout);


        }
    }

    public void onMapButtonClick(View view){
        Intent intent = new Intent(PlaceActivity.this,MapsActivity.class);
        intent.putExtra(DBContentProvider.CONTENT_ITEM_TYpe, routeUri);
        startActivity(intent);
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

    private int convertToDp(int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }
}
