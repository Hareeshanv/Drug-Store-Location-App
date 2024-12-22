package com.example.pharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AndroidRuntimeException;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostOrderActivity extends NavigationDrawerBaseActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    Button uploadpic;
    ImageView imagebox;
    ProgressBar progressBar;

    byte[] byteArray;
    String encodedImage;

    ConnectionClass connectionClass = new ConnectionClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_order);

        super.OnCreateDrawer();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Post Order");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uploadpic = (Button) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imagebox = (ImageView) findViewById(R.id.imageView);

        progressBar.setVisibility(View.GONE);

        uploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !Environment.getExternalStorageState().equals(Environment.MEDIA_CHECKING)) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);

                } else {
                    Toast.makeText(PostOrderActivity.this, "No activity found to perform this task", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            progressBar.setVisibility(View.VISIBLE);
            Bitmap originBitmap = null;
            Uri selectedImage = data.getData();
            Toast.makeText(PostOrderActivity.this, selectedImage.toString(), Toast.LENGTH_LONG).show();
            InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(selectedImage);
                originBitmap = BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage().toString());
            }
            if (originBitmap != null) {
                this.imagebox.setImageBitmap(originBitmap);
                Log.w("Image Setted in", "Done Loading Image");
                try {
                    Bitmap image = ((BitmapDrawable) imagebox.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    UploadImage uploadImage = new UploadImage();
                    uploadImage.execute("");

                } catch (Exception e) {
                    Log.w("OOooooooooo", "exception");
                }
                Toast.makeText(PostOrderActivity.this, "Conversion Done", Toast.LENGTH_SHORT).show();
            }

        } else {
            System.out.println("Error Occured");
        }
    }

    public class UploadImage extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPostExecute(String r)
        {

            progressBar.setVisibility(View.GONE);
            Toast.makeText(PostOrderActivity.this , "Image Inserted Succesfully" , Toast.LENGTH_LONG).show();

        }
        @Override
        protected String doInBackground(String... params) {

            String msg = "unknown";
            try {
                Connection conn = connectionClass.CONN(); //Connection Object


                if (conn == null) {
                    Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                    return "";
                }

                String UserID="";
                String query1 = "Select ID from tblOrders order by 1 Desc";
                PreparedStatement preparedStatement2 = conn.prepareStatement(query1);
                ResultSet rs = preparedStatement2.executeQuery();
                if (rs.next()){
                    String inID=rs.getString("ID");
                    int intID;
                    intID=Integer.parseInt(inID.substring(inID.indexOf("-") + 1).toString());
                    intID=intID+1;

                    String unpadded = Integer.toString(intID);
                    String padded = "0000".substring(unpadded.length()) + unpadded;

                    UserID="Order-"+padded;

                }else{
                    UserID="Order-0001";
                }

                String OrderDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());

                //fetching value from shared preference
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PU", 0);
                String user_id = sharedPreferences.getString("user_id", "");


                String query = "insert into tblOrders (ID,Image,OrderDate,UserID,Status) " +
                        "values ('" + UserID + "','" + encodedImage + "','" + OrderDate + "','" + user_id + "', " +
                        "'New')";
                PreparedStatement preStmt = conn.prepareStatement( query );
                preStmt.executeUpdate();
                msg = "Inserted Successfully";
            } catch (SQLException ex) {
                msg = ex.getMessage().toString();
                Log.d( "Error no 1:", msg );
            } catch (IOError ex) {
                msg = ex.getMessage().toString();
                Log.d( "Error no 2:", msg );
            } catch (AndroidRuntimeException ex) {
                msg = ex.getMessage().toString();
                Log.d( "Error no 3:", msg );
            } catch (NullPointerException ex) {
                msg = ex.getMessage().toString();
                Log.d( "Error no 4:", msg );
            } catch (Exception ex) {
                msg = ex.getMessage().toString();
                Log.d( "Error no 5:", msg );
            }
            System.out.println( msg );
            return "";

        }
    }
    @Override
    public void onBackPressed() {
        // do something on back.
        finish();
        startActivity(new Intent(getApplicationContext(),HomePage.class));
    }


}
