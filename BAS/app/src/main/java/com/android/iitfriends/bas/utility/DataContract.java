package com.android.iitfriends.bas.utility;

import android.provider.BaseColumns;

/**
 * API Contract for the Pets app.
 */
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
        public static final String USER_EMAIL = "user_email";

        public static final String TABLE_REGISTER = "register_table";
        public static final String OTP_REGISTER = "otp_register";
        public static final String PASSWORD = "password";
        public static final String REGISTER_NAME = "user_name";
        public static final String REGISTER_EMAIL = "user_email";

    }

    /**
     * Inner class that defines constant values for all the locations
     * Each entry in the table represents a single location.
     */
    public static final class Location{
        public static final int CV_RAMAN = 1;
        public static final int DG_CORNER = 2;
        public static final int HEALTH_C = 3;
        public static final int LC_CORNER = 4;
        public static final int MORVI = 5;
        public static final int RAMANUJAN = 6;
        public static final int S_BHAVAN = 7;
        public static final int VISHWAKARMA = 8;
        public static final int VIVEKANAND = 9;
        public static final int LANKA = 10;
        public static final int VISHWANATH = 11;
    }

}
