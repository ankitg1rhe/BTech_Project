package com.android.iitfriends.bas;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.iitfriends.bas.utility.DataContract;
import com.android.iitfriends.bas.utility.RegisterDbHelper;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.android.iitfriends.bas.utility.Constants;
import com.android.iitfriends.bas.utility.RequestHandler;
import com.android.iitfriends.bas.utility.DataContract.LoginEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText textName, textEmail, textPassword;
    private Button signUpButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textName = (EditText) findViewById(R.id.register_name);
        textEmail = (EditText) findViewById(R.id.register_email);
        textPassword = (EditText) findViewById(R.id.register_password);

        signUpButton = (Button) findViewById(R.id.register_button);
        signUpButton.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
    }

    private void checkInfo(){
        String name = textName.getText().toString().trim();
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!email.endsWith("@itbhu.ac.in") && !email.endsWith("@iitbhu.ac.in")){
            textEmail.setError("Required domains are \n\'@itbhu.ac.in\' \nand \'@iitbhu.ac.in\'");
            textEmail.requestFocus();
            return;
        }

        if(password.length() != 6){
            textPassword.setError("Please enter 6-digit pin");
            textPassword.requestFocus();
            return;
        }

        String[] arr = email.split("@")[0].split("\\.");
        String username = "";
        for(String s : arr) username += s;
        String otp = generateOTP(name, username, password);
        sendOTP(email, username, otp);
        /*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(300);
                Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                startActivity(intent);
                finish();
            }
        }, 200);
        */
    }

    private String generateOTP(String name, String username, String password){
        int OTP = 0;
        Random ran = new Random();
        while(OTP < 10002)
            OTP = ran.nextInt(99998);

        RegisterDbHelper mDbHelper = new RegisterDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(LoginEntry.TABLE_REGISTER, null, null);

        // Create a ContentValues object where column names are the keys,
        // and login attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(LoginEntry.REGISTER_NAME, name);
        values.put(LoginEntry.REGISTER_EMAIL, username);
        values.put(LoginEntry.PASSWORD, password);
        values.put(LoginEntry.OTP_REGISTER, String.valueOf(OTP));

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(LoginEntry.TABLE_REGISTER, null, values);
        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error while verifying...", Toast.LENGTH_SHORT).show();
            startActivity(getIntent());
        }

        return  String.valueOf(OTP);
    }

    private void sendOTP(final String mEmail, final String mUsername, final String mOTP){
        progressDialog.setMessage("Verifying your email...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_OTP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"),
                                        Toast.LENGTH_SHORT).show();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // first value would be name of param in php file
                // 2nd value would be actual value of param
                params.put("username", mUsername);
                params.put("email", mEmail);
                params.put("otp", mOTP);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v == signUpButton){
            checkInfo();
        }
    }
}

