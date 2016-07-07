package com.evnica.theatermap.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.evnica.theatermap.ContentRequest;
import com.evnica.theatermap.DetailActivity;
import com.evnica.theatermap.Locator;
import com.evnica.theatermap.R;
import com.evnica.theatermap.RouteCalculator;
import com.evnica.theatermap.RoutingErrorActivity;
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
    private boolean mHybridMap = false;
    private Locator mLocator = new Locator();

    public MapTabFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNull();
    }

    private void setUpMapIfNull() {
        if (mMap == null) {
            getMapAsync(this);
            Locator.setGoogleMap(mMap);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setupMap();
        Locator.setGoogleMap(mMap);
    }

    private void setupMap() {

        mMap.getUiSettings().setMapToolbarEnabled(false);
        addMarkers();
    }

    private void addMarkers(){
        ArrayList<Theater> theaters;
        Theater theater = null;
        if ((theaters = TheaterListFragment.getTheaters()) == null) {
            theaters = ContentRequest.retrieveContent();
        }
        for (int i = 0; i < theaters.size(); i++) {

            theater = theaters.get(i);

            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(theater.getmTheaterLatitude(),
                            theater.getmTheaterLongitude()))
                    .title(theater.getmTheaterName())
                    .snippet(theater.getmTheaterLocation())
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.drama_icon));

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
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("name", marker.getTitle());
                intent.putExtra("address", marker.getSnippet());

                double lat = marker.getPosition().latitude;
                double lon = marker.getPosition().longitude;

                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);

                startActivity(intent);
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams bunnonParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        ViewGroup viewGroup = (ViewGroup) view;

        Button mapTypeButton = new Button(getActivity());
        mapTypeButton.setText("Kartenaussehen ändern");
        mapTypeButton.setLayoutParams(bunnonParams);
        mapTypeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!mHybridMap) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    mHybridMap = true;
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mHybridMap = false;
                }
                //MAP_TYPE_NONE;
                //MAP_TYPE_NORMAL;
                //MAP_TYPE_SATELLITE;
                //MAP_TYPE_TERRAIN;
                //MAP_TYPE_HYBRID;
            }
        });

        Button routeButton = new Button(getActivity());
        routeButton.setText("Route berechnen");
        routeButton.setLayoutParams(bunnonParams);
        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RouteCalculator.getStart() != null && RouteCalculator.getTarget() != null){
                      RouteCalculator.setMap(mMap);
                      RouteCalculator.calculateRoute();
                }
                else {
                    startActivity(new Intent(getActivity(), RoutingErrorActivity.class));
                }
            }
        });

        linearLayout.addView(mapTypeButton);
        linearLayout.addView(routeButton);
        viewGroup.addView(linearLayout);
    }


}
