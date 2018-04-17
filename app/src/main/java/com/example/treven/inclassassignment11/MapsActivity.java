package com.example.treven.inclassassignment11;

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
import com.google.gson.Gson;

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
    double mlatEnd;
    double mlongEND;
    double mlat;
    double mlong;

    ArrayList<LatLng> cordList = new ArrayList<>();

    public  void DrawLine(LatLng location){
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(location)
                .add(new LatLng(mlat, mlong))
                .add(new LatLng(mlatEnd,mlongEND));
        mMap.addPolyline(polylineOptions);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        
        Gson gson = new Gson();
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
            Log.d("test", j.toString());

        } catch (Exception e){
        }


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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng start = new LatLng(cordList.get(0).latitude, cordList.get(0).longitude);
        mMap.addMarker(new MarkerOptions().position(start).title("Start"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(start));

        LatLng end = new LatLng(cordList.get(2).latitude, cordList.get(2).longitude);
        mMap.addMarker(new MarkerOptions().position(end).title("End"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(end));


    }
}

class Cord {
     Double lat, lon;

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public Cord(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
    // standard getters & setters...
}