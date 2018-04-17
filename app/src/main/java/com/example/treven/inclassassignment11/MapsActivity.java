package com.example.treven.inclassassignment11;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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

        InputStream is = getResources().openRawResource(R.raw.trip);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng start = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(start).title("Start"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(start));

        LatLng end = new LatLng(-100, 151);
        mMap.addMarker(new MarkerOptions().position(end).title("End"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(end));


    }
}
