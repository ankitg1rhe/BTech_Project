package com.android.iitfriends.badriver.utility;

import android.provider.BaseColumns;

public final class DataContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DataContract(){}
    /**
     * Inner class that defines constant values for the login database table.
     * Each entry in the table represents a single pet.
     */
    public static final class LoginEntry implements BaseColumns{
        public static final String TABLE_NAME = "user_info";
        public static final String _ID = BaseColumns._ID;
        public static final String USER_NAME = "user_name";
        public static final String USER_MOBILE = "user_mobile";
        public static final String AUTO_ID = "user_auto";

    }
}
