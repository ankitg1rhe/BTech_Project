package com.android.iitfriends.badriver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.iitfriends.badriver.utility.Constants;
import com.android.iitfriends.badriver.utility.RequestHandler;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText textMobile, textPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textMobile = (EditText) findViewById(R.id.login_mobile);
        textPassword = (EditText) findViewById(R.id.login_password);
        progressDialog = new ProgressDialog(this);

        Button signInButton = (Button) findViewById(R.id.email_sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void signIn(){
        String mobile = textMobile.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if(mobile.length() != 10){
            textMobile.setError("Please enter valid mobile");
            textMobile.requestFocus();
            return;
        }

        if(password.length() != 6){
            textPassword.setError("Please enter 6-DIGIT PIN");
            textPassword.requestFocus();
            return;
        }

        String abmob = "ab" + mobile;
        checkCredentials(abmob, password);
    }

    private boolean checkCredentials(final String mobile, final String password){
        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")){
                               if(Constants.makeLogin(
                                        getApplicationContext(),
                                        jsonObject.getString("mobile"),
                                        jsonObject.getString("name"),
                                        jsonObject.getInt("auto_id"))){
                                    Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                   Toast.makeText(getApplicationContext(), "Error while app login", Toast.LENGTH_LONG).show();
                               }

                            }else {
                                Toast.makeText(getApplicationContext(),
                                        jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("mobile", mobile);
                params.put("password",password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        return false;
    }
}
