package com.example.pharmadb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RegisterPage extends AppCompatActivity {

    EditText etMedicalStore, etAddressLine1, etAddressLine2, etMobile, etEmailID;
    Spinner spinnerCity;

    ConnectionClass connectionClass = new ConnectionClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        etMedicalStore = (EditText) findViewById(R.id.etMedicalStore);
        etAddressLine1 = (EditText) findViewById(R.id.etAddressLine1);
        etAddressLine2 = (EditText) findViewById(R.id.etAddressLine2);
        spinnerCity = (Spinner) findViewById(R.id.spinnercity);
        etMobile = (EditText) findViewById(R.id.etMobile);
        etEmailID = (EditText) findViewById(R.id.etEmailID);


        try {
            Connection conn = connectionClass.CONN(); //Connection Object

            if (conn == null) {
                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
            } else {
                String query = ("SELECT * FROM tblCities");
                PreparedStatement preparedStatement2 = conn.prepareStatement(query);
                ResultSet rs = preparedStatement2.executeQuery();
                ArrayList<String> data = new ArrayList<String>();

                String city;

                while (rs.next()){
                    city = rs.getString("City");
                    data.add(city);
                }
                String[] array = data.toArray(new String[0]);
                ArrayAdapter NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
                spinnerCity.setAdapter(NoCoreAdapter);
            }
        }catch (Exception e) {
            e.printStackTrace();
            Writer writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));

            Toast.makeText(getApplicationContext(), writer.toString(), Toast.LENGTH_LONG).show();
        }

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!setValidation())
                    return;

                try {
                    Connection conn = connectionClass.CONN(); //Connection Object

                    if (conn == null) {
                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                    } else {

                        String pwd= Integer.toString(generateRandomNumber());

                        String UserID="";
                        String query1 = "Select ID from tblMedicalStore order by 1 Desc";
                        PreparedStatement preparedStatement2 = conn.prepareStatement(query1);
                        ResultSet rs = preparedStatement2.executeQuery();
                        if (rs.next()){
                            String inID=rs.getString("ID");
                            int intID;
                            intID=Integer.parseInt(inID.substring(inID.indexOf("-") + 1).toString());
                            intID=intID+1;

                            String unpadded = Integer.toString(intID);
                            String padded = "0000".substring(unpadded.length()) + unpadded;

                            UserID="User-"+padded;

                        }else{
                            UserID="User-0001";
                        }

                        String query2 = "Insert into tblMedicalStore (ID,MedicalStore,AddressLine1,AddressLine2,City," +
                                "Mobile,EmailID,LatAd,LongAd) values ('" + UserID + "','" + etMedicalStore.getText().toString().trim() + "'," +
                                "'" + etAddressLine1.getText().toString().trim() + "','" + etAddressLine2.getText().toString().trim() + "'," +
                                "'" + spinnerCity.getSelectedItem().toString() + "','" + etMobile.getText().toString().trim() + "'," +
                                "'" + etEmailID.getText().toString().trim() + "','','')";
                        preparedStatement2 = conn.prepareStatement(query2);
                        preparedStatement2.executeUpdate();

                        query2 = "Insert into tblLogin (UserID,Password,UserType,UserName) values ('" + etMobile.getText().toString().trim() + "','" + pwd + "','DBoy','" + etMedicalStore.getText().toString().trim() + "')";
                        preparedStatement2 = conn.prepareStatement(query2);
                        preparedStatement2.executeUpdate();

                        Toast.makeText(getApplicationContext(), "Registered Successfully. Password is "+pwd, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                        finish();
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Writer writer = new StringWriter();
                    e.printStackTrace(new PrintWriter(writer));

                    Toast.makeText(getApplicationContext(), writer.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });

        findViewById(R.id.login1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),LoginPage.class));
            }
        });



    }

    @Override
    public void onBackPressed() {
        // do something on back.
        finish();
        startActivity(new Intent(getApplicationContext(),LoginPage.class));
    }

    private boolean setValidation() {
        String dboy = etMedicalStore.getText().toString().trim();
        String addressline1 = etAddressLine1.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String emailid = etEmailID.getText().toString().trim();

        boolean isDBoy, isAddressLine1,isMobile,isEmailID;

        if (dboy.isEmpty()) {
            etMedicalStore.setError("Farmer Name can't be empty");
            isDBoy = false;
        } else {
            etMedicalStore.setError(null);
            isDBoy = true;
        }

        if (addressline1.isEmpty()) {
            etAddressLine1.setError("AddressLine1 can't be empty");
            isAddressLine1 = false;
        } else {
            etAddressLine1.setError(null);
            isAddressLine1 = true;
        }

        if (mobile.isEmpty()) {
            etMobile.setError("Mobile can't be empty");
            isMobile= false;
        }
        else if (mobile.length()!=10) {
            etMobile.setError("Invalid Mobile number");
            isMobile= false;
        }
        else {
            etMobile.setError(null);
            isMobile= true;
        }

        if (emailid.isEmpty()) {
            etEmailID.setError("Email ID can't be empty");
            isEmailID = false;
        } else {
            etEmailID.setError(null);
            isEmailID = true;
        }


        if(isDBoy==true && isAddressLine1==true && isMobile==true && isEmailID==true)
            return true;
        else
            return false;
    }

    public int generateRandomNumber() {
        int range = 9;  // to generate a single number with this range, by default its 0..9
        int length = 4; // by default length is 4

        int randomNumber;

        SecureRandom secureRandom = new SecureRandom();
        String s = "";
        for (int i = 0; i < length; i++) {
            int number = secureRandom.nextInt(range);
            if (number == 0 && i == 0) { // to prevent the Zero to be the first number as then it will reduce the length of generated pin to three or even more if the second or third number came as zeros
                i = -1;
                continue;
            }
            s = s + number;
        }

        randomNumber = Integer.parseInt(s);

        return randomNumber;
    }
}