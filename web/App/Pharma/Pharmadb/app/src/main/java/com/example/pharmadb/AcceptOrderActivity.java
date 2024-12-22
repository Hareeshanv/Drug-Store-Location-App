package com.example.pharmadb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AcceptOrderActivity extends NavigationDrawerBaseActivity {

    ConnectionClass connectionClass = new ConnectionClass();
    TextView tvConfirmation,tvID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_order);


        super.OnCreateDrawer();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Accept Order");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvConfirmation = (TextView) findViewById(R.id.tvConfirmation);
        tvID=(TextView)findViewById(R.id.tvID);

        Intent intent = getIntent();
        if(intent != null) {
            tvID.setText(intent.getExtras().getString("ID"));
        } else {
            tvID.setText("Data not available");
        }


        try {
                    Connection conn = connectionClass.CONN(); //Connection Object

                    if (conn == null) {
                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                    } else {
                        //fetching value from shared preference
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PU", 0);
                        String userid = sharedPreferences.getString("user_id", "");

                        String query2 = "Update tblOrders set MedicalStoreID='" + userid + "',Status='Accepted' " +
                                "where ID='" + tvID.getText().toString() + "'" ;
                        PreparedStatement preparedStatement2 = conn.prepareStatement(query2);
                        preparedStatement2.executeUpdate();

                        //Toast.makeText(getApplicationContext(), "Accepted Successfully. ", Toast.LENGTH_LONG).show();

                       tvConfirmation.setText("Accepted Successfully.");
                    }
                } catch (Exception e) {
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
        startActivity(new Intent(getApplicationContext(),AcceptedOrdersActivity.class));
    }
}