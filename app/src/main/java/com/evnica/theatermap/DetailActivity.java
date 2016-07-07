package com.evnica.theatermap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String nameToSet = getIntent().getStringExtra("name");
        String addressToSet = getIntent().getStringExtra("address");
        String coordsToSet = getIntent().getStringExtra("coords");

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
    }
}
