package com.evnica.theatermap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class RoutingErrorActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routingerror);

        /*TextView errorTitle = (TextView) findViewById(R.id.error);
        errorTitle.setText(Html.fromHtml(getString(R.string.error)));

        TextView error = (TextView) findViewById(R.id.errorDetail);
        error.setText(Html.fromHtml(getString(R.string.errorDetail)));*/

        Button back = (Button)findViewById(R.id.backErrorButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
