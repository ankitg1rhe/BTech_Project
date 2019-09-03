package com.android.iitfriends.badriver;

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
import android.widget.Toast;

import com.android.iitfriends.badriver.utility.LoginDbHelper;
import com.android.iitfriends.badriver.utility.DataContract.LoginEntry;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(300);
                checkNetworkConnection();
            }
        },1000);
    }

    private void checkNetworkConnection(){
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE) ;
        NetworkInfo networkInfo = conMan.getActiveNetworkInfo() ;
        if(networkInfo != null && networkInfo.isConnected()) {
            checkStatus();
        }else {
            Toast.makeText(this, "Network unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkStatus(){
        LoginDbHelper mDbHelper = new LoginDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                LoginEntry._ID,
                LoginEntry.USER_NAME,
                LoginEntry.USER_MOBILE
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
                Intent intent = new Intent(LauncherActivity.this, OrderActivity.class);
                startActivity(intent);
                finish();

            }else {
                Intent intent = new Intent(LauncherActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        } finally {
            cursor.close();
        }
    }
}
