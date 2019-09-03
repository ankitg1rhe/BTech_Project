package com.android.iitfriends.bas.utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.iitfriends.bas.utility.DataContract.LoginEntry;

public class RegisterDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = LoginDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "register.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link LoginDbHelper}.
     *
     * @param context of the app
     */
    public RegisterDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_LOGIN_TABLE =  "CREATE TABLE " + LoginEntry.TABLE_REGISTER+ " ("
                + LoginEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LoginEntry.REGISTER_NAME + " TEXT NOT NULL, "
                + LoginEntry.REGISTER_EMAIL + "  TEXT NOT NULL, "
                + LoginEntry.PASSWORD + " TEXT NOT NULL, "
                + LoginEntry.OTP_REGISTER + " TEXT NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
