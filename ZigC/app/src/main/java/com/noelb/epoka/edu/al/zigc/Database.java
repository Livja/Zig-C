package com.noelb.epoka.edu.al.zigc;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by noelb on 6/8/2017.
 */

public class Database extends Activity {

    public static final String user = "root";
    public static final String pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database);

        testDB();

    }


    public void testDB(){
        try {
            Log.i("dbs", "ok");

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);


            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.1.5/zig-c",user,pass);
            String result = "Database connection success";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from employees");
            ResultSetMetaData rsmd = rs.getMetaData();
            Log.i("dbs", result);
            while (rs.next()){
                Log.i("dbs", rs.toString());
                result += rsmd.getColumnName(1) + ": " + rs.getString(1) +"\n";
                result += rsmd.getColumnName(2) + ": " + rs.getString(2) +"\n";
                result += rsmd.getColumnName(3) + ": " + rs.getString(3) +"\n";
                result += rsmd.getColumnName(4) + ": " + rs.getString(4) +"\n";
                result += rsmd.getColumnName(5) + ": " + rs.getString(5) +"\n";
                result += rsmd.getColumnName(6) + ": " + rs.getString(6) +"\n";
                result += rsmd.getColumnName(7) + ": " + rs.getString(7) +"\n";
            }
            Log.i("dbs", result+"\n");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.i("dbs", e.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("dbs", "2"+e.toString());

        }
    }

    }

