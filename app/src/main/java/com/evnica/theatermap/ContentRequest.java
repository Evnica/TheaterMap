package com.evnica.theatermap;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by: Evnica
 * Date: 04.07.2016.
 * Version: 0.1
 */

public class ContentRequest {

    public static ArrayList<Theater> retrieveContent(){

        String url = "http://nominatim.openstreetmap.org/search?q=theater+vienna&format=json&polygon=0&addressdetails=1";
        System.out.println("Requesting content...");

        ArrayList<Theater> theaters = new ArrayList<>();
        String result = "";
        try {
            InputStream inputStream = new URL(url).openStream();
            result = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next(); //ISO8859-1
        }
        catch (MalformedURLException e)
        {
            Log.e(e.getMessage(), "MalformedURLException during data loading");
        }
        catch (IOException e)
        {
            Log.e(e.getMessage(), "IOException while loading data");
        }

        JSONObject entry;
        double lat, lon;
        String name, location = "";
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++)
            {
                entry = jsonArray.getJSONObject(i);
                JSONObject address = new JSONObject(entry.getString("address"));
                //if there is no theater attribute, the location is not a theater and shouldn't be
                //displayed in the list
                if (address.has("theatre") || address.has("address26"))
                {
                    try {
                        name = address.getString("theatre");
                    }
                    catch (JSONException e) {
                        try {
                            name = address.getString("address26");
                        }
                        catch (JSONException e1) {
                            Log.e(e1.getMessage(), "Apparently this theater has no name");
                            name = "Name unbekannt";
                        }
                    }

                    lat = Double.parseDouble(entry.getString("lat"));
                    lon = Double.parseDouble(entry.getString("lon"));

                    if (address.has("city_district")) {
                        location =  address.getString("city_district");
                    }

                    if (address.has("road")){
                        location = location + ", " + address.getString("road");
                        if (address.has("house_number")){
                            location = location + ", " + address.getString("house_number");
                        }
                    }
                    else if (address.has("pedestrian")){
                        location = location + ", " + address.getString("pedestrian");
                        if (address.has("house_number")){
                            location = location + ", " + address.getString("house_number");
                        }
                    }

                    if (address.has("postcode")) {
                        location = location + ", " + address.getString("postcode") + " Wien";
                    }
                    else {
                        location = location + " Wien";
                    }

                    theaters.add(new Theater(name, location, lon, lat));
                }
            }
        }
        catch (Exception e) {
            Log.e(e.getMessage(), "Exception while JSON parsing. What a pity!");
        }
        return theaters;
    }

}
