package com.example.architecturens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlaceInfoActivity extends AppCompatActivity {

    public static final String PLACE_INFO = "com.example.architecturens.PLACE_INFO";
    private PlaceInfo placeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);
        getIntentAndDisplayValues();
    }

    private void getIntentAndDisplayValues() {

        Intent intent = getIntent();
        placeInfo = intent.getParcelableExtra(PLACE_INFO);

        TextView textView = findViewById(R.id.textViewPlaceName);
        textView.setText(placeInfo.getPlaceTitle());

        ImageView imageView = findViewById(R.id.placeImageView);
        imageView.setImageResource(getResourceID(placeInfo.getPictureFileName()));

        SeekBar seekBar = findViewById(R.id.scrubSeekBar);
        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_IN);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.rating);
        Drawable drawable = ratingBar.getProgressDrawable();
        drawable.setColorFilter(getResources().getColor(R.color.gold),PorterDuff.Mode.SRC_ATOP);

        TextView textViewDesc = findViewById(R.id.placeDescription);
        textViewDesc.setText(placeInfo.getPlaceDescription());


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

}
