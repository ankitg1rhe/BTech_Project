package com.android.iitfriends.bas.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SendOtp {

    public static final String LOG_TAG = SendOtp.class.getSimpleName();

    private Context context;
    private String email;
    private String username;
    private int OTP = 0;
    private boolean result;

    private ProgressDialog progressDialog;

    public SendOtp(String email, String username, Context context){
        Random ran = new Random();
        while(OTP < 10000)
            OTP = ran.nextInt(99998);

        this.context = context;
        this.email = email;
        this.username = username;
        result = false;
    }

    public static boolean sendOTP(String email, String username, Context context){
        return false;
    }

}
