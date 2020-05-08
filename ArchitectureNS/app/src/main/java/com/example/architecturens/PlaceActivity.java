package com.example.architecturens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        TextView textView=findViewById(R.id.textView);
        textView.setText(mRouteInfo.getTitle());

    }
}
