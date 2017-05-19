package com.example.noelb.zig_cmobile;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Locale;

/**
 * Created by noelb on 5/2/2017.
 */

public class GpsActivity extends AppCompatActivity {


    private Toolbar toolbar;
    public GetCurrentLocation location;
    private Button test;
    private Boolean flag;

    public LocationManager locationManager =null;
    public LocationListener locationListener=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_photo);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        test = (Button) findViewById(R.id.btnLocationUpdates);


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               flag = displayGpsStatus();
                if (flag) {

                    Log.v("GPS", "onClick");



                    locationListener = new MyLocationListener();

                    if (ContextCompat.checkSelfPermission(GpsActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(GpsActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION)) {

                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(GpsActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CONTEXT_INCLUDE_CODE  );

                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    }


                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,locationListener);

                } else {
                    Log.i("GPS", "Your GPS is: OFF");
                }

            }

            private Boolean displayGpsStatus() {
                ContentResolver contentResolver = getBaseContext()
                        .getContentResolver();
                boolean gpsStatus = Settings.Secure
                        .isLocationProviderEnabled(contentResolver,
                                LocationManager.GPS_PROVIDER);
                return gpsStatus;
            }




            });

    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {



            Toast.makeText(getBaseContext(),"Location changed : Lat: " +
                            loc.getLatitude()+ " Lng: " + loc.getLongitude(),
                    Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " +loc.getLongitude();
            Log.v("GPS", longitude);
            String latitude = "Latitude: " +loc.getLatitude();
            Log.v("GPS", latitude);

    /*----------to get City-Name from coordinates ------------- */
            String cityName=null;
            Geocoder gcd = new Geocoder(getBaseContext(),
                    Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(), loc
                        .getLongitude(), 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getLocality());
                cityName=addresses.get(0).getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String s = longitude+"\n"+latitude +
                    "\n\nMy Currrent City is: "+cityName;
            Log.i("GPS", s);

            Double la = loc.getLatitude();
            Double al = loc.getAltitude();
            writeToFile(la.toString()+"\n"+al.toString(), GpsActivity.this);

        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }

    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("gpsData.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}