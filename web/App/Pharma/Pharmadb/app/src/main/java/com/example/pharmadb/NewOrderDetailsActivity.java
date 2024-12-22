package com.example.pharmadb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class NewOrderDetailsActivity extends NavigationDrawerBaseActivity {


    ImageView imageView;
    ProgressBar progressBar;
    Button btnDownload;
    TextView errorMsg;
    TextView tvID;

    ConnectionClass connectionClass = new ConnectionClass();

    TextView tvUserName,tvAddressLine1,tvAddressLine2,tvCity,tvMobile,tvLatAd,tvLongAd;
    TextView tvUserLatAd,tvUserLongAd;

    Button btnFetchLocation,btnViewLocation;

    GpsTracker1 gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_details);

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
        tvLatAd=(TextView)findViewById(R.id.tvLatAd);
        tvLongAd=(TextView)findViewById(R.id.tvLongAd);

        tvUserLatAd=(TextView)findViewById(R.id.tvUserLatAd);
        tvUserLongAd=(TextView)findViewById(R.id.tvUserLongAd);

        btnFetchLocation=(Button)findViewById(R.id.btnFetchLocation);
        btnViewLocation=(Button)findViewById(R.id.btnViewLocation);

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
                    tvLatAd.setText(rs.getString("LatAd").toString());
                    tvLongAd.setText(rs.getString("LongAd").toString());
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

        btnFetchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchLocation();
            }
        });

        btnViewLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String sdestination = tvLatAd.getText().toString().trim()+ ", " + tvLongAd.getText().toString();
                String  ssource = tvUserLatAd.getText().toString().trim() + ", "+ tvUserLongAd.getText().toString();


                if (ssource.equals("") && sdestination.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Enter both location", Toast.LENGTH_SHORT).show();
                } else
                {
                    DisplayTrack(ssource, sdestination);
                }
            }
        });

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadImage(View view)
    {

        DownloadImage doin = new DownloadImage();
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
                String query= "SELECT Image from tblOrders where ID='" + tvID.getText().toString() + "'";
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


    public void fetchLocation()
    {
        double latitude=0;
        double longitude=0;
        try {
            if
            (ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    !=
                    PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new

                        String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        gpsTracker = new GpsTracker1(NewOrderDetailsActivity.this);
        if(gpsTracker.canGetLocation()){
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            tvUserLatAd.setText(String.valueOf(latitude));
            tvUserLongAd.setText(String.valueOf(longitude));
        }else {
            gpsTracker.showSettingsAlert();
        }

        if(latitude==0.0  && longitude==0.0)
        {
            btnViewLocation.setVisibility(View.INVISIBLE);
            btnFetchLocation.setVisibility(View.VISIBLE);

            Toast.makeText(getApplicationContext(), "IF", Toast.LENGTH_LONG).show();
        }
        else {
            btnFetchLocation.setVisibility(View.INVISIBLE);
            btnViewLocation.setVisibility(View.VISIBLE);

            Toast.makeText(getApplicationContext(), "Else", Toast.LENGTH_LONG).show();
        }
    }

    private void DisplayTrack(String ssource, String sdestination)
    {
        try
        {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + ssource + "/" + sdestination);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //   String URI= String.format(Locale.ENGLISH, "http://google.com/maps?q=loc:%f ,%f", 75.909032,14.432432);
            //  Intent intent1 = new Intent(Intent.ACTION_VIEW,Uri.parse(URI));
            //   startActivity(intent);
            Uri URI= Uri.parse("http://maps.googleapis.com/maps/api/geocode/json?latlng=lat,long&sesor=true");
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e)
        {
            Uri uri = Uri.parse("https://play.google.com/store/apps/dtails?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }

    @Override
    public void onBackPressed() {
        // do something on back.
        finish();
        startActivity(new Intent(getApplicationContext(),NewOrderListActivity.class));
    }


}