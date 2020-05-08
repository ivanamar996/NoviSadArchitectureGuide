package com.example.architecturens;

import androidx.appcompat.app.AppCompatActivity;

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

        for (RouteInfo route : routes) {

            RelativeLayout relativeLayout = new RelativeLayout(this);

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeLayout.setLayoutParams(params);

            ImageView imageView = new ImageView(this);
            ViewGroup.LayoutParams paramsImage = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics()));
            imageView.setAdjustViewBounds(true);
            imageView.setPadding(3, 3, 3, 3);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(getReourceID(route.getPictureFileName()));

            relativeLayout.addView(imageView);

            TextView textViewRouteName = new TextView(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, convertToDp(150), 0, 0);
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
            marginLayoutParams.setMargins(0, convertToDp(180), 0, 0);
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
            layoutParams.setMargins(convertToDp(30), convertToDp(180), 0, 0);
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
            marginLayoutParams.setMargins(0, convertToDp(180), 0, 0);
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
            layoutParams.setMargins(0, convertToDp(180), 0, 0);
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
