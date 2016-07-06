package com.evnica.theatermap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Theater> mTheaters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        new DataGetter().execute();
    }

    private class DataGetter extends AsyncTask<String, Void, ArrayList<Theater>>{

        @Override
        protected ArrayList<Theater> doInBackground(String... params) {
            mTheaters = ContentRequest.retrieveContent();
            return mTheaters;
        }

        @Override
        protected void onPostExecute(ArrayList<Theater> theaters) {
            MarkerOptions marker;
            Theater theater = null;
            for (int i = 0; i < theaters.size(); i++){

                theater = theaters.get(i);

                marker = new MarkerOptions()
                        .position( new LatLng(theater.getmTheaterLatitude(),
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
