package com.evnica.theatermap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class DetailActivity extends Activity {

    double lat, lon;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String nameToSet = getIntent().getStringExtra("name");
        String addressToSet = getIntent().getStringExtra("address");
         lat = getIntent().getDoubleExtra("lat", 0.0);
         lon = getIntent().getDoubleExtra("lon", 0.0);
        String coordsToSet = lat + ", " + lon;  //getIntent().getStringExtra("coords");

        ((TextView)findViewById(R.id.name)).setText(nameToSet);
        ((TextView)findViewById(R.id.address)).setText(addressToSet);
        ((TextView)findViewById(R.id.coords)).setText(coordsToSet);

        Button back = (Button)findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button startButton = (Button)findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteCalculator.setStart(new LatLng(lat, lon));
                finish();
            }
        });

        Button targetButton = (Button)findViewById(R.id.target);
        targetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteCalculator.setTarget(new LatLng(lat, lon));
                finish();
            }
        });
    }
}
