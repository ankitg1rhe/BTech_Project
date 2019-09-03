package com.android.iitfriends.badriver;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.iitfriends.badriver.Orders.ConfirmOrderDbHelper;
import com.android.iitfriends.badriver.Orders.Order;
import com.android.iitfriends.badriver.Orders.OrderAdapter;
import com.android.iitfriends.badriver.Orders.QueryUtils;
import com.android.iitfriends.badriver.utility.Constants;
import com.android.iitfriends.badriver.utility.DataContract;
import com.android.iitfriends.badriver.utility.LoginDbHelper;
import com.android.iitfriends.badriver.utility.RequestHandler;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.HashMap;
import java.util.Map;

public class SummaryActivity extends AppCompatActivity implements OrderAdapter.ListItemClickListener{
    private ProgressDialog progressDialog;
    private OrderAdapter mAdapter;
    private RecyclerView mOrderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        progressDialog = new ProgressDialog(this);

        /* Appwork activities*/
        setAdapterManullyConfirm();

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView_summary);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
        TextView refreshView = (TextView) findViewById(R.id.action_refresh_summary);
        refreshView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(100);
                        setAdapterManullyConfirm();
                        progressDialog.dismiss();
                    }
                }, 100);
            }
        });
    }

    private void setAdapterManullyConfirm(){
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE) ;
        NetworkInfo networkInfo = conMan.getActiveNetworkInfo() ;
        if(networkInfo != null && networkInfo.isConnected()) {
            getDriverMobile();
        }else {
            Toast.makeText(this, "Network unavailable", Toast.LENGTH_SHORT).show();
        }
        mOrderView = (RecyclerView) findViewById(R.id.recycler_view_summary);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mOrderView.setLayoutManager(layoutManager);
        mOrderView.setHasFixedSize(true);
        String confirmRespond = getConfirmInformation();

        View emptyView = findViewById(R.id.empty_view_of_summary);

        if(!TextUtils.isEmpty(confirmRespond) && confirmRespond != null){
            emptyView.setVisibility(View.GONE);
            mAdapter = new OrderAdapter( this, confirmRespond, this);
            mOrderView.setAdapter(mAdapter);
        }

    }

    private void viewConfirmedOrders(final String mobile){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GET_CONFIRM_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!TextUtils.isEmpty(response))
                            QueryUtils.saveConfirmOrderData(response, SummaryActivity.this);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SummaryActivity.this, "Server connection error", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", mobile);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void getDriverMobile(){
        LoginDbHelper mDbHelper = new LoginDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {DataContract.LoginEntry.USER_MOBILE};
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
                int emailIndex = cursor.getColumnIndex(DataContract.LoginEntry.USER_MOBILE);
                String mobileOfDriver = cursor.getString(emailIndex);
                viewConfirmedOrders(mobileOfDriver);
            }
        } finally {
            cursor.close();
        }
    }
    private String getConfirmInformation(){
        ConfirmOrderDbHelper cDbHelper = new ConfirmOrderDbHelper(this);
        SQLiteDatabase db = cDbHelper.getReadableDatabase();

        String[] projection = {QueryUtils.CONFIRM_ORDER_DATA};
        Cursor cursor = db.query(
                QueryUtils.CONFIRM_ORDER_TABLE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try{
            if(cursor.moveToNext()){
                int responseIndex = cursor.getColumnIndex(QueryUtils.CONFIRM_ORDER_DATA);
                return cursor.getString(responseIndex);
            }
        }finally {
            cursor.close();
        }
        return  null;
    }
    @Override
    public void onListItemClick(Order order) { }
}
