package com.example.treven.inclassassignment11;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.InputStream;
import java.lang.reflect.Array;
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LatLng start;
    LatLng end;

    ArrayList<LatLng> cordList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





    }

    private String readJsonFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte bufferByte[] = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(bufferByte)) != -1) {
                outputStream.write(bufferByte, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        ArrayList<LatLng> cordList = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.trip);
        String jsonString = readJsonFile(inputStream);
        //Object response = gson.fromJson(jsonString, Object.class);
        try{
            JSONObject j = new JSONObject(jsonString);
            JSONArray ll = j.getJSONArray("points");
            for(int i =0; i< ll.length(); i++){
                double lat = ((JSONObject) ll.get(i)).getDouble("latitude");
                double longitude = ((JSONObject) ll.get(i)).getDouble("longitude");
                cordList.add(new LatLng(lat, longitude));




            }
            Log.d("test", String.valueOf(cordList.size()));

        } catch (Exception e){
        }


        start = new LatLng(cordList.get(0).latitude, cordList.get(0).longitude);
        end = new LatLng(cordList.get(cordList.size() - 1).latitude, cordList.get(cordList.size() - 1).longitude);
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(start).title("Start"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(start));


        mMap.addMarker(new MarkerOptions().position(end).title("End"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(end));
        Log.d("test", String.valueOf(cordList.size()));

        for (int g = 0; g < cordList.size() - 1; g++) {
            LatLng src = cordList.get(g);
            LatLng dest = cordList.get(g + 1);


            Log.d("test", String.valueOf(src.latitude));

            Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(
                            new LatLng(src.latitude, src.longitude),
                            new LatLng(dest.latitude, dest.longitude)));

            PolylineOptions rectOptions = new PolylineOptions()
                    .add(new LatLng(src.latitude, src.longitude))
                    .add(new LatLng(dest.latitude,dest.longitude))
                    .width(5).color(Color.BLUE).geodesic(true);
            Polyline polyline = mMap.addPolyline(rectOptions);
        }

        // Add a marker in Sydney and move the camera


        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));
        LatLng zoom = new LatLng(cordList.get(0).latitude, cordList.get(0).longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(zoom, 10));


    }
}
