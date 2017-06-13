package com.noelb.epoka.edu.al.zigc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class FolderActivity extends AppCompatActivity {


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

    private MainActivity mainActivity;
    private GridView gv;

    private static final String HOST = "stud-proj.epoka.edu.al";

    int cnt = 0;
    int idF;

    private String[] fileName;
    private String region;

    private int[] imageView;

    ImageView fImage;
    TextView fName;
    TextView fId;

    List<String> allFiles = new ArrayList<String>();
    List<Integer> allFilesId = new ArrayList<Integer>();
    List<Integer> allFilesLevel = new ArrayList<Integer>();

    Integer fileId[];
//    Integer file[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folder_activity);

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

        gv = (GridView) findViewById(R.id.gridViewFolder);




        Intent i = getIntent();
        region = getIntent().getExtras().getString("region");

        Log.i("dbs", region+"");

        try {
            readDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void readDB() throws SQLException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {

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
            ResultSet rs = st.executeQuery("select * from file where region = '"+region+"' and level < 3");
            ResultSetMetaData rsmd = rs.getMetaData();
            Log.i("dbs rs size fetch", rs.getFetchSize() + "");
//            Log.i("dbs", rsmd.getColumnDisplaySize()+"");

            while (rs.next()) {

                allFilesId.add(rs.getInt("id"));
                allFiles.add(rs.getString("Filename"));
//                allFilesLevel.add(rs.getInt("level"));

                Log.i("dbs while", "\n");
//                fileName[cnt] = result;
                cnt++;
                Log.i("dbs cnt", cnt+"");
            }
            fileName = allFiles.toArray(new String[allFiles.size()]);

            fileId = allFilesId.toArray(new Integer[allFilesId.size()]);

            populateGridView(fileName.length);

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

    public void populateGridView(int length) {

        imageView = new int[length];
        for (int j = 0; j < imageView.length; j++) {
            imageView[j] = R.drawable.file_white;
        }
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();


        for (int i = 0; i < length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("imageView", Integer.toString(imageView[i]));
            hm.put("title", fileName[i]);
            hm.put("id", Integer.toString(fileId[i]));
            aList.add(hm);
        }
        Log.i("gridview alist", aList.toString());
        // Keys used in Hashmap
        String[] from = {"imageView", "title", "id"};

        fImage = (ImageView) findViewById(R.id.fileImage);
        fName = (TextView) findViewById(R.id.fileName);
        fId = (TextView) findViewById(R.id.fId);

        // Ids of views in listview_layout
        int[] to = {R.id.fileImage, R.id.fileName, R.id.fId};

        Log.i("gridview", "gridpopulated" + " " + to[0] + " " + to[1] + " " + to[2]);


        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        final SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.folder_item, from, to);
        // Setting an adapter containing images to the gridview
        gv.setAdapter(adapter);

        if (isOnline()) {

            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Bundle b = new Bundle();
                    Intent i = new Intent(FolderActivity.this, GpsOrPhoto.class);
                    b.putInt("id", fileId[position]);
                    i.putExtras(b);
                    startActivity(i);
                    Log.i("grid view", position + " " + fileId[position]);

                }}
            );

        }else {
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Toast.makeText(getBaseContext(), "Your device does not have an active internet connection. Please continue offline", Toast.LENGTH_LONG).show();


                }}
            );


        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
