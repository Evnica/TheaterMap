package com.evnica.theatermap.fragments;

import android.util.Log;

import com.evnica.theatermap.R;
import com.evnica.theatermap.Theater;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapTabFragment extends SupportMapFragment implements
        OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;

    public MapTabFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("MyMap", "onResume");
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        if (mMap == null) {

            Log.d("MyMap", "setUpMapIfNeeded");

            getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MyMap", "onMapReady");
        mMap = googleMap;
        setUpMap();
    }

    private void setUpMap() {

        //mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        ArrayList<Theater> theaters;
        Theater theater = null;
        if ((theaters = TheaterListFragment.getTheaters()) != null)
        {
            for (int i = 0; i < theaters.size(); i++) {

                theater = theaters.get(i);

                MarkerOptions marker = new MarkerOptions()
                        .position(new LatLng(theater.getmTheaterLatitude(),
                                theater.getmTheaterLongitude()))
                        .title(theater.getmTheaterName()) //set the title of the Marker
                        .snippet(theater.getmTheaterLocation()) //set the description of the marker
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.drama_icon)); //set a custom Icon of the marker

                mMap.addMarker(marker);
            }

            if (theater != null) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(theater.getmTheaterLatitude(),
                                theater.getmTheaterLongitude()))
                        .zoom(15).build();

                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
            }
        }

    }
}
