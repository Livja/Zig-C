package com.noelb.epoka.edu.al.zigc;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GpsOrPhoto extends AppCompatActivity {

    private int id = 0;

    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_or_photo);

        Button btnLocation = (Button) findViewById(R.id.btnLocation);
        Button btnPhoto = (Button) findViewById(R.id.btnPhoto);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        try {
            id = getIntent().getExtras().getInt("id");
        }catch (NullPointerException e){
            e.printStackTrace();
            Log.i("id null", e.toString());
        }
        Log.i("id", id+"");

            btnLocation.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    Intent i = new Intent(GpsOrPhoto.this, GoogleLocationActivity.class);
                    b.putInt("id", id);
                    i.putExtras(b);
                    GpsOrPhoto.this.startActivity(i);
                }
            });






            btnPhoto.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Bundle b = new Bundle();
                    Intent i = new Intent(GpsOrPhoto.this, PhotoIntentActivity.class);
                    b.putInt("id", id);
                    i.putExtras(b);

                    GpsOrPhoto.this.startActivity(i);
                }
            });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Storage permision check")
                        .setMessage("Zig-C would like to be able to write in your memory")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(GpsOrPhoto.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            }
                        })
                        .create()
                        .show();


            }else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }

        }


    }

}
