package com.android.iitfriends.bas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.iitfriends.bas.Orders.Order;
import com.android.iitfriends.bas.Orders.OrderAdapter;
import com.android.iitfriends.bas.Orders.OrderDbHelper;
import com.android.iitfriends.bas.Orders.QueryUtils;
import com.android.iitfriends.bas.utility.Constants;
import com.android.iitfriends.bas.utility.DataContract;
import com.android.iitfriends.bas.utility.LoginDbHelper;
import com.android.iitfriends.bas.utility.RequestHandler;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.HashMap;
import java.util.Map;

public class SummaryActivity extends AppCompatActivity
        implements OrderAdapter.ListItemClickListener{
    private LoginDbHelper mDbHelper;
    /**
     * Constant value for the order loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private String response;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        mRecyclerView = (RecyclerView) findViewById(R.id.order_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        progressDialog = new ProgressDialog(this);
        setAdapterManually();
        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        TextView refreshView = (TextView) findViewById(R.id.action_refresh);
        refreshView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(100);
                        setAdapterManually();
                    }
                }, 100);
            }
        });
    }

    private void setAdapterManually(){
        progressDialog.dismiss();
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE) ;
        NetworkInfo networkInfo = conMan.getActiveNetworkInfo() ;
        if(networkInfo != null && networkInfo.isConnected()) {
            getCustomer();
            response = getInformation();
        }else {
            Toast.makeText(this, "Network unavailable", Toast.LENGTH_SHORT).show();
        }
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);

        if(!TextUtils.isEmpty(response) && response != null){
            emptyView.setVisibility(View.GONE);
            mAdapter = new OrderAdapter(this,response);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private void viewMyOrders(final String customer){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_MY_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!TextUtils.isEmpty(response))
                            QueryUtils.saveData(response, getApplicationContext());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Server connection error", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customer);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void getCustomer() {
        mDbHelper = new LoginDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DataContract.LoginEntry._ID,
                DataContract.LoginEntry.USER_NAME,
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
                String customer = cursor.getString(emailIndex);
                viewMyOrders(customer);
            }
        } finally {
            cursor.close();
        }
    }
    private String getInformation(){
        OrderDbHelper oDbHelper = new OrderDbHelper(this);
        SQLiteDatabase db = oDbHelper.getReadableDatabase();

        String[] projection = {
                QueryUtils.JSON_RESPONSE
        };

        Cursor cursor = db.query(
                QueryUtils.ORDER_TABLE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            if(cursor.moveToNext()){
                int responseIndex = cursor.getColumnIndex(QueryUtils.JSON_RESPONSE);
                String respond = cursor.getString(responseIndex);
                return respond;
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    @Override
    public void onListItemClick(Order order) {
        int status = order.getStatus();
        if(status != 0){
            Toast.makeText(this, "Oredr cannot be cancelled", Toast.LENGTH_SHORT).show();
            return;
        }

        String obj = "{" +
                "\"source\":\"" + order.getSource() + "\"," +
                "\"destination\":\"" + order.getDestination() + "\"," +
                "\"reg_date\":\"" + order.getRegDate() + "\"," +
                "\"number\":\"" + order.getOrderNumber() + "\"," +
                "\"noofcust\":\"" + order.getNoOfCustomer() + "\"}" ;

        Intent intent = new Intent(this, CancelOrderActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, obj);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, OrderActivity.class));
        finish();
    }
}
