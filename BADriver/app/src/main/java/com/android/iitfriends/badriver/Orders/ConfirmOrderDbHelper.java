package com.android.iitfriends.badriver.Orders;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class ConfirmOrderDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = ConfirmOrderDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "confirmorderdatabase.db";
    private static final int DATABASE_VERSION = 1;

    public ConfirmOrderDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ORDER_TABLE =  "CREATE TABLE " + QueryUtils.CONFIRM_ORDER_TABLE + " ("
                + QueryUtils.CONFIRM_ORDER_DATA + "  TEXT NOT NULL);";
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ORDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
