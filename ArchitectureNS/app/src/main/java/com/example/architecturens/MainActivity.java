package com.example.architecturens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        initializeDisplayContent();
    }

    private void initializeDisplayContent() {

        DataManager dataManager = DataManager.getInstance();
        List<RouteInfo> routes = dataManager.getRoutes();

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
            imageView.setImageResource(getReourceID(route.getPictureFileName()));

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
        imageViewPlace.setImageResource(getReourceID(place1.getPictureFileName()));

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
        textViewPlaceName.setText(place1.getPlaceTitle());
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
        imageViewPlace1.setImageResource(getReourceID(place2.getPictureFileName()));

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
        textViewPlaceName1.setText(place2.getPlaceTitle());
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
