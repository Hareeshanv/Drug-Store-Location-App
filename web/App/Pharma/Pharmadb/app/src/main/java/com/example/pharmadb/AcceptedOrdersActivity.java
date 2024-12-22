package com.example.pharmadb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pharmadb.adapters.acceptedorderAdapters;
import com.example.pharmadb.data_models.acceptedorderDataType;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AcceptedOrdersActivity extends NavigationDrawerBaseActivity {

    ConnectionClass connectionClass = new ConnectionClass();

    RecyclerView recyclerView;
    ResultSet rss;
    ArrayList<acceptedorderDataType> ex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_orders);


        super.OnCreateDrawer();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Orders List");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

       try {
            Connection conn = connectionClass.CONN(); //Connection Object

            if (conn == null) {

                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
            } else {
                //fetching value from shared preference
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PU", 0);
                String user_name = sharedPreferences.getString("user_name", "");
                String user_id = sharedPreferences.getString("user_id", "");

                String query = "SELECT * From tblOrders where MedicalStoreID='" + user_id + "'";

                PreparedStatement stmt = conn.prepareStatement(query);

                acceptedorderDataType dt = new acceptedorderDataType();

              rss = stmt.executeQuery();
                ex = new ArrayList<>();
                while(rss.next()) {
                    //Log.d("ResultSet", rs.getString("ID"));

                    dt.setID(rss.getString("ID").toString());
                    dt.setOrderDate(rss.getString("OrderDate").toString());
                    dt.setUserID(rss.getString("UserID").toString());
                    dt.setStatus(rss.getString("Status").toString());

                   String BillAmount = rss.getString("BillAmount");
                    if (rss.wasNull()) {
                        dt.setBillAmount("Not Billed");
                    }
                    else
                        dt.setBillAmount(rss.getString("BillAmount").toString());

                    ex.add(dt);
                } 
                acceptedorderAdapters eadapters = new acceptedorderAdapters(getApplicationContext(),ex);
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

    @Override
    public void onBackPressed() {
        // do something on back.
        finish();
        startActivity(new Intent(getApplicationContext(),HomePage.class));
    }
}