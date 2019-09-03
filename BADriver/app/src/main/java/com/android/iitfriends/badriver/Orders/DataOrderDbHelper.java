package com.android.iitfriends.badriver.Orders;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DataOrderDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = DataOrderDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "dataorderdatabase.db";
    private static final int DATABASE_VERSION = 1;

    public DataOrderDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ORDER_TABLE =  "CREATE TABLE " + QueryUtils.DATA_ORDER_TABLE + " ("
                + QueryUtils.DATA_ORDER_DATA + "  TEXT NOT NULL);";
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ORDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
