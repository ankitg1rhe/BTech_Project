package com.android.iitfriends.bas.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.android.iitfriends.bas.utility.DataContract.LoginEntry;


public class Constants {

    public static final String LOG_TAG = Constants.class.getSimpleName() ;

    private static String _ip = "http://172.17.32.95" ;
    private static final String ROOT_URL =  _ip + "/bhuas/customer/v1/" ;
    public static final String URL_REGISTERED = ROOT_URL + "registerUser.php" ;
    public static final String URL_LOGIN = ROOT_URL + "userLogin.php";
    public static final String URL_OTP = ROOT_URL + "checkForOtp.php";

    private static final String APP_WORK_URL = _ip + "/bhuas/appwork/web/" ;
    public static final String URL_SEND_ORDER = APP_WORK_URL + "sendOrder.php";
    public static final String URL_MY_ORDER = APP_WORK_URL + "myOrder.php" ;
    public static final String CANCEL_MY_ORDER = APP_WORK_URL + "cancelOrder.php" ;

    /**
     * Following method used for logging in and getting user data
     */
    public static boolean makeLogin(Context context, String username, String name){
        LoginDbHelper mDbHelper = new LoginDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DataContract.LoginEntry.TABLE_NAME, null, null);

        // Create a ContentValues object where column names are the keys,
        // and login attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(LoginEntry.USER_NAME, name);
        values.put(LoginEntry.USER_EMAIL, username);

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





/*

    private class registerTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            // Create URL object
            URL url = createUrl(Constants.URL_OTP);
            try {
                String jsonResponse = makeHttpRequest(url, params);
                try{
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if(!jsonObject.getBoolean("error")){
                        Toast.makeText(getApplicationContext(),
                                jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Verifying user...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            super.onPostExecute(aBoolean);
        }

        /**
         * Returns new URL object from the given string URL.
         */
/*
private URL createUrl(String stringUrl) {
    URL url = null;
    try {
        url = new URL(stringUrl);
    } catch (MalformedURLException exception) {
        return null;
    }
    return url;
}

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
/*
    private String makeHttpRequest(URL url, String... params) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            outputStream = urlConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String url_post_data =
                    URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&"
                            + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&"
                            + URLEncoder.encode("otp", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
            bufferedWriter.write(url_post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = bufferedReader.readLine()) != null)
                jsonResponse += line;
            bufferedReader.close();
            inputStream.close();

        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return jsonResponse;
    }
}

 */