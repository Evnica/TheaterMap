package com.evnica.theatermap;

import android.location.Location;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Locator implements LocationListener {

    private static GoogleMap googleMap;

    public static void setGoogleMap(GoogleMap googleMap) {
        Locator.googleMap = googleMap;
    }

    @Override
    public void onLocationChanged(Location location) {
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .title("This is where i currently am");
        googleMap.addMarker(marker);
    }



}
