package com.example.architecturens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        initializeDisplayContent();
    }

    private  void initializeDisplayContent(){
        initializeImages();
        initializeDuration
    }

    private void initializeImages() {
        ImageView imageView=findViewById(R.id.image1);
        DataManager dm= DataManager.getInstance();
        RouteInfo ri=dm.getRoute("route1");
        String imgName= ri.getPictureFileName();
        Resources res = getResources();
        String[] parts = imgName.split("/");
        String part1 = parts[0]; // 004
        String part2 = parts[1]; // 034556
        String part = part2;
        String mDrawableName = splitString(part);
        int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
        Drawable drawable = res.getDrawable(resID );
        imageView.setImageDrawable(drawable);
    }

    private String splitStringByFullstop(String part) {
        String[] parts1 = part.split("\\.");
        String part12 = parts1[0]; // 004
        String part22 = parts1[1]; // 034556
        return  part12;
    }
}
