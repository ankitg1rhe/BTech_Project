package com.android.iitfriends.bas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.iitfriends.bas.utility.Constants;
import com.android.iitfriends.bas.utility.DataContract.LoginEntry;
import com.android.iitfriends.bas.utility.RegisterDbHelper;
import com.android.iitfriends.bas.utility.RequestHandler;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OTPActivity extends AppCompatActivity {
    private EditText textOTP;
    private Button otpButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        textOTP = (EditText) findViewById(R.id.enter_otp);
        otpButton = (Button) findViewById(R.id.send_otp);
        progressDialog = new ProgressDialog(this);

        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chekOTP();
            }
        });
    }

    private void chekOTP(){
        String otp_typed = textOTP.getText().toString().trim();
        String name, email, password, otp_sent;

        RegisterDbHelper mDbHelper = new RegisterDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                LoginEntry._ID,
                LoginEntry.REGISTER_NAME,
                LoginEntry.REGISTER_EMAIL,
                LoginEntry.PASSWORD,
                LoginEntry.OTP_REGISTER
        };

        Cursor cursor = db.query(
                LoginEntry.TABLE_REGISTER,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            if(cursor.moveToNext()){
                int nameIndex = cursor.getColumnIndex(LoginEntry.REGISTER_NAME);
                name = cursor.getString(nameIndex);
                int emailIndex = cursor.getColumnIndex(LoginEntry.REGISTER_EMAIL);
                email = cursor.getString(emailIndex);
                int passIndex = cursor.getColumnIndex(LoginEntry.PASSWORD);
                password = cursor.getString(passIndex);
                int otpIndex = cursor.getColumnIndex(LoginEntry.OTP_REGISTER);
                otp_sent = cursor.getString(otpIndex);

                if(otp_sent.equals(otp_typed)){
                    registerUser(name, email, password);
                }else {
                    textOTP.setError("Entered wrong OTP");
                    textOTP.requestFocus();
                }
            }
        } finally {
            cursor.close();
        }

    }

    private void registerUser(final String name, final String email, final String password){
        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        RegisterDbHelper mDbHelper = new RegisterDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(LoginEntry.TABLE_REGISTER, null, null);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_REGISTERED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),
                                    jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            if(!jsonObject.getBoolean("error")){
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "Server connection error", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // first value would be name of param in php file
                // 2nd value would be actual value of param
                params.put("name", name);
                params.put("username",email);
                params.put("password",password);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        RegisterDbHelper mDbHelper = new RegisterDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(LoginEntry.TABLE_REGISTER, null, null);
        super.onBackPressed();
    }
}
