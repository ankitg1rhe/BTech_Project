package com.android.iitfriends.bas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.android.iitfriends.bas.utility.Constants;
import com.android.iitfriends.bas.utility.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText textEmail, textPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textEmail = (EditText) findViewById(R.id.login_email);
        textPassword = (EditText) findViewById(R.id.login_password);
        progressDialog = new ProgressDialog(this);

        Button signInButton = (Button) findViewById(R.id.email_sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        Button signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signIn(){
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if(!email.endsWith("@itbhu.ac.in") && !email.endsWith("@iitbhu.ac.in")){
            textEmail.setError("Please enter valid email");
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

        checkCredentials(username, password);
    }


    private boolean checkCredentials(final String username, final String password){
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
                                        jsonObject.getString("username"),
                                        jsonObject.getString("name"))){
                                    Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                                    startActivity(intent);
                                    finish();
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
                        Toast.makeText(getApplicationContext(), "Server connection error", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("username", username);
                params.put("password",password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        return false;
    }

}




