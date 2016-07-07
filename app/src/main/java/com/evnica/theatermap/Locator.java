package com.evnica.theatermap;

import android.location.Location;

import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Locator implements LocationListener {

    private static GoogleMap googleMap;
    public static int accessFineLocationPermission;
    public static int accessCoarseLocationPermission;

    public static void setGoogleMap(GoogleMap googleMap) {
        Locator.googleMap = googleMap;
    }

    @Override
    public void onLocationChanged(Location location) {
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .title("Ich bin da");
        googleMap.addMarker(marker);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
