package com.android.iitfriends.bas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.iitfriends.bas.Orders.OrderDbHelper;
import com.android.iitfriends.bas.Orders.QueryUtils;
import com.android.iitfriends.bas.utility.Constants;
import com.android.iitfriends.bas.utility.DataContract.LoginEntry;
import com.android.iitfriends.bas.utility.DataContract.Location;
import com.android.iitfriends.bas.utility.LoginDbHelper;
import com.android.iitfriends.bas.utility.RequestHandler;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Spinner mSourceSpinner, mDestinationSpinner;
    private TextView quantity;
    private TextView totalMoney;
    private Button increment;
    private Button decrement;
    private Button orderNow;

    private String nameOfCustomer;
    private String emailOfCustomer;

    private LoginDbHelper mDbHelper;
    private ProgressDialog progressDialog;

    /*
    Initial value for source and location
     */
    private int mSource, mDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // find name of customer
        setNameOfCustomer();

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);


        mSourceSpinner = (Spinner) findViewById(R.id.spinner_source);
        mDestinationSpinner = (Spinner) findViewById(R.id.spinner_destination);
        quantity = (TextView) findViewById(R.id.quantity);
        totalMoney = (TextView) findViewById(R.id.actual_money);
        progressDialog = new ProgressDialog(this);
        setupSpinner();

        // for increment and decrement of customer
        increment = (Button) findViewById(R.id.increment_customer);
        increment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                incrementCustomer();
            }
        });
        decrement = (Button) findViewById(R.id.decrement_customer);
        decrement.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                decrementCustomer();
            }
        });
        // for sending orders
        orderNow = (Button) findViewById(R.id.order_auto);
        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMyDialog();
            }
        });

        mSourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.hostel_1))) {
                        mSource = Location.CV_RAMAN;
                    } else if (selection.equals(getString(R.string.hostel_2))) {
                        mSource = Location.DG_CORNER;
                    } else if (selection.equals(getString(R.string.hostel_3))) {
                        mSource = Location.HEALTH_C;
                    } else if (selection.equals(getString(R.string.hostel_4))) {
                        mSource = Location.LC_CORNER;
                    } else if (selection.equals(getString(R.string.hostel_5))) {
                        mSource = Location.MORVI;
                    } else if (selection.equals(getString(R.string.hostel_6))) {
                        mSource = Location.RAMANUJAN;
                    } else if (selection.equals(getString(R.string.hostel_7))) {
                        mSource = Location.S_BHAVAN;
                    } else if (selection.equals(getString(R.string.hostel_8))) {
                        mSource = Location.VISHWAKARMA;
                    } else if (selection.equals(getString(R.string.hostel_9))) {
                        mSource = Location.VIVEKANAND;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSource = Location.CV_RAMAN;
            }
        });
        mDestinationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.hostel_1))) {
                        mDestination = Location.CV_RAMAN;
                    } else if (selection.equals(getString(R.string.hostel_2))) {
                        mDestination = Location.DG_CORNER;
                    } else if (selection.equals(getString(R.string.hostel_3))) {
                        mDestination = Location.HEALTH_C;
                    } else if (selection.equals(getString(R.string.hostel_4))) {
                        mDestination = Location.LC_CORNER;
                    } else if (selection.equals(getString(R.string.hostel_5))) {
                        mDestination = Location.MORVI;
                    } else if (selection.equals(getString(R.string.hostel_6))) {
                        mDestination = Location.RAMANUJAN;
                    } else if (selection.equals(getString(R.string.hostel_7))) {
                        mDestination = Location.S_BHAVAN;
                    } else if (selection.equals(getString(R.string.hostel_8))) {
                        mDestination = Location.VISHWAKARMA;
                    } else if (selection.equals(getString(R.string.hostel_9))) {
                        mDestination = Location.VIVEKANAND;
                    } else if (selection.equals(getString(R.string.hostel_10))) {
                        mDestination = Location.LANKA;
                    } else {
                        mDestination = Location.VISHWANATH;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDestination = Location.CV_RAMAN;
            }
        });

        /*
        * all settings for navigation activity
        */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void createMyDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.builder_message);
        builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getOrder();
            }
        });

        builder.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void getOrder(){
        TextView customerView = (TextView) findViewById(R.id.quantity);
        int noofcust = Integer.parseInt(customerView.getText().toString().trim()) ;
        // Set the integer mSelected to the constant values

        sendOrder(emailOfCustomer, Integer.toString(mSource), Integer.toString(mDestination), Integer.toString(noofcust));
    }

    private void sendOrder(final String customer, final String source, final String destination, final String noofcust){
        progressDialog.setMessage("Sending order...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_SEND_ORDER,
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
                        Toast.makeText(getApplicationContext(), "Server Connection Error", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("customer", customer);
                params.put("source",source);
                params.put("destination",destination);
                params.put("noofcust",noofcust);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setNameOfCustomer() {
        mDbHelper = new LoginDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                LoginEntry._ID,
                LoginEntry.USER_NAME,
                LoginEntry.USER_EMAIL
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
                nameOfCustomer = cursor.getString(nameIndex);

                int emailIndex = cursor.getColumnIndex(LoginEntry.USER_EMAIL);
                emailOfCustomer = cursor.getString(emailIndex);

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                TextView nameView = (TextView) headerView.findViewById(R.id.customer_name);
                nameView.setText("Hi,\n" + nameOfCustomer);
            }
        } finally {
            cursor.close();
        }
    }

    /*
    Increase the number of customer
     */
    public void incrementCustomer(){
        int cus = Integer.parseInt(quantity.getText().toString().trim()) ;
        if(cus >= 5) return;
        ++cus;
        quantity.setText(String.valueOf(cus));
        totalMoney.setText("₹ " + 10*cus);
    }

    /*
    Decrease the number of customer
     */
    public void decrementCustomer(){
        int cus = Integer.parseInt(quantity.getText().toString().trim()) ;
        if(cus <= 1) return;
        --cus;
        quantity.setText(String.valueOf(cus));
        totalMoney.setText("₹ " + 10*cus);
    }

    /**
     * Setup the dropdown spinner that allows the
     * user to select the source and destination of the pet.
     */
    private void setupSpinner(){
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter sourceSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_source_option, android.R.layout.simple_spinner_item);
        ArrayAdapter destinationSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_destination_option, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        sourceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        destinationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mSourceSpinner.setAdapter(sourceSpinnerAdapter);
        mDestinationSpinner.setAdapter(destinationSpinnerAdapter);
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
            makeLogout();
        }else if(id == R.id.order_summary){
            Intent intent = new Intent(OrderActivity.this, SummaryActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void makeLogout(){
        OrderDbHelper oDbHelper = new OrderDbHelper(this);
        SQLiteDatabase db = oDbHelper.getWritableDatabase();
        db.delete(QueryUtils.ORDER_TABLE, null, null);
        mDbHelper = new LoginDbHelper(this);
        db = mDbHelper.getWritableDatabase();
        db.delete(LoginEntry.TABLE_NAME, null, null);
        Intent intent = new Intent(OrderActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
