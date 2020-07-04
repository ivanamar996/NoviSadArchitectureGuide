package com.example.architecturens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
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

public class PlaceInfoActivity extends AppCompatActivity  implements Runnable{

    public static final String PLACE_INFO = "com.example.architecturens.PLACE_INFO";
    private PlaceInfo placeInfo;
    private Uri placeUri;
    private RatingBar ratingBar;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private boolean wasPlaying = false;
    private ImageButton playButton;
    public static boolean wifiConnected;

    @Override
    protected void onResume() {
        super.onResume();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSong();
            }
        });

        final TextView seekBarHint = findViewById(R.id.textView);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                seekBarHint.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                seekBarHint.setVisibility(View.INVISIBLE);
                int x = (int) Math.ceil(progress / 1000f);

                if (x < 10)
                    seekBarHint.setText("0:0" + x);
                else
                    seekBarHint.setText("0:" + x);

                double percent = progress / (double) seekBar.getMax();
                int offset = seekBar.getThumbOffset();
                int seekWidth = seekBar.getWidth();
                int val = (int) Math.round(percent * (seekWidth - 2 * offset));
                int labelWidth = seekBarHint.getWidth();
                seekBarHint.setX(offset + seekBar.getX() + val
                        - Math.round(percent * offset)
                        - Math.round(percent * labelWidth / 2));

                if (progress > 0 && mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    clearMediaPlayer();
                    //fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, android.R.drawable.ic_media_play));
                    PlaceInfoActivity.this.seekBar.setProgress(0);
                }

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });


    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_place_info);

        seekBar= findViewById(R.id.scrubSeekBar);
        ratingBar = findViewById(R.id.rating);
        playButton = findViewById(R.id.playButton);

        Bundle extras = getIntent().getExtras();
        placeUri = (bundle == null) ? null : (Uri) bundle.getParcelable(DBContentProvider.CONTENT_ITEM_PLACE);

        // Or passed from the other activity

        if(extras != null) {
            placeUri = extras.getParcelable(DBContentProvider.CONTENT_ITEM_PLACE);
            placeInfo = DBContentProvider.getPlaceFromSqlite(placeUri);
            getIntentAndDisplayValues();
        }

        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(MainActivity.wifiStateReceiver,intentFilter);


    }



    @Override
    protected void onStart() {
        super.onStart();
        //IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        //registerReceiver(MainActivity.wifiStateReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregisterReceiver(MainActivity.wifiStateReceiver);

        if(mediaPlayer!=null)
            clearMediaPlayer();
    }

    private void getIntentAndDisplayValues() {

        TextView textView = findViewById(R.id.textViewPlaceName);
        textView.setText(placeInfo.getTitle());

        ImageView imageView = findViewById(R.id.placeImageView);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(placeInfo.getImage(),0,placeInfo.getImage().length));


        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_IN);


        ratingBar.setRating((float) placeInfo.getGrade());

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(wifiConnected){
                    double newGrade=(rating+placeInfo.getGrade())/2;
                    updateGrade(newGrade);
                    postGradeToBackend(newGrade);
                }else{
                    Toast.makeText(PlaceInfoActivity.this,"Please, connect to wifi to rate the place.", Toast.LENGTH_SHORT).show();
                }
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
                Toast.makeText(PlaceInfoActivity.this,"Thanks, we have included your grade in the average.", Toast.LENGTH_SHORT).show();
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

    public static void setWifiConnected(boolean connected){
        wifiConnected = connected;
    }

    public void playSong() {

        try {


            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                clearMediaPlayer();
                seekBar.setProgress(0);
                wasPlaying = true;
                //playButton.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, android.R.drawable.ic_media_play));
            }


            if (!wasPlaying) {

                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                }

                //fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, android.R.drawable.ic_media_pause));

                AssetFileDescriptor descriptor = getAssets().openFd("NoviSadGuide.mp3");
                mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                descriptor.close();

                mediaPlayer.prepare();
                mediaPlayer.setVolume(0.5f, 0.5f);
                mediaPlayer.setLooping(false);
                seekBar.setMax(mediaPlayer.getDuration());

                mediaPlayer.start();
                new Thread(this).start();

            }

            wasPlaying = false;
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(MainActivity.wifiStateReceiver);
        if(mediaPlayer!=null)
          clearMediaPlayer();
    }

    private void clearMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void run() {

        int currentPosition = mediaPlayer.getCurrentPosition();
        int total = mediaPlayer.getDuration();


        while (mediaPlayer != null && mediaPlayer.isPlaying() && currentPosition < total) {
            try {
                Thread.sleep(1000);
                currentPosition = mediaPlayer.getCurrentPosition();
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }

            seekBar.setProgress(currentPosition);
        }
    }
}
