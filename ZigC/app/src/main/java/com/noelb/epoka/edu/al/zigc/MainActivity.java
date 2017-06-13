package com.noelb.epoka.edu.al.zigc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static android.R.color.white;


public class MainActivity extends AppCompatActivity {

    Connection connection = null;
    Session session= null;

    String host = "stud-proj.epoka.edu.al";
    String servUser = "nboci14";
    String servPwd = "nb536oel";
    int port = 22;

    String rhost = "localhost";
    int rport = 3306;
    int lport = 1234;

    String driverName = "com.mysql.jdbc.Driver";
    String db2Url = "jdbc:mysql://localhost:" + lport + "/web16_nboci14";
    String dbUsr = "nboci14";
    String dbPwd = "nb536oel";

    public static MainActivity instance;

    public static Display display;

    private EditText username;
    private EditText password;
    private TextView error;

    private ImageButton loginButton;
    private Button offlineButton;

    private String uname;
    private String pass;
    private String region;
    private String department;
    private int level;
    public Database database;

    private static final String HOST = "localhost";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint("WifiManagerLeak")
        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        final String ipAddress = Formatter.formatIpAddress(ip);

        Log.i("dbs ip", ipAddress);

        instance = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i("internet", isOnline() + "");

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("");
        } catch (NullPointerException npe) {
            Log.i("TAG", "Action bar is null");
        }

        username = (EditText) findViewById(R.id.usernameText);
        password = (EditText) findViewById(R.id.passwordText);
        error = (TextView) findViewById(R.id.errorTextView);
        loginButton = (ImageButton) findViewById(R.id.loginButton);
        offlineButton = (Button) findViewById(R.id.offlineButton);

        username.getBackground().setColorFilter(getResources().getColor(white), PorterDuff.Mode.SRC_IN);
        password.getBackground().setColorFilter(getResources().getColor(white), PorterDuff.Mode.SRC_IN);

        if (isOnline()) {

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    error.setText("");
                    error.setEnabled(true);
                    error.setVisibility(View.VISIBLE);

                    try {
                        testDB(username.getText().toString());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (level == 1) {
                        Log.i("dbs", uname + " " + pass);
                        if (username.getText().toString().equals(uname) && password.getText().toString().equals(pass)) {
                            error.setVisibility(View.INVISIBLE);
                            hideSoftKeyboard(instance);
                            Toast.makeText(getBaseContext(), "Files are being uploaded", Toast.LENGTH_LONG).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Bundle b = new Bundle();
                                    Intent i = new Intent(MainActivity.this, FolderActivity.class);
                                    Log.i("dbs intent", region);
                                    b.putString("region", region);
                                    i.putExtras(b);
                                    MainActivity.this.startActivity(i);
                                }
                            }, 1000);

                        } else {
                            error.setText("Please enter your user data ");
                        }
                    } else {
                        error.setText("Only regional coordinators can enter the mobile app");
                    }
                }
            });

        }else {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    error.setEnabled(false);
                    Toast.makeText(getBaseContext(), "Your device does not have an active internet connection. Please continue offline", Toast.LENGTH_LONG).show();
                }});

        }
        offlineButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, GpsOrPhoto.class);

                MainActivity.this.startActivity(i);
            }
        });

    }

    public void testDB(String user) throws SQLException {
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            JSch jsch = new JSch();
            // Get SSH session
            session = jsch.getSession(servUser, host, port);
            session.setPassword(servPwd);
            Properties config = new Properties();
            // Never automatically add new host keys to the host file
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            // Connect to remote server
            session.connect();
            // Apply the port forwarding
            session.setPortForwardingL(lport, rhost, rport);
            // Connect to remote database
            Class.forName(driverName);

            connection = DriverManager.getConnection(db2Url, dbUsr, dbPwd);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from employees where username =  '"+user+"'");
            while (rs.next()) {
                uname = rs.getString("username");
                pass = rs.getString("password");
                department +=rs.getString("department");
                level = rs.getInt("level");
                region = rs.getString("region");
            }
            Log.i("dbs ", region);
            connection.close();
        }catch(Exception e){ System.out.println(e);
        }finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }


    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
