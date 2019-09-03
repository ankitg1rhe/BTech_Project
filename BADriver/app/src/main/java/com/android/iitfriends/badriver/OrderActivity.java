package com.android.iitfriends.badriver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.iitfriends.badriver.Orders.DataOrderDbHelper;
import com.android.iitfriends.badriver.Orders.Order;
import com.android.iitfriends.badriver.Orders.OrderAdapter;
import com.android.iitfriends.badriver.Orders.QueryUtils;
import com.android.iitfriends.badriver.utility.Constants;
import com.android.iitfriends.badriver.utility.LoginDbHelper;
import com.android.iitfriends.badriver.utility.DataContract.LoginEntry;
import com.android.iitfriends.badriver.utility.RequestHandler;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OrderAdapter.ListItemClickListener {

    private String nameOfDriver;
    private String mobileOfDriver;
    private int autoID;

    private LoginDbHelper mDbHelper;

    private OrderAdapter mAdapterData;
    private RecyclerView mOrderDataView;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        progressDialog = new ProgressDialog(this);
        setNameOfCustomer();

        /* App Activities */
        setAdapterManullyOrder();

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.order_adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

    }

    private void setAdapterManullyOrder(){
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE) ;
        NetworkInfo networkInfo = conMan.getActiveNetworkInfo() ;
        if(networkInfo != null && networkInfo.isConnected()) {
            getDataOrders();
        }else {
            Toast.makeText(this, "Network unavailable", Toast.LENGTH_SHORT).show();
        }
        mOrderDataView = (RecyclerView) findViewById(R.id.order_recycler_view);
        LinearLayoutManager layoutOrderManager = new LinearLayoutManager(this);
        mOrderDataView.setLayoutManager(layoutOrderManager);
        mOrderDataView.setHasFixedSize(true);
        String orderRespond = getDataInformation();

        View emptyView = findViewById(R.id.order_empty_view);

        if(!TextUtils.isEmpty(orderRespond) && orderRespond != null){
            emptyView.setVisibility(View.GONE);
            mAdapterData = new OrderAdapter(this,  orderRespond, this);
            mOrderDataView.setAdapter(mAdapterData);
        }
    }

    private void setNameOfCustomer() {
        mDbHelper = new LoginDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                LoginEntry._ID,
                LoginEntry.USER_NAME,
                LoginEntry.USER_MOBILE,
                LoginEntry.AUTO_ID
        };

        Cursor cursor = db.query(
                LoginEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            if(cursor.moveToNext()){
                int nameIndex = cursor.getColumnIndex(LoginEntry.USER_NAME);
                nameOfDriver = cursor.getString(nameIndex);

                int emailIndex = cursor.getColumnIndex(LoginEntry.USER_MOBILE);
                mobileOfDriver = cursor.getString(emailIndex);

                int idIndex = cursor.getColumnIndex(LoginEntry.AUTO_ID);
                autoID = cursor.getInt(idIndex);

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                TextView nameView = (TextView) headerView.findViewById(R.id.driver_name);
                nameView.setText( nameOfDriver);

                TextView mobileView = (TextView) headerView.findViewById(R.id.mobile_of_driver);
                mobileView.setText(mobileOfDriver.substring(2));

                TextView idView = (TextView) headerView.findViewById(R.id.auto_id_of_driver);
                idView.setText("BHU " + autoID);
            }
        } finally {
            cursor.close();
        }
    }

    private void getDataOrders(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.URL_GET_DATA_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!TextUtils.isEmpty(response))
                            QueryUtils.saveDataOrderData(response, getApplicationContext());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderActivity.this, "Server connection error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private String getDataInformation(){
        DataOrderDbHelper cDbHelper = new DataOrderDbHelper(this);
        SQLiteDatabase db = cDbHelper.getReadableDatabase();

        String[] projection = {QueryUtils.DATA_ORDER_DATA};
        Cursor cursor = db.query(
                QueryUtils.DATA_ORDER_TABLE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try{
            if(cursor.moveToNext()){
                int responseIndex = cursor.getColumnIndex(QueryUtils.DATA_ORDER_DATA);
                return cursor.getString(responseIndex);
            }
        }finally {
            cursor.close();
        }
        return  null;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.log_out) {
            mDbHelper = new LoginDbHelper(this);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.delete(LoginEntry.TABLE_NAME, null, null);
            Intent intent = new Intent(OrderActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        if(id == R.id.summary_orders){
            Intent intent = new Intent(this, SummaryActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh){
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(100);
                    setAdapterManullyOrder();
                    progressDialog.dismiss();
                }
            }, 100);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(Order order) {
        String obj = "{" +
                "\"customer\":\"" + order.getCustomerId() + "\"," +
                "\"source\":\"" + order.getmSource() + "\"," +
                "\"mobile\":\"" + mobileOfDriver + "\"," +
                "\"auto_id\":\"" + autoID + "\"," +
                "\"destination\":\"" + order.getmDestination() + "\"," +
                "\"reg_date\":\"" + order.getRegDate() + "\"," +
                "\"number\":\"" + order.getmNumber() + "\"," +
                "\"noofcust\":\"" + order.getNoOfCustomer() + "\"}" ;

        Intent intent = new Intent(this, ConfirmOrderActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, obj);
        startActivity(intent);
    }
}
