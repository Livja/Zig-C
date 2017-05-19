package com.example.noelb.zig_cmobile;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import static android.R.color.white;


public class MainActivity extends AppCompatActivity {

    public static MainActivity instance;

    public static Display display;

    private EditText username;
    private EditText password;
    private TextView error;
    private TextView or;
    private ImageButton loginButton;
    private Button offlineButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        instance = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("");
        } catch (NullPointerException npe) {
            Log.i("TAG","Action bar is null");
        }

        username = (EditText) findViewById(R.id.usernameText);
        password = (EditText) findViewById(R.id.passwordText);
        error = (TextView) findViewById(R.id.errorTextView);
        or = (TextView) findViewById(R.id.orTextView);
        loginButton = (ImageButton) findViewById(R.id.loginButton);
        offlineButton = (Button) findViewById(R.id.offlineButton);

        username.getBackground().setColorFilter(getResources().getColor(white), PorterDuff.Mode.SRC_IN);
        password.getBackground().setColorFilter(getResources().getColor(white), PorterDuff.Mode.SRC_IN);


        offlineButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, GoogleLocationActivity.class);

                MainActivity.this.startActivity(i);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error.setVisibility(View.VISIBLE);

//                error.setText(username.getText().toString()+" "+password.getText().toString());

                if(username.getText().toString().equals("noel") && password.getText().toString().equals("noel")){
                    Log.i("Noel", username.getText().toString());

                    error.setVisibility(View.INVISIBLE);
                    Intent i = new Intent(MainActivity.this, GetCurrentLocation.class);
                    MainActivity.this.startActivity(i);
                }else{
                    error.setText("Please enter your user data");
                }
            }
        });
    }

}
