package com.evnica.theatermap;


import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.GeoPoint;

import java.util.ArrayList;

public class RouteCalculator {

    private static LatLng start = null, target = null;
    private static GoogleMap map = null;
    private static boolean routeCalculated = false;
    private static Polyline path = null;

    public static void setMap(GoogleMap map) {
        RouteCalculator.map = map;
    }

    public static void setStart(LatLng start) {
        RouteCalculator.start = start;
    }

    public static void setTarget(LatLng target) {
        RouteCalculator.target = target;
    }

    public static LatLng getStart() {
        return start;
    }

    public static LatLng getTarget() {
        return target;
    }

    public static boolean calculateRoute(){
        new RoutingTask().execute();
        return routeCalculated;
    }

    static class RoutingTask extends AsyncTask<String, Void, Route> {

        @Override
        protected Route doInBackground(String... params) {
            routeCalculated = false;
            Route route = null;
            if (start != null && target != null) {
                String routeUrl =
                        "http://maps.google.com/maps/api/directions/json?sensor=true&mode=driving&" +
                                "origin=" + start.latitude + "," + start.longitude +
                                "&destination=" + target.latitude + "," + target.longitude;
                GoogleParser.setUrlToParse(routeUrl);
                route = GoogleParser.parse();
            }
            return route;
        }

        @Override
        protected void onPostExecute(Route route) {
            if (route != null)
            {
                ArrayList<GeoPoint> milestones = (ArrayList<GeoPoint>) route.getPoints();
                PolylineOptions routeOptions = new PolylineOptions();
                GeoPoint start, end;

                for (int i = 0; i < milestones.size() - 2; i++){
                    start = milestones.get(i);
                    end = milestones.get(i+1);

                    routeOptions.add
                            (new LatLng(start.getLatitudeE6()/1E6, start.getLongitudeE6()/1E6),
                             new LatLng(end.getLatitudeE6()/1E6, end.getLongitudeE6()/1E6))
                            .width(8)
                            .color(Color.BLUE);
                }

                if (map != null)
                {
                    if (path != null)
                    {
                        path.remove();
                    }
                    path = map.addPolyline(routeOptions);
                    routeCalculated = true;
                }

            }
        }
    }

}