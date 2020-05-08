package com.example.architecturens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
        ImageView imageView=findViewById(R.id.image1);
        DataManager dm= DataManager.getInstance();
        final RouteInfo ri=dm.getRoute("route1");
        String imgName= ri.getPictureFileName();
        Resources res = getResources();
        String[] parts = imgName.split("/");
        String part1 = parts[0]; // 004
        String part2 = parts[1]; // 034556
        String part = part2;
        String mDrawableName = splitStringByFullstop(part);
        int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
        Drawable drawable = res.getDrawable(resID );
        imageView.setImageDrawable(drawable);
        TextView title= findViewById(R.id.textView1);
        title.setText(ri.getTitle());
        TextView kilometres= findViewById(R.id.textViewKm1);
        kilometres.setText((int) ri.getKilometres() + "km");
        TextView duration = findViewById(R.id.textViewH2);
        duration.setText((double)ri.getDuration() + "h");

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
                public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,PlaceActivity.class);
                intent.putExtra(PlaceActivity.PLACE_INFO, ri);
                startActivity(intent);
            }


            });

        }


    private String splitStringByFullstop(String part) {
        String[] parts1 = part.split("\\.");
        String part12 = parts1[0]; // 004
        String part22 = parts1[1]; // 034556
        return  part12;
    }


}
