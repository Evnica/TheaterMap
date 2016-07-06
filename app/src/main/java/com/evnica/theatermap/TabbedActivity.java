package com.evnica.theatermap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import com.evnica.theatermap.fragments.MapTabFragment;
import com.evnica.theatermap.fragments.TheaterListFragment;

/**
 * Created by Evnica on 06.07.2016.
 */
public class TabbedActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        mTabHost = (FragmentTabHost) findViewById(R.id.mainActivityTabHost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.mainActivityTabFrameLayout);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Auflistung"),
                TheaterListFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Karte"),
                MapTabFragment.class, null);
    }
}
