package com.example.architecturens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlaceActivity extends AppCompatActivity {
    public static final String PLACE_INFO = "com.example.architecturens.PLACE_INFO";
    private RouteInfo mRouteInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        getIntentAndDisplayValues();

    }

    private void getIntentAndDisplayValues() {
        Intent intent = getIntent();
        mRouteInfo = intent.getParcelableExtra(PLACE_INFO);
        ImageView imageView = findViewById(R.id.routeImageView);
        imageView.setImageResource(getResourceID(mRouteInfo.getPictureFileName()));

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

        for(final PlaceInfo place : mRouteInfo.getPlaces()){

            RelativeLayout relativeLayout = new RelativeLayout(this);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeLayout.setLayoutParams(layoutParams);

            ImageView imageViewPlace = new ImageView(this);
            layoutParams = new RelativeLayout.LayoutParams(convertToDp(180), convertToDp(180));
            imageViewPlace.setLayoutParams(layoutParams);
            imageViewPlace.setAdjustViewBounds(true);
            imageViewPlace.setPadding(3, 3, 3, 3);
            imageViewPlace.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewPlace.setImageResource(getResourceID(place.getPictureFileName()));

            imageView.setOnClickListener(new View.OnClickListener() {
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
            textViewPlaceName.setText(place.getPlaceTitle());
            textViewPlaceName.setTextColor(Color.BLACK);
            textViewPlaceName.setTextSize(16);


            relativeLayout.addView(textViewPlaceName);

            linearLayoutHorizontal.addView(relativeLayout);

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

    private int convertToDp(int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }
}
