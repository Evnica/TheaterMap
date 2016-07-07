package com.evnica.theatermap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import com.evnica.theatermap.fragments.HelloFragment;
import com.evnica.theatermap.fragments.MapTabFragment;
import com.evnica.theatermap.fragments.TheaterListFragment;

public class TabbedActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.mainActivityTabHost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.mainActivityTabFrameLayout);

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Start"),
                HelloFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Auflistung"),
                TheaterListFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Karte"),
                MapTabFragment.class, null);
    }
}
