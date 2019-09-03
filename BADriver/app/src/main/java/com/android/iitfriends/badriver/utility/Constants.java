package com.android.iitfriends.badriver.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import com.android.iitfriends.badriver.utility.DataContract.LoginEntry;

public class Constants {
    public static final String LOG_TAG = Constants.class.getSimpleName() ;

    private static String _ip = "http://172.17.32.95" ;
    private static final String ROOT_URL = _ip + "/bhuas/driver/v1/" ;
    public static final String URL_LOGIN = ROOT_URL + "userLogin.php";

    private static final String ROOT_URL_APP_WORK = _ip + "/bhuas/appwork/web/" ;
    public static final String URL_GET_CONFIRM_ORDER = ROOT_URL_APP_WORK + "driverOrder.php";
    public static final String URL_GET_DATA_ORDER = ROOT_URL_APP_WORK + "notifyDriver.php";
    public static final String URL_CONFIRM_ORDER = ROOT_URL_APP_WORK + "confirmOrder.php";


    /**
     * Following method used for logging in and getting user data
     */
    public static boolean makeLogin(Context context, String mobile, String name, int auto_id){
        LoginDbHelper mDbHelper = new LoginDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and login attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(LoginEntry.USER_NAME, name);
        values.put(LoginEntry.USER_MOBILE, mobile);
        values.put(LoginEntry.AUTO_ID, auto_id);

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(DataContract.LoginEntry.TABLE_NAME, null, values);
        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(context, "Error while logging in", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
