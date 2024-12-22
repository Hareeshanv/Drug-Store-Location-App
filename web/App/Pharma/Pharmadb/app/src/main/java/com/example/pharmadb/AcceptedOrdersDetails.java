package com.example.pharmadb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharmadb.locations.GpsTracker1;

import java.io.IOError;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AcceptedOrdersDetails extends NavigationDrawerBaseActivity {

    ImageView imageView;
    ProgressBar progressBar;
    Button btnDownload;
    TextView errorMsg;
    TextView tvID;

    ConnectionClass connectionClass = new ConnectionClass();

    TextView tvUserName,tvAddressLine1,tvAddressLine2,tvCity,tvMobile;
    EditText etBillAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_orders_details);

        super.OnCreateDrawer();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Centre Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvID=(TextView)findViewById(R.id.tvID);

        tvID=(TextView)findViewById(R.id.tvID);
        tvUserName=(TextView)findViewById(R.id.tvUserName);
        tvAddressLine1=(TextView)findViewById(R.id.tvAddressLine1);
        tvAddressLine2=(TextView)findViewById(R.id.tvAddressLine2);
        tvCity=(TextView)findViewById(R.id.tvCity);
        tvMobile=(TextView)findViewById(R.id.tvMobile);

        etBillAmount=(EditText)findViewById(R.id.etBillAmount);

        Intent intent = getIntent();
        if(intent != null) {
            tvID.setText(intent.getExtras().getString("ID"));
        } else {
            tvID.setText("Data not available");
        }

        imageView = (ImageView) findViewById(R.id.imageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        errorMsg = (TextView) findViewById(R.id.errorMsg);
        btnDownload = (Button) findViewById(R.id.button);

        progressBar.setVisibility(View.GONE);


        try {
            Connection conn = connectionClass.CONN(); //Connection Object

            if (conn == null) {

                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
            } else {

                String query1 = "Select * from tblOrders where ID='" + tvID.getText().toString() + "'";
                PreparedStatement preparedStatement2 = conn.prepareStatement(query1);
                ResultSet rs = preparedStatement2.executeQuery();
                if (rs.next()) {
                    tvMobile.setText(rs.getString("UserID").toString());
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


        try {
            Connection conn = connectionClass.CONN(); //Connection Object

            if (conn == null) {

                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
            } else {

                String query1 = "Select * from tblUser where Mobile='" + tvMobile.getText().toString() + "'";
                PreparedStatement preparedStatement2 = conn.prepareStatement(query1);
                ResultSet rs = preparedStatement2.executeQuery();
                if (rs.next()) {
                    tvUserName.setText(rs.getString("UserName").toString());
                    tvAddressLine1.setText(rs.getString("AddressLine1").toString());
                    tvAddressLine2.setText(rs.getString("AddressLine2").toString());
                    tvCity.setText(rs.getString("City").toString());
                    tvMobile.setText(rs.getString("Mobile").toString());
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

    public void orderUpdate(View view)
    {

        try {
            Connection conn = connectionClass.CONN(); //Connection Object

            if (conn == null) {

                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
            } else {

                String query2 = "Update tblOrders set BillAmount='" + etBillAmount.getText().toString() + "'," +
                        "Status='Delivered' where ID='" + tvID.getText().toString() + "'";

                PreparedStatement preparedStatement2 = conn.prepareStatement(query2);
                preparedStatement2.executeUpdate();

                Toast.makeText(getApplicationContext(), "Updated Successfully.", Toast.LENGTH_LONG).show();

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

    public void downloadImage(View view)
    {
        AcceptedOrdersDetails.DownloadImage doin = new AcceptedOrdersDetails.DownloadImage();
        doin.execute();
    }

    private class DownloadImage extends AsyncTask<String, Void, String>
    {
        String image="";
        String msg =  "";
        ResultSet rs;

        @Override
        protected void onPreExecute()
        {
            errorMsg.setText("Downloading Please Wait...");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params)
        {
            String msg =  "";
            try
            {
                Connection conn = connectionClass.CONN(); //Connection Object
                String query= "SELECT Image from tblOrders";
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                if(rs.next())
                {
                    image = rs.getString("Image");
                    msg = "Retrieved Successfully";
                }
                else
                {
                    msg = "Image not Found in the Database";
                }
            }

            catch (SQLException ex)
            {
                msg = ex.getMessage().toString();
                Log.d("seotoolzz", msg);
            }
            catch (IOError ex)
            {

                msg = ex.getMessage().toString();
                Log.d("seotoolzz", msg);
            }
            catch (AndroidRuntimeException ex)
            {
                msg = ex.getMessage().toString();
                Log.d("seotoolzz", msg);
            }
            catch (NullPointerException ex)
            {
                msg = ex.getMessage().toString();
                Log.d("seotoolzz", msg);
            }
            catch (Exception ex)
            {
                msg = ex.getMessage().toString();
                Log.d("seotoolzz", msg);
            }
            return image;
        }

        @Override
        protected void onPostExecute(String resultSet)
        {

            progressBar.setVisibility(View.GONE);
            errorMsg.setText(msg);

            if(resultSet.matches(""))
            {

            }
            else
            {
                byte[] decodeString = Base64.decode(resultSet, Base64.DEFAULT);
                Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
                imageView.setImageBitmap(decodebitmap);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // do something on back.
        finish();
        startActivity(new Intent(getApplicationContext(),AcceptedOrdersActivity.class));
    }
}