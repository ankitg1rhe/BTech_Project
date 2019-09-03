package com.android.iitfriends.bas.Orders;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = OrderDbHelper.class.getSimpleName();
    /** Name of the database file */
    private static final String DATABASE_NAME = "order.db";
    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Constructs a new instance of {@link OrderDbHelper}.
     *
     * @param context of the app
     */
    public OrderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_LOGIN_TABLE =  "CREATE TABLE " + QueryUtils.ORDER_TABLE + " ("
                + QueryUtils.JSON_RESPONSE + "  TEXT NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
