package com.android.iitfriends.bas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.iitfriends.bas.utility.Constants;
import com.android.iitfriends.bas.utility.DataContract;
import com.android.iitfriends.bas.utility.LoginDbHelper;
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

public class CancelOrderActivity extends AppCompatActivity {
    private String reg_date = "";
    private int src = 0, dest = 0;
    private int orderNum = 0, noOfCus = 0;

    private ProgressDialog progressDialog;
    private String emailOfCustomer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);
        progressDialog = new ProgressDialog(this);
        setNameOfCustomer();

        Intent startingIntent = getIntent();
        if(startingIntent.hasExtra(Intent.EXTRA_TEXT)){
            String sentObject = startingIntent.getStringExtra(Intent.EXTRA_TEXT);
            setAllValues(sentObject);
        }else {
            Toast.makeText(this, "Some error occurred", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
            finish();
        }

        Button cancelOrderButton = (Button) findViewById(R.id.cancel_order_button);
        cancelOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMyDialog();
            }
        });

    }

    public void createMyDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.builder_message);
        builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelMyOrder(String.valueOf(orderNum), emailOfCustomer);
            }
        });

        builder.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), SummaryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void cancelMyOrder(final String number, final String customer){
        progressDialog.setMessage("Cancelling order...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.CANCEL_MY_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            if(!jsonObject.getBoolean("error")){
                                Intent intent = new Intent(getApplicationContext(), SummaryActivity.class);
                                startActivity(intent);
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

                params.put("number", number);
                params.put("customer", customer);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void setNameOfCustomer() {
        LoginDbHelper mDbHelper = new LoginDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DataContract.LoginEntry._ID,
                DataContract.LoginEntry.USER_EMAIL
        };

        Cursor cursor = db.query(
                DataContract.LoginEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            if(cursor.moveToNext()){
                int emailIndex = cursor.getColumnIndex(DataContract.LoginEntry.USER_EMAIL);
                emailOfCustomer = cursor.getString(emailIndex);
            }
        } finally {
            cursor.close();
        }
    }

    private void setAllValues(String orderResponse){

        try {
            JSONObject jsonObject = new JSONObject(orderResponse);
            reg_date = jsonObject.getString("reg_date");
            orderNum = jsonObject.getInt("number");
            src = jsonObject.getInt("source");
            dest = jsonObject.getInt("destination");
            noOfCus = jsonObject.getInt("noofcust");
        }catch (JSONException e){
            e.printStackTrace();
            return;
        }

        String[] arr = reg_date.split(" ");
        String[] tm = arr[1].split(":");
        int hr = Integer.parseInt(tm[0].trim());
        String when = " AM";
        if(hr > 12){
            hr -= 12;
            if(hr > 9) tm[0] = String.valueOf(hr);
            else  tm[0] = "0" + String.valueOf(hr);
            when = " PM";
        }
        reg_date = "Date: " + arr[0].split("-")[2] + "-" + arr[0].split("-")[1]
                + "-" + arr[0].split("-")[0];
        arr[1] = "Time: " + tm[0] + ":" + tm[1] + when;

        String srcString = "";
        switch (src){
            case 1:
                srcString = "CV Raman Hostel";
                break;
            case 2:
                srcString = "Dhanrajgiri Corner";
                break;
            case 3:
                srcString = "Health Center";
                break;
            case 4:
                srcString = "Limbdi Corner";
                break;
            case 5:
                srcString = "Morvi Hostel";
                break;
            case 6:
                srcString = "Ramanujan Hostel";
                break;
            case 7:
                srcString = "Swatantrata Bhavan";
                break;
            case 8:
                srcString = "Vishwakarma Hostel";
                break;
            case 9:
                srcString = "Vivekanand Hostel";
                break;
            case 10:
                srcString = "Lanaka Gate";
                break;
            case 11:
                srcString = "Vishwanath Temple";
        }

        String destString = "";
        switch (dest){
            case 1:
                destString = "CV Raman Hostel";
                break;
            case 2:
                destString = "Dhanrajgiri Corner";
                break;
            case 3:
                destString = "Health Center";
                break;
            case 4:
                destString = "Limbdi Corner";
                break;
            case 5:
                destString = "Morvi Hostel";
                break;
            case 6:
                destString = "Ramanujan Hostel";
                break;
            case 7:
                destString = "Swatantrata Bhavan";
                break;
            case 8:
                destString = "Vishwakarma Hostel";
                break;
            case 9:
                destString = "Vivekanand Hostel";
                break;
            case 10:
                destString = "Lanaka Gate";
                break;
            case 11:
                destString = "Vishwanath Temple";
        }


        TextView timeView = (TextView) findViewById(R.id.order_time_in_confirm);
        timeView.setText(arr[1]);
        TextView dateView = (TextView) findViewById(R.id.order_date_in_confirm);
        dateView.setText(reg_date);
        TextView sourceView = (TextView) findViewById(R.id.from_source_in_confirm);
        sourceView.setText("From : " + srcString);
        TextView destinationView = (TextView) findViewById(R.id.to_destination_in_confirm);
        destinationView.setText("To : " + destString);
        TextView numOfCusView = (TextView) findViewById(R.id.no_of_customer_in_confirm);
        numOfCusView.setText("Number of Customers : " + noOfCus);
    }
}

