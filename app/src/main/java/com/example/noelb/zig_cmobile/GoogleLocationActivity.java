package com.example.noelb.zig_cmobile;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GoogleLocationActivity extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener {
    // LogCat tag
    private static final String TAG = MainActivity.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters

    // UI elements
    private TextView lblLocation;
    private Button btnShowLocation, btnStartLocationUpdates;

    private GoogleMap googleMap;

    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_photo);

        lblLocation = (TextView) findViewById(R.id.lblLocation);
        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
        btnStartLocationUpdates = (Button) findViewById(R.id.btnLocationUpdates);

        // First we need to check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }

        // First we need to check availability of play services
        if (checkPlayServices()) {

            createLocationRequest();
        }

        // Show location button click listener
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.i("GPS","show location");
                    displayLocation();

            }
        });
        // Toggling the periodic location updates
        btnStartLocationUpdates.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("GPS","periodic location updates");
                togglePeriodicLocationUpdates();
            }
        });

    }

    /**
     * Creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Method to display the location on UI
     */
    private void displayLocation() {
        Log.i("GPS", "ok");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {

            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            lblLocation.setText(latitude + ", " + longitude);
            Log.i("GPS", lblLocation.getText().toString());


        } else {

            lblLocation.setText("(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }

    /**
     * Method to toggle periodic location updates
     */
    private void togglePeriodicLocationUpdates() {
        Log.i("GPS", "update");
        if (!mRequestingLocationUpdates) {
            // Changing the button text
            btnStartLocationUpdates
                    .setText(getString(R.string.btn_stop_location_updates));

            mRequestingLocationUpdates = true;

            // Starting the location updates
            startLocationUpdates();
            Log.i("GPS", lblLocation.getText().toString());
            Log.d("GPS", "Periodic location updates started!");

        } else {
            // Changing the button text
            btnStartLocationUpdates
                    .setText(getString(R.string.btn_start_location_updates));

            mRequestingLocationUpdates = false;

            // Stopping the location updates
            stopLocationUpdates();

            Log.d(TAG, "Periodic location updates stopped!");
        }
    }

    /**
     * Creating location request object
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT); // 10 meters
    }

    /**
     * Starting the location updates
     */
    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (LocationListener) this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();

        // Resuming the periodic location updates
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
            displayLocation();

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;

        Toast.makeText(getApplicationContext(), "Location changed!",
                Toast.LENGTH_SHORT).show();

        // Displaying the new location on UI

            displayLocation();

    }

    private void writeToFile(String data, Context context) throws IOException {
        File root = context.getFilesDir();
        Log.i("file", root.toString());
        Log.i("file", String.valueOf(root.toURI()));
        File gpxfile = new File(root, "gps.txt");
        FileWriter writer = new FileWriter(gpxfile);
        Log.i("file", data);
        writer.append(data+"\n");
        writer.flush();
        writer.close();
    }


}