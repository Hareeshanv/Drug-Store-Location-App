package com.example.pharmadb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pharmadb.adapters.neworderAdapters;
import com.example.pharmadb.data_models.neworderDataType;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class NewOrderListActivity extends NavigationDrawerBaseActivity {

    ConnectionClass connectionClass = new ConnectionClass();

    RecyclerView recyclerView;
    ResultSet rss;
    ArrayList<neworderDataType> ex;

    Button btnFetchLocation,btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_list);


        super.OnCreateDrawer();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Centre List");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        btnSearch=(Button)findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String City=null;
                String LatAd=null;
                String LongAd=null;

                try {
                    Connection conn = connectionClass.CONN(); //Connection Object

                    if (conn == null) {

                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                    } else {
                        String user_id = null;

                        //fetching value from shared preference
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PU", 0);
                        user_id = sharedPreferences.getString("user_id", "");

                        String query1 = "Select * from tblMedicalStore where Mobile='" + user_id + "'";
                        PreparedStatement preparedStatement2 = conn.prepareStatement(query1);
                        ResultSet rs = preparedStatement2.executeQuery();
                        if (rs.next()) {
                            City = rs.getString("City").toString();
                            LatAd = rs.getString("LatAd").toString();
                            LongAd = rs.getString("LongAd").toString();
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Writer writer = new StringWriter();
                    e.printStackTrace(new PrintWriter(writer));

                    Toast.makeText(getApplicationContext(), writer.toString(), Toast.LENGTH_LONG).show();
                }

                try {
                    Connection conn = connectionClass.CONN(); //Connection Object

                    if (conn == null) {

                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                    } else
                    {
                        String query = "SELECT o.ID, o.UserID, ROUND(6371 * ACOS(COS(RADIANS('" + LatAd + "')) * COS(RADIANS(u.LatAd)) * COS(RADIANS(u.LongAd) - RADIANS('" + LongAd + "')) + SIN(RADIANS('" + LatAd + "')) * SIN(RADIANS(u.LatAd))), 2) AS distance  " +
                                "FROM     tblOrders o,tblUser u " +
                                "WHERE  o.UserID=u.Mobile and u.City='" + City + "' and Status='New' " +
                                "ORDER BY distance";

                        /* String query="SELECT ID, Hospital, distance " +
                                "FROM     (SELECT TOP (100) PERCENT ID, Hospital, ROUND(6371 * ACOS(COS(RADIANS('" + txtLatitude.getText().toString() + "')) * COS(RADIANS(LatAd)) * COS(RADIANS(LongAd) - RADIANS('" + txtLongitude.getText().toString() + "')) + SIN(RADIANS('" + txtLatitude.getText().toString() + "')) * SIN(RADIANS(LatAd))), 2) AS distance " +
                                "                  FROM      tblHospitals " +
                                "                  ORDER BY distance) AS vt " +
                                "WHERE  (distance < 15) " +
                                "ORDER BY distance"; */

                        PreparedStatement stmt = conn.prepareStatement(query);
                        rss = stmt.executeQuery();
                        ex = new ArrayList<>();
                        while(rss.next()) {
                            //Log.d("ResultSet", rs.getString("ID"));
                            neworderDataType dt = new neworderDataType();
                            dt.setID(rss.getString("ID").toString());
                            dt.setUserID(rss.getString("UserID").toString());
                            dt.setDistance(rss.getString("distance").toString());
                            ex.add(dt);
                        }
                        neworderAdapters eadapters = new neworderAdapters(getApplicationContext(),ex);
                        recyclerView.setAdapter(eadapters);
                        //ExcersisesAdapters edadapters=new ExcersisesAdapters(getApplicationContext(),ex);
                        //recyclerView.setAdapter(edadapters);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Writer writer = new StringWriter();
                    e.printStackTrace(new PrintWriter(writer));

                    Toast.makeText(getApplicationContext(), writer.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        // do something on back.
        finish();
        startActivity(new Intent(getApplicationContext(),HomePage.class));
    }
}