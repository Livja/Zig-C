package com.noelb.epoka.edu.al.zigc;

/**
 * Created by noelb on 5/29/2017.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button back;
    public Double lat;
    public Double lon;

    public double[] latArray;
    public double[] lonArray;

    public int index;

    private GoogleLocationActivity gla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        back = (Button) findViewById(R.id.btnGoBack);


        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setTitle("");

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(MapsActivity.this, GoogleLocationActivity.class);

                MapsActivity.this.startActivity(i);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

            Bundle bundle = getIntent().getExtras();

        SharedPreferences bb = getSharedPreferences("index", 0);
        int m = bb.getInt("size", 0);
        Toast.makeText(this, "The size of the array is: "+m, Toast.LENGTH_SHORT).show();
        Log.i("gps index no intent", m+" "+index);

        latArray = new double[m];
        lonArray = new double[m];


            latArray = bundle.getDoubleArray("latArray");
            lonArray = bundle.getDoubleArray("lonArray");


        Log.i("gps", bundle.getDouble("dLat")+"");


        for (int i = 0; i < latArray.length; i++){

            double d1 = latArray[i];
            double d2 = lonArray[i];

            createMarker(d1, d2,"marker at index "+i);

//            setMarker(latArray[i], lonArray[i], i);
            Log.i("gps latArray Data",latArray[i]+" "+lonArray[i]+" index "+i);
        }

//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 10.0f));
    }





    public void setMarker(Double lat, Double lon, int index){

        LatLng marker = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(marker).title("Marker in current location" +index));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));

    }

    protected Marker createMarker(double latitude, double longitude, String title) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title));
    }

    }
