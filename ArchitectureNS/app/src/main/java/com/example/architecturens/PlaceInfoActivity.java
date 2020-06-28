package com.example.architecturens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.NetworkConnection.DBContentProvider;
import com.example.NetworkConnection.DbConnection;
import com.example.NetworkConnection.RouteSQLiteHelper;
import com.example.service.GetDataService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceInfoActivity extends AppCompatActivity {

    public static final String PLACE_INFO = "com.example.architecturens.PLACE_INFO";
    private PlaceInfo placeInfo;
    private Uri placeUri;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_place_info);

        Bundle extras = getIntent().getExtras();
        placeUri = (bundle == null) ? null : (Uri) bundle.getParcelable(DBContentProvider.CONTENT_ITEM_PLACE);

        // Or passed from the other activity

        if(extras != null) {
            placeUri = extras.getParcelable(DBContentProvider.CONTENT_ITEM_PLACE);
            placeInfo = DBContentProvider.getPlaceFromSqlite(placeUri);
            getIntentAndDisplayValues();
        }


    }

    private void getIntentAndDisplayValues() {

        TextView textView = findViewById(R.id.textViewPlaceName);
        textView.setText(placeInfo.getTitle());

        ImageView imageView = findViewById(R.id.placeImageView);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(placeInfo.getImage(),0,placeInfo.getImage().length));

        SeekBar seekBar = findViewById(R.id.scrubSeekBar);
        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_IN);

        ratingBar = (RatingBar) findViewById(R.id.rating);
        ratingBar.setRating((float) placeInfo.getGrade());
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                double newGrade=(rating+placeInfo.getGrade())/2;
                updateGrade(newGrade);
                postGradeToBackend(newGrade);
            }
        });

        TextView textViewDesc = findViewById(R.id.placeDescription);
        textViewDesc.setText(placeInfo.getDescription());


    }

    private void postGradeToBackend(double grade){
        placeInfo.setGrade(grade);
        final GetDataService service = DbConnection.getRetrofitInstance().create(GetDataService.class);

        Call<String> call = service.postGrade(placeInfo.getId(),placeInfo.getGrade());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void updateGrade(double grade){
        //update sqlite
        //post na back za update back
        ContentValues contentValues=new ContentValues();
        contentValues.put(RouteSQLiteHelper.COLUMN_PLACE_GRADE,grade);
        getContentResolver().update((Uri)DBContentProvider.CONTENT_URI_PLACE, contentValues, "place_info_id="+placeInfo.getId(), null);

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
