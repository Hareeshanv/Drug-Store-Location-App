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
import java.sql.ResultSet;

public class InfoActivity extends NavigationDrawerBaseActivity {

    ConnectionClass connectionClass = new ConnectionClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        super.OnCreateDrawer();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Deliver Boy Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tv_Name=(TextView)findViewById(R.id.tv_Name);
        TextView tv_AddressLine1=(TextView)findViewById(R.id.tv_AddressLine1);
        TextView tv_AddressLine2=(TextView)findViewById(R.id.tv_AddressLine2);
        TextView tv_City=(TextView)findViewById(R.id.tv_City);
        TextView tv_Mobile=(TextView)findViewById(R.id.tv_Mobile);
        TextView tv_EmailID=(TextView)findViewById(R.id.tv_EmailID);


        //fetching value from shared preference
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PU", 0);
        tv_Name.setText(sharedPreferences.getString("user_name", ""));
        tv_Mobile.setText(sharedPreferences.getString("user_id", ""));

        try {
            Connection conn = connectionClass.CONN(); //Connection Object

            if (conn == null) {

                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
            } else {
                String query1 = "Select * from tblMedicalStore where Mobile='" + tv_Mobile.getText().toString() + "'";
                PreparedStatement preparedStatement2 = conn.prepareStatement(query1);
                ResultSet rs = preparedStatement2.executeQuery();
                if (rs.next()) {
                    tv_AddressLine1.setText(rs.getString("AddressLine1").toString());
                    tv_AddressLine2.setText(rs.getString("AddressLine2").toString());
                    tv_City.setText(rs.getString("City").toString());
                    tv_Mobile.setText(rs.getString("Mobile").toString());
                    tv_EmailID.setText(rs.getString("EmailID").toString());
                }
            }
        }
        catch (Exception e)
        {
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